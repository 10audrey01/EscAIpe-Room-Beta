package nz.ac.auckland.se206.controllers.rooms.rave;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GameState.Difficulty;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller class for handling the music quiz event room. */
public class MusicQuizController {
  @FXML private TextArea speechBox;
  @FXML private Button btnReturn;
  @FXML private Button hintBtn;
  @FXML private Button songBtn;
  @FXML private Button optionOneBtn;
  @FXML private Button optionTwoBtn;
  @FXML private Button optionThreeBtn;
  @FXML private Button optionFourBtn;
  @FXML private Button optionFiveBtn;
  @FXML private Button optionSixBtn;
  @FXML private Label timerLabel;
  @FXML private Label hintLabel;
  @FXML private Label cooldownLabel;
  @FXML private Label answerLabel;

  // instance of gamestate
  private GameState gameState;

  // fields managing the solution to the game - genres being possible genres.
  private String[] genres = {
    "techno", "pop", "hip hop", "rnb", "kpop", "funk", "house", "drum and bass"
  };
  private List<String> selectedGenres = new ArrayList<>();
  private ArrayList<Integer> availableWrongButtons;
  private String genreSolution;
  private int correctGenreIndex;

  private MediaPlayer musicPlayer;

  // fields for managing the timer cooldowns upon using an answer
  private int timeToAnswer;
  private Timer timer;
  private Thread timerThread;
  private boolean timerStarted;

  /**
   * Initializes the MusicQuizController by setting up the gamestate, labels, timers, and other
   * game-related variables.
   *
   * @throws IOException If there is an issue with input/output.
   * @throws URISyntaxException If there is an issue with URI syntax.
   * @throws ApiProxyException If there is an issue with the API proxy.
   */
  @FXML
  private void initialize() throws IOException, URISyntaxException, ApiProxyException {
    // gets the gamestate and adds the relevant labels to the gamestate
    this.gameState = GameState.getInstance();
    this.gameState.getTimeManager().addToTimers(timerLabel);
    this.gameState.getHintManager().addHintLabel(hintLabel);

    // initial values for managing the game
    this.timeToAnswer = 0;
    this.timerStarted = false;
    this.availableWrongButtons = new ArrayList<Integer>();

    this.speechBox.setText("Hey man, I need your help identifying this music...");
    Random random = new Random();

    // select a random genre as solution for respective song.
    int randomNumber = random.nextInt(8);
    this.genreSolution = genres[randomNumber];
    System.out.println("Solution for genre: " + genreSolution);

    // creates a music player to replay the song
    this.musicPlayer =
        new MediaPlayer(
            new Media(
                getClass()
                    .getResource("/sounds/songs/" + genreSolution + ".mp3")
                    .toURI()
                    .toString()));

    // event listener to replay the music once it ends.
    musicPlayer.setOnEndOfMedia(
        () -> {
          musicPlayer.seek(Duration.ZERO);
        });

    // select the other wrong options for the other buttons
    selectOptions();

    // hide the hint button on hard, as hints are not available
    if (GameState.difficulty == Difficulty.HARD) {
      hintBtn.setVisible(false);
    }
  }

  /**
   * Selects incorrect genre options for the game, shuffles them, and sets them on the corresponding
   * buttons. Also determines the correct genre.
   *
   * @throws ApiProxyException If there is an issue with the API proxy.
   */
  private void selectOptions() throws ApiProxyException {
    // get a list of all the available genres excluding the genre solution to choose from
    List<String> availableGenres = new ArrayList<>();
    Collections.addAll(availableGenres, genres);
    availableGenres.remove(genreSolution);

    Random random = new Random();

    // shuffle the order of the list
    Collections.shuffle(availableGenres, random);

    // get a random index for the correct solution button to be on
    int randomIndex = random.nextInt(availableGenres.size() - 1);
    this.correctGenreIndex = randomIndex + 1;
    availableGenres.add(randomIndex, genreSolution);
    // create a sublist of the respective selected genres
    selectedGenres = availableGenres.subList(0, 6);

    // get a list of every button
    List<Button> optionButtons =
        Arrays.asList(
            optionOneBtn, optionTwoBtn, optionThreeBtn, optionFourBtn, optionFiveBtn, optionSixBtn);

    // go through the selected genres and update the respective button to display the answer
    for (int i = 0; i < selectedGenres.size(); i++) {
      String genre = selectedGenres.get(i);
      Button button = optionButtons.get(i);
      button.setText((i + 1) + ". " + genre);
    }

    // create an array for the indexes of the buttons that are INCORRECT (for hint functionality)
    for (int i = 1; i <= 6; i++) {
      if (i != correctGenreIndex) {
        availableWrongButtons.add(i);
      }
    }

    System.out.println("Correct genre index: " + correctGenreIndex);
  }

  /**
   * Handles the click event for the "PLAY SONG" button, allowing the player to play or pause the
   * music.
   */
  @FXML
  private void onClickSongBtn() {
    // if already playing, we pause the music and update the button
    if (musicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
      musicPlayer.pause();
      songBtn.setText("PLAY SONG");
    } else {
      // if paused, we will start playing the music
      musicPlayer.play();
      songBtn.setText("PAUSE SONG");
    }
  }

  /** Sets cooldowns on all option buttons and starts a timer for the cooldown period. */
  private void setButtonCooldowns() {
    disableAllButtons();
    startCooldownTimer();
  }

  /** Disables all option buttons from the GUI, not allowing the player to interact with them. */
  private void disableAllButtons() {
    optionOneBtn.setDisable(true);
    optionTwoBtn.setDisable(true);
    optionThreeBtn.setDisable(true);
    optionFourBtn.setDisable(true);
    optionFiveBtn.setDisable(true);
    optionSixBtn.setDisable(true);
  }

  /** Starts a countdown timer for the button cooldown period, updating the GUI accordingly. */
  private void startCooldownTimer() {
    // will not start the countdown if it has already started.
    if (this.timerStarted) {
      return;
    }

    // create a new instance for the timer with a 20s timer to cooldown
    timer = new Timer();
    this.timerStarted = true;
    this.timeToAnswer = 20;

    // task for the update of the timer, decrements timer every second and updates GUI, aswell as
    // validating for any events that may have to be run.
    Task<Void> timerTask =
        new Task<Void>() {

          @Override
          // A task for the continuous decrementing of the timer, and updates all timer
          // value indicators for the GUI.
          protected Void call() throws Exception {
            timer.scheduleAtFixedRate(
                new TimerTask() {
                  @Override
                  public void run() {
                    if (timeToAnswer > 0) {
                      timeToAnswer--;
                      // indicate in the gui the time left before being able to answer again
                      Platform.runLater(
                          () -> {
                            cooldownLabel.setText(
                                "You can answer again in " + timeToAnswer + " seconds!");
                          });
                    } else {
                      // stop the countdown at 0 and allow the player to answer again
                      stopCountdown();
                      Platform.runLater(
                          () -> {
                            cooldownLabel.setText("You can attempt to answer again!");
                          });
                    }
                  }
                },
                1000,
                1000);
            return null;
          }
        };

    Thread timerThread = new Thread(timerTask, "TimerThread");
    timerThread.start();
  }

  /** Stops the countdown timer, re-enables option buttons, and hides the cooldown label. */
  public void stopCountdown() {
    // cancels / interrupts the thread relevant to timing
    if (timer != null) {
      timer.cancel();
    }

    if (timerThread != null) {
      timerThread.interrupt();
    }

    // set timerstarted to false and enable all the option buttons again.
    this.timerStarted = false;
    optionOneBtn.setDisable(false);
    optionTwoBtn.setDisable(false);
    optionThreeBtn.setDisable(false);
    optionFourBtn.setDisable(false);
    optionFiveBtn.setDisable(false);
    optionSixBtn.setDisable(false);
  }

  /**
   * Attempts to solve the music quiz with the given index and handles the outcome, such as correct
   * and incorrect selections.
   *
   * @param index The index of the selected answer.
   */
  private void attemptSolve(int index) {
    if (correctGenreIndex == index) {
      // if the selection is correct, notify the user through the GUI and notify the gamestate.
      // disable all now useless functionality.
      hintBtn.setVisible(false);
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. For you, I got this key for you man.");
      gameState.getObjectiveListManager().completeObjective1();
      answerLabel.setText("CORRECT");
      answerLabel.setTextFill(Color.GREEN);
      cooldownLabel.setVisible(false);
      disableAllButtons();
      return;
    }

    if (correctGenreIndex != index) {
      // if the selection is incorrect, notify the user that they are wrong and set the cooldowns.
      answerLabel.setText("INCORRECT");
      answerLabel.setTextFill(Color.RED);
      setButtonCooldowns();
    }
  }

  /**
   * Event handler for when the corresponding answer button is clicked, attempting to solve the quiz
   * with the respective index.
   */
  @FXML
  private void onClickOne() {
    System.out.println("1");
    attemptSolve(1);
  }

  /**
   * Event handler for when the corresponding answer button is clicked, attempting to solve the quiz
   * with the respective index.
   */
  @FXML
  private void onClickTwo() {
    System.out.println("2");
    attemptSolve(2);
  }

  /**
   * Event handler for when the corresponding answer button is clicked, attempting to solve the quiz
   * with the respective index.
   */
  @FXML
  private void onClickThree() {
    System.out.println("3");
    attemptSolve(3);
  }

  /**
   * Event handler for when the corresponding answer button is clicked, attempting to solve the quiz
   * with the respective index.
   */
  @FXML
  private void onClickFour() {
    System.out.println("4");
    attemptSolve(4);
  }

  /**
   * Event handler for when the corresponding answer button is clicked, attempting to solve the quiz
   * with the respective index.
   */
  @FXML
  private void onClickFive() {
    System.out.println("5");
    attemptSolve(5);
  }

  /**
   * Event handler for when the corresponding answer button is clicked, attempting to solve the quiz
   * with the respective index.
   */
  @FXML
  private void onClickSix() {
    System.out.println("6");
    attemptSolve(6);
  }

  /**
   * Deletes a single wrong option from the available buttons, making the game easier by providing a
   * hint.
   */
  private void deleteWrongOption() {
    // select a random wrong button to remove
    Collections.shuffle(availableWrongButtons);
    int toRemove = availableWrongButtons.remove(0);

    // switch case which sets the visibility of the selected button to false.
    switch (toRemove) {
      case 1:
        optionOneBtn.setVisible(false);
        break;
      case 2:
        optionTwoBtn.setVisible(false);
        break;
      case 3:
        optionThreeBtn.setVisible(false);
        break;
      case 4:
        optionFourBtn.setVisible(false);
        break;
      case 5:
        optionFiveBtn.setVisible(false);
        break;
      case 6:
        optionSixBtn.setVisible(false);
        break;
      default:
        break;
    }
    if (availableWrongButtons.size() <= 1) {
      hintBtn.setVisible(false);
    }
  }

  /**
   * Event handler for clicking the hint button, using a hint if available and deleting a wrong
   * option as a hint.
   */
  @FXML
  private void onClickHint() {
    System.out.println("hint");
    // check if the player has hints available
    if (this.gameState.getHintManager().getHintsRemaining() > 0) {
      this.gameState.getHintManager().useHint();
      // delete a wrong option upon using a hint
      deleteWrongOption();
    } else {
      speechBox.setText("Sorry bro, I don't have any more hints for you.");
    }
  }

  /**
   * Switches back to the Rave room scene when the "RETURN" button is clicked, pausing the music if
   * it's playing.
   *
   * @param event The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
    musicPlayer.pause();
    songBtn.setText("PLAY SONG");
  }
}
