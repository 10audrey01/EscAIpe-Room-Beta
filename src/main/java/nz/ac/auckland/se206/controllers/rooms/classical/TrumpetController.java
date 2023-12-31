package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.tasks.TrumpetTask;

/** Controller class for handling the trumpet room. */
public class TrumpetController {

  @FXML private ImageView trumpetButton1;
  @FXML private ImageView trumpetButton2;
  @FXML private ImageView trumpetButton3;
  @FXML private ImageView note1Symbol;
  @FXML private ImageView note2Symbol;
  @FXML private ImageView note3Symbol;
  @FXML private ImageView beamNote1;
  @FXML private ImageView beamNote2;
  @FXML private ImageView beamNote3;
  @FXML private ImageView beamNote4;
  @FXML private ImageView beamNote5;
  @FXML private ImageView playingMusicGif;
  @FXML private Pane leaveTrumpet;
  @FXML private Pane playTrumpet;
  @FXML private Label timerLabel;

  private boolean isButton1Down;
  private boolean isButton2Down;
  private boolean isButton3Down;

  private ArrayList<ImageView> beamNotes;
  private ArrayList<Integer> noteSequence;
  private Integer noteToPlay;
  private int currentNoteIndex;
  private MediaPlayer trumpetNotePlayer;
  private int taskIndex;

  private GameState gameState;

  /**
   * Initializes the TrumpetEventController by setting up the game state, labels, and other
   * game-related variables.
   *
   * @throws IOException If there is an issue with input/output.
   */
  @FXML
  private void initialize() throws IOException {
    // set gamestate and add timer for the controller
    gameState = GameState.getInstance();
    gameState.getTimeManager().addToTimers(timerLabel);
    taskIndex = gameState.getTaskManager().getTaskIndex(TrumpetTask.class);
    // set initial fields
    isButton1Down = false;
    isButton2Down = false;
    isButton3Down = false;
    beamNotes =
        new ArrayList<ImageView>(List.of(beamNote1, beamNote2, beamNote3, beamNote4, beamNote5));
    // generate the sequence of notes for the player to play this game
    generateNoteSequence();
    noteToPlay = noteSequence.get(0);
    currentNoteIndex = 0;
    setSymbols();
    setBeamNotes();
  }

  /** Helper function to generate the note sequence for the trumpet event task. */
  private void generateNoteSequence() {
    int numberToAdd;
    noteSequence = new ArrayList<Integer>();
    // randomly select 5 unique notes to add to sequence
    for (int i = 0; i < 5; i++) {
      numberToAdd =
          (int) (Math.random() * 2)
              + 10 * (int) (Math.random() * 2)
              + 100 * (int) (Math.random() * 2);
      while (noteSequence.contains(numberToAdd)) {
        // ensure notes are unique
        numberToAdd =
            (int) (Math.random() * 2)
                + 10 * (int) (Math.random() * 2)
                + 100 * (int) (Math.random() * 2);
      }
      noteSequence.add(numberToAdd);
    }
  }

  /**
   * Sets the symbols for the trumpet event based on the current note to play.
   *
   * @throws IOException If there is an issue with loading images.
   */
  private void setSymbols() throws IOException {
    // Load images for the musical notes (on and off)
    Image onNoteImage =
        new Image(App.class.getResource("/images/classicalRoom/Up.png").openStream());
    Image offNoteImage =
        new Image(App.class.getResource("/images/classicalRoom/Down.png").openStream());

    // Check the value of each noteToPlay digit and update the corresponding note symbol
    if (noteToPlay / 100 == 0) {
      Platform.runLater(
          () -> {
            note1Symbol.setImage(onNoteImage);
          });
    } else {
      Platform.runLater(
          () -> {
            note1Symbol.setImage(offNoteImage);
          });
    }
    if (noteToPlay / 10 % 10 == 0) {
      Platform.runLater(
          () -> {
            note2Symbol.setImage(onNoteImage);
          });
    } else {
      Platform.runLater(
          () -> {
            note2Symbol.setImage(offNoteImage);
          });
    }
    if (noteToPlay % 10 == 0) {
      Platform.runLater(
          () -> {
            note3Symbol.setImage(onNoteImage);
          });
    } else {
      Platform.runLater(
          () -> {
            note3Symbol.setImage(offNoteImage);
          });
    }
  }

  /**
   * Handles the event when one of the trumpet buttons is clicked to toggle its state.
   *
   * @param event The MouseEvent representing the button click event.
   * @throws IOException If there is an issue with loading images.
   */
  @FXML
  private void onClickedTrumpetButton1(MouseEvent event) throws IOException {
    // Toggle the state of trumpet button 1 (down/up)
    if (!isButton1Down) {
      // updates the trumpet image to the downview if the button is not down
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton1Down.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton1Down = true;
    } else {
      // otherwise update the trumper image to the upview as button is down
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton1Up.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton1Down = false;
    }
  }

  /**
   * Handles the event when one of the trumpet buttons is clicked to toggle its state.
   *
   * @param event The MouseEvent representing the button click event.
   * @throws IOException If there is an issue with loading images.
   */
  @FXML
  private void onClickedTrumpetButton2(MouseEvent event) throws IOException {
    // Toggle the state of trumpet button 2 (down/up)
    if (!isButton2Down) {
      // updates the trumpet image to the downview if the button is not down
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton2Down.png")
                  .openStream());
      Platform.runLater(
          // update the gui
          () -> {
            current.setImage(currentImage);
          });
      isButton2Down = true;
    } else {
      // otherwise update the trumper image to the upview as button is down
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton2Up.png")
                  .openStream());
      Platform.runLater(
          // update the gui
          () -> {
            current.setImage(currentImage);
          });
      isButton2Down = false;
    }
  }

  /**
   * Handles the event when one of the trumpet buttons is clicked to toggle its state.
   *
   * @param event The MouseEvent representing the button click event.
   * @throws IOException If there is an issue with loading images.
   */
  @FXML
  private void onClickedTrumpetButton3(MouseEvent event) throws IOException {
    // Toggle the state of trumpet button 3 (down/up)
    if (!isButton3Down) {
      // updates the trumpet image to the downview if the button is not down
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton3Down.png")
                  .openStream());
      Platform.runLater(
          // update the gui
          () -> {
            current.setImage(currentImage);
          });
      isButton3Down = true;
    } else {
      // otherwise update the trumper image to the upview as button is down
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton3Up.png")
                  .openStream());
      Platform.runLater(
          // update the gui
          () -> {
            current.setImage(currentImage);
          });
      isButton3Down = false;
    }
  }

  /**
   * Checks if the corresponding trumpet button plays the correct note based on the current game.
   *
   * @return True if the note is correct, false otherwise.
   */
  private boolean checkNote1() {
    // check if the note is the note to play
    if (noteToPlay / 100 == 1) {
      // return a boolean based on if the note is the correct note played
      if (isButton1Down) {
        return true;
      }
    } else {
      if (!isButton1Down) {
        return true;
      }
    }
    // return a false if it is incorrect
    return false;
  }

  /**
   * Checks if the corresponding trumpet button plays the correct note based on the current game.
   *
   * @return True if the note is correct, false otherwise.
   */
  private boolean checkNote2() {
    // check if the note is the note to play
    if (noteToPlay / 10 % 10 == 1) {
      // return a boolean based on if the note is the correct note played
      if (isButton2Down) {
        return true;
      }
    } else {
      if (!isButton2Down) {
        return true;
      }
    }
    // return a false if it is incorrect
    return false;
  }

  /**
   * Checks if the corresponding trumpet button plays the correct note based on the current game.
   *
   * @return True if the note is correct, false otherwise.
   */
  private boolean checkNote3() {
    // check if the note is the note to play
    if (noteToPlay % 10 == 1) {
      // return a boolean based on if the note is the correct note played
      if (isButton3Down) {
        return true;
      }
    } else {
      if (!isButton3Down) {
        return true;
      }
    }
    // return a false if it is incorrect
    return false;
  }

  /**
   * Handles the event when the "Play Trumpet" button is clicked to check if the notes have been
   * played correctly.
   */
  @FXML
  public void onClickedPlayTrumpet(MouseEvent event) throws URISyntaxException {
    System.out.println("Play Trumpet Clicked");
    playMusicGif();
    playNoteSound();
    // check if the notes have been played correctly
    if (checkNote1() && checkNote2() && checkNote3()) {
      System.out.println("Correct note played");
      correctNotePlayed();
    } else {
      // otherwise handle the player not inputting the correct note
      System.out.println("Incorrect note played");
      incorrectNotePlayed();
    }
  }

  /**
   * Helper for the event for playing the sound gif animation when the player clicks "Play Trumpet"
   * button after note is played.
   */
  @FXML
  public void playMusicGif() {
    // play the gif animation
    playingMusicGif.setVisible(true);
    // turn off the gif animation after 500ms
    new java.util.Timer()
        .schedule(
            new java.util.TimerTask() {
              @Override
              public void run() {
                playingMusicGif.setVisible(false);
              }
            },
            500);
  }

  /**
   * Helper for the playing the sound when the player clicks "Play Trumpet" button after note is
   * played. This will play the sound of the note to play.
   */
  @FXML
  public void playNoteSound() throws URISyntaxException {
    // check what note was played and change sound file to corresponding sound
    Integer noteplayed = 0;
    if (isButton1Down) {
      noteplayed += 100;
    }
    if (isButton2Down) {
      noteplayed += 10;
    }
    if (isButton3Down) {
      noteplayed += 1;
    }
    // get the correct audio sequence to get the audio file name for the played notes by the player
    String audioName = noteplayed.toString();
    if (noteplayed == 11) {
      audioName = "011";
    } else if (noteplayed == 10) {
      audioName = "010";
    } else if (noteplayed == 1) {
      audioName = "001";
    } else if (noteplayed == 0) {
      audioName = "000";
    }

    // load the notes from resources for the game to play
    Media note =
        new Media(
            getClass()
                .getResource("/sounds/trumpetSounds/" + audioName + ".wav")
                .toURI()
                .toString());
    // set the note as the current player and play the sound the player has inputted
    trumpetNotePlayer = new MediaPlayer(note);
    trumpetNotePlayer.setVolume(0.05);
    trumpetNotePlayer.play();
  }

  /**
   * Handles the case when the player inputs the correct note. This will increase the note index,
   * and updates the next note to play.
   */
  public void correctNotePlayed() {
    // increment the note index
    currentNoteIndex++;
    if (currentNoteIndex <= 4) {
      // if the sequence isn't completed, set the next note to play
      noteToPlay = noteSequence.get(currentNoteIndex);
    } else {
      // otherwise the sequence is completed and complete the task
      System.out.println("Trumpet completed");
      GameState.isTrumpetPlayed = true;
      gameState.getObjectiveListManager().completeObjective(taskIndex);
      hideAllSymbols();
    }

    // try to set the new symbols / beam notes for the player ui
    try {
      setSymbols();
      setBeamNotes();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Handles the case when the player inputs an incorrect note. */
  public void incorrectNotePlayed() {
    System.out.println(noteToPlay);
  }

  /** Helper function for setting the visibility of beam notes based on the current note index. */
  private void setBeamNotes() {
    for (int i = 0; i < beamNotes.size(); i++) {
      // update the respective views of the beams based on the current indices
      if (currentNoteIndex > i) {
        beamNotes.get(i).setOpacity(1);
      } else {
        beamNotes.get(i).setOpacity(0);
      }
    }
  }

  /** Hides all note symbols from the GUI by setting the opacity to 0. */
  private void hideAllSymbols() {
    Platform.runLater(
        () -> {
          note1Symbol.setOpacity(0);
          note2Symbol.setOpacity(0);
          note3Symbol.setOpacity(0);
        });
  }

  /**
   * Handles the event when the "Leave Trumpet" button is clicked to switch back to the classical
   * room.
   *
   * @param event The MouseEvent representing the button click event.
   * @throws IOException If there is an issue with switching scenes.
   */
  @FXML
  public void onClickedLeaveTrumpet(MouseEvent event) throws IOException {
    System.out.println("Leave Trumpet Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
