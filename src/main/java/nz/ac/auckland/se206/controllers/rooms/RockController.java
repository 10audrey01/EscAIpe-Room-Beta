package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RockBigTaskManager.Colour;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TaskManager.LargeTask;
import nz.ac.auckland.se206.controllers.rooms.classical.HarpController;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.tasks.RiddleTask;

/** Controller class for handling the rock room. */
public class RockController extends RoomController {

  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle raveDoor;
  @FXML private Pane guitaristPane;
  @FXML private Pane drumsPane;
  @FXML private Pane cyanGuitarPane;
  @FXML private Pane blueGuitarPane;
  @FXML private Pane purpleGuitarPane;
  @FXML private Pane yellowGuitarPane;
  @FXML private Pane amplifierPane;
  @FXML private Pane chatBoxPane;
  @FXML private Pane rockNotePane;
  @FXML private ImageView noteImage;
  @FXML private ImageView noteImage1;
  @FXML private Circle circle1;
  @FXML private Circle circle2;
  @FXML private VBox objectiveList;

  // fields for handling the rock room event
  private MediaPlayer guitarNotePlayer;
  private HashMap<Colour, Integer> orderColourMap;
  private ArrayList<String> audioNames;
  private ArrayList<Circle> circles;
  private String[] noteSequence;
  private int numberOfCorrectGuitarClicks = 0;

  // task index for the rock room task
  private int taskIndex;

  /**
   * Initializes the Rock Room's graphical components and game variables when the room is loaded.
   * This method is called automatically when the FXML file is loaded.
   *
   * @throws ApiProxyException If there is an error in generating the initial chat message.
   */
  @FXML
  private void initialize() throws ApiProxyException {

    initialiseAllGameStateVariables();
    taskIndex = gameState.getTaskManager().getTaskIndex(RiddleTask.class);

    gameState.getChatManager().generateInitialMessage();

    // create an array list of the notes
    audioNames = new ArrayList<String>();
    audioNames.add("c2");
    audioNames.add("d2");
    audioNames.add("e2");
    audioNames.add("f2");
    audioNames.add("g2");
    audioNames.add("a2");
    audioNames.add("b2");

    // create and set circles for the harp event
    circles = new ArrayList<Circle>(List.of(circle1, circle2));
    setCircles();
  }

  /**
   * Switches the scene to the Rave Room when the rave door is clicked.
   *
   * @param event The mouse event triggered by clicking the rave door.
   * @throws IOException If there is an error while switching scenes.
   */
  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    // switches the scene to the rave room scene
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  /**
   * Switches the scene to the Classical Room when the classical door is clicked.
   *
   * @param event The mouse event triggered by clicking the classical door.
   * @throws IOException If there is an error while switching scenes.
   */
  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    // switches the scene to the classical room scene
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  /**
   * Switches the scene to the Guitarist Room when the guitarist is clicked.
   *
   * @param event The mouse event triggered by clicking the guitarist.
   */
  @FXML
  private void onClickGuitarist(MouseEvent event) {
    // switches the scene to the guitarist scene
    System.out.println("guitarist clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.GUITARIST));
  }

  /**
   * Handles clicking on the drums.
   *
   * @param event The mouse event triggered by clicking the drums.
   */
  @FXML
  private void onClickDrums(MouseEvent event) {
    System.out.println("drums clicked");
  }

  /**
   * Handles clicking on the cyan guitar and checks the correctness of the guitar sequence for the
   * rock room task.
   *
   * @param event The mouse event triggered by clicking the cyan guitar.
   * @throws URISyntaxException If there is an issue with URIs.
   */
  @FXML
  private void onClickCyanGuitar(MouseEvent event) throws URISyntaxException {
    // checks the correctness of the guitar sequence for the rock room task
    System.out.println("cyan guitar clicked");
    if (gameState.getTaskManager().getCurrentLargeTask() == LargeTask.ROCK) {
      playNote(Colour.CYAN);
      checkGuitarSequence(Colour.CYAN);
    } else {
      playRandomNote();
    }
  }

  /**
   * Handles clicking on the blue guitar and checks the correctness of the guitar sequence for the
   * rock room task.
   *
   * @param event The mouse event triggered by clicking the blue guitar.
   * @throws URISyntaxException If there is an issue with URIs.
   */
  @FXML
  private void onClickBlueGuitar(MouseEvent event) throws URISyntaxException {
    // checks the correctness of the guitar sequence for the rock room task
    System.out.println("blue guitar clicked");
    if (gameState.getTaskManager().getCurrentLargeTask() == LargeTask.ROCK) {
      playNote(Colour.BLUE);
      checkGuitarSequence(Colour.BLUE);
    } else {
      playRandomNote();
    }
  }

  /**
   * Handles clicking on the purple guitar and checks the correctness of the guitar sequence for the
   * rock room task.
   *
   * @param event The mouse event triggered by clicking the purple guitar.
   * @throws URISyntaxException If there is an issue with URIs.
   */
  @FXML
  private void onClickPurpleGuitar(MouseEvent event) throws URISyntaxException {
    // checks the correctness of the guitar sequence for the rock room task
    System.out.println("purple guitar clicked");
    if (gameState.getTaskManager().getCurrentLargeTask() == LargeTask.ROCK) {
      playNote(Colour.PURPLE);
      checkGuitarSequence(Colour.PURPLE);
    } else {
      playRandomNote();
    }
  }

  /**
   * Handles clicking on the yellow guitar and checks the correctness of the guitar sequence for the
   * rock room task.
   *
   * @param event The mouse event triggered by clicking the yellow guitar.
   * @throws URISyntaxException If there is an issue with URIs.
   */
  @FXML
  private void onClickYellowGuitar(MouseEvent event) throws URISyntaxException {
    // checks the correctness of the guitar sequence for the rock room task
    System.out.println("yellow guitar clicked");
    if (gameState.getTaskManager().getCurrentLargeTask() == LargeTask.ROCK) {
      playNote(Colour.YELLOW);
      checkGuitarSequence(Colour.YELLOW);
    } else {
      playRandomNote();
    }
  }

  /**
   * Handles clicking on the amplifier.
   *
   * @param event The mouse event triggered by clicking the amplifier.
   */
  @FXML
  private void onClickAmplifier(MouseEvent event) {
    System.out.println("amplifier clicked");
  }

  /** Handles the toggling of the note. */
  @FXML
  private void onClickNote1() {
    gameState.getRockBigTaskManager().setVisibilityNotePanes(true);
    gameState.getRockBigTaskManager().setVisibilityNoteArrows(false);
  }

  /**
   * Handles playing a note on a guitar in the rock room.
   *
   * @param guitarColour The color of the guitar clicked.
   * @throws URISyntaxException If there is an issue with URIs.
   */
  public void playNote(Colour guitarColour) throws URISyntaxException {
    if (gameState.getTaskManager().getCurrentLargeTask() == LargeTask.ROCK) {

      if (GameState.isRiddleObjectFound && !GameState.isGuitarsPlayed) {
        noteSequence = gameState.getRockBigTaskManager().getNoteSequence();
        orderColourMap = gameState.getRockBigTaskManager().getOrderColourMap();

        // play the note of the guitar clicked according to the note sequence
        String noteToPlay = noteSequence[orderColourMap.get(guitarColour) - 1];

        switch (noteToPlay) { // switch statement to play any note
          case "C":
            System.out.println("C note played");
            playGuitarNotePlayer("c2");
            break;
          case "D":
            System.out.println("D note played");
            playGuitarNotePlayer("d2");
            break;
          case "E":
            System.out.println("E note played");
            playGuitarNotePlayer("e2");
            break;
          case "F":
            System.out.println("F note played");
            playGuitarNotePlayer("f2");
            break;
          case "G":
            System.out.println("G note played");
            playGuitarNotePlayer("g2");
            break;
          case "A":
            System.out.println("A note played");
            playGuitarNotePlayer("a2");
            break;
          case "B":
            System.out.println("B note played");
            playGuitarNotePlayer("b2");
            break;
        }
      } else { // play a random note if the note sequence is not found and the riddle object is not
        // found
        playRandomNote();
      }
    }
  }

  /**
   * Plays a random note on a guitar in the rock room.
   *
   * @throws URISyntaxException If there is an issue with URIs.
   */
  public void playRandomNote() throws URISyntaxException {
    String randomNote = audioNames.get((int) (Math.random() * 7));
    playGuitarNotePlayer(randomNote);
    System.out.println("Random note " + randomNote + " played");
  }

  /**
   * Plays the media for a specific note on the guitar.
   *
   * @param audioName The name of the audio file representing the note.
   * @throws URISyntaxException If there is an issue with URIs.
   */
  public void playGuitarNotePlayer(String audioName) throws URISyntaxException {
    Media note =
        new Media(getClass().getResource("/sounds/" + audioName + ".mp3").toURI().toString());
    guitarNotePlayer = new MediaPlayer(note);
    guitarNotePlayer.play();
  }

  /**
   * Checks the correctness of the guitar sequence in the rock room task.
   *
   * @param guitarColour The color of the guitar clicked.
   */
  public void checkGuitarSequence(Colour guitarColour) {
    if (GameState.isRiddleObjectFound && !GameState.isGuitarsPlayed) {
      // check if the guitar clicked is the correct one
      if (orderColourMap.get(guitarColour) - 1 == numberOfCorrectGuitarClicks) {
        // if it is, set the note sequence label to the note sequence
        gameState
            .getRockBigTaskManager()
            .setNoteSequenceLabels(noteSequence[orderColourMap.get(guitarColour) - 1].toString());
        numberOfCorrectGuitarClicks++;
      } else {
        // otherwise reset the labels
        gameState.getRockBigTaskManager().clearNoteSequenceLabels();
        numberOfCorrectGuitarClicks = 0;
      }

      if (numberOfCorrectGuitarClicks == 4) {
        GameState.isGuitarsPlayed = true;
        gameState.getObjectiveListManager().completeObjective(taskIndex);
        System.out.println("Correct sequence played");
      }
    }
  }

  /**
   * Handles clicking on the note in the rock room.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  public void onClickNote(MouseEvent event) {
    // switches the scene to the note scene
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCKNOTE));
  }

  /**
   * Sets the appearance of circles for the harp event in the rock room. Uses the HarpController to
   * retrieve colors and opacity settings.
   */
  public void setCircles() {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    for (int i = 0; i < circles.size(); i++) {
      circles.get(i).setFill(harpController.getColourIndex(i));
      circles.get(i).setOpacity(100);
    }
  }

  /**
   * Handles clicking on circle 1 during the harp event in the rock room. Updates the state of the
   * harp event using the HarpController.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  public void onClickedCircle1(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 1 clicked");
    harpController.setCirclesFound(0);
    circle1.setOpacity(0);
    circle1.setDisable(true);
  }

  /**
   * Handles clicking on circle 2 during the harp event in the rock room. Updates the state of the
   * harp event using the HarpController.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  public void onClickedCircle2(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 2 clicked");
    harpController.setCirclesFound(1);
    circle2.setOpacity(0);
    circle2.setDisable(true);
  }
}
