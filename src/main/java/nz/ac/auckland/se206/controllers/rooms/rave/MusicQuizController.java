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
  private GameState gamestate;

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

  // initialises the musicquiz controller
  @FXML
  private void initialize() throws IOException, URISyntaxException, ApiProxyException {
    // gets the gamestate and adds the relevant labels to the gamestate
    this.gamestate = GameState.getInstance();
    this.gamestate.getTimeManager().addToTimers(timerLabel);
    this.gamestate.getHintManager().addHintLabel(hintLabel);

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

  // selects the other incorrect options for the game
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

  // event handler for when the play song button is clicked
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

  // helper function that will set all buttons on cooldown and start the timer
  private void setButtonCooldowns() {
    disableAllButtons();
    startCooldownTimer();
  }

  // helper function that disables ALL of the option buttons
  private void disableAllButtons() {
    optionOneBtn.setDisable(true);
    optionTwoBtn.setDisable(true);
    optionThreeBtn.setDisable(true);
    optionFourBtn.setDisable(true);
    optionFiveBtn.setDisable(true);
    optionSixBtn.setDisable(true);
  }

  // function for starting a cooldown timer for the buttons
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

  // helper function to stop the countdown.
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

  // helper function to attempt solve the puzzle
  private void attemptSolve(int index) {
    if (correctGenreIndex == index) {
      // if the selection is correct, notify the user through the GUI and notify the gamestate.
      // disable all now useless functionality.
      hintBtn.setVisible(false);
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. For you, I got this key for you man.");
      gamestate.getObjectiveListManager().completeObjective1();
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

  // on clicking the first answer button
  @FXML
  private void onClickOne() {
    System.out.println("1");
    attemptSolve(1);
  }

  // on clicking the second answer button
  @FXML
  private void onClickTwo() {
    System.out.println("2");
    attemptSolve(2);
  }

  // on clicking the third answer button
  @FXML
  private void onClickThree() {
    System.out.println("3");
    attemptSolve(3);
  }

  // on clicking the fourth answer button
  @FXML
  private void onClickFour() {
    System.out.println("4");
    attemptSolve(4);
  }

  // on clicking the fifth answer button
  @FXML
  private void onClickFive() {
    System.out.println("5");
    attemptSolve(5);
  }

  // on clicking the sixth answer button
  @FXML
  private void onClickSix() {
    System.out.println("6");
    attemptSolve(6);
  }

  // deletes a singular wrong option from the buttons
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

  // on the clicking of the hint button
  @FXML
  private void onClickHint() {
    System.out.println("hint");
    if (this.gamestate.getHintManager().getHintsRemaining() > 0) {
      this.gamestate.getHintManager().useHint();
      deleteWrongOption();
    } else {
      speechBox.setText("Sorry bro, I don't have any hints for you man.");
    }
  }

  // switches back to the rave room
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
    musicPlayer.pause();
    songBtn.setText("PLAY SONG");
  }
}
