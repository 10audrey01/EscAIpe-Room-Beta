package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

public class RockController {

  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle raveDoor;
  @FXML private Pane guitaristPane;
  @FXML private Pane drumsPane;
  @FXML private Pane cyanGuitarPane;
  @FXML private Pane blueGuitarPane;
  @FXML private Pane purpleGuitarPane;
  @FXML private Pane yellowGuitarPane;
  @FXML private Pane amplifierPane;
  @FXML private Pane notePane;
  @FXML private Pane chatBoxPane;
  @FXML private Pane rockNotePane;
  @FXML private Label colourLabel1;
  @FXML private Label colourLabel2;
  @FXML private Label colourLabel3;
  @FXML private Label colourLabel4;
  @FXML private Label noteSequenceLabel;
  @FXML private Label timerLabel;
  @FXML private Label hintLabel;
  @FXML private Label step1Label;
  @FXML private Label step2Label;
  @FXML private Label step3Label;
  @FXML private Label step4Label;
  @FXML private TextArea textArea;
  @FXML private TextField textField;
  @FXML private ImageView pointingArrowGif;
  @FXML private ImageView noteImage;
  @FXML private ImageView noteImage1;
  @FXML private ImageView gmSprite;
  @FXML private ImageView step1BlueKey;
  @FXML private ImageView step2GreenKey;
  @FXML private ImageView step3RedKey;
  @FXML private ImageView step4YellowKey;
  @FXML private boolean chatOpened;
  @FXML private Circle circle1, circle2;
  @FXML private VBox objectiveList;

  // fields for handling the rock room event
  private GameState gameState;
  private MediaPlayer guitarNotePlayer;
  private HashMap<Colour, Integer> orderColourMap;
  private ArrayList<String> audioNames;
  private ArrayList<Circle> circles;
  private String[] noteSequence;
  private int numberOfCorrectGuitarClicks = 0;

  @FXML
  private void initialize() throws ApiProxyException {
    // intialising game state and adding relevant labels
    gameState = GameState.getInstance();
    gameState.addInitialLabels(timerLabel, hintLabel, textArea, textField, gmSprite);
    gameState.chatManager.generateInitialMessage();
    // adding labels for objective list to the objective list manager
    gameState.addObjectiveListLabels(
        step1Label,
        step2Label,
        step3Label,
        step4Label,
        step1BlueKey,
        step2GreenKey,
        step3RedKey,
        step4YellowKey);
    // if the task is the rock room task, add the relevant fields to the task manager
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      gameState.rockBigTaskManager.addAllRockTaskElements(
          colourLabel1,
          colourLabel2,
          colourLabel3,
          colourLabel4,
          notePane,
          noteImage1,
          noteSequenceLabel,
          pointingArrowGif);
    }

    // create an array list of the notes
    audioNames = new ArrayList<String>();
    audioNames.add("c2");
    audioNames.add("d2");
    audioNames.add("e2");
    audioNames.add("f2");
    audioNames.add("g2");
    audioNames.add("a2");
    audioNames.add("b2");
    chatOpened = false;

    // create and set circles for the harp event
    circles = new ArrayList<Circle>(List.of(circle1, circle2));
    setCircles();
  }

  // function for handing clicking the rave door
  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    // switches the scene to the rave room scene
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  // function for handing clicking the classical door
  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    // switches the scene to the classical room scene
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  // function for handing clicking the guitarist
  @FXML
  private void onClickGuitarist(MouseEvent event) {
    // switches the scene to the guitarist scene
    System.out.println("guitarist clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.GUITARIST));
  }

  // function for handing clicking the drums
  @FXML
  private void onClickDrums(MouseEvent event) {
    System.out.println("drums clicked");
  }

  // function for handing clicking the cyan guitar
  @FXML
  private void onClickCyanGuitar(MouseEvent event) throws URISyntaxException {
    // checks the correctness of the guitar sequence for the rock room task
    System.out.println("cyan guitar clicked");
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      playNote(Colour.CYAN);
      checkGuitarSequence(Colour.CYAN);
    } else {
      playRandomNote();
    }
  }

  // function for handing clicking the blue guitar
  @FXML
  private void onClickBlueGuitar(MouseEvent event) throws URISyntaxException {
    // checks the correctness of the guitar sequence for the rock room task
    System.out.println("blue guitar clicked");
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      playNote(Colour.BLUE);
      checkGuitarSequence(Colour.BLUE);
    } else {
      playRandomNote();
    }
  }

  // function for handing clicking the purple guitar
  @FXML
  private void onClickPurpleGuitar(MouseEvent event) throws URISyntaxException {
    // checks the correctness of the guitar sequence for the rock room task
    System.out.println("purple guitar clicked");
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      playNote(Colour.PURPLE);
      checkGuitarSequence(Colour.PURPLE);
    } else {
      playRandomNote();
    }
  }

  // function for handing clicking the yellow guitar
  @FXML
  private void onClickYellowGuitar(MouseEvent event) throws URISyntaxException {
    // checks the correctness of the guitar sequence for the rock room task
    System.out.println("yellow guitar clicked");
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      playNote(Colour.YELLOW);
      checkGuitarSequence(Colour.YELLOW);
    } else {
      playRandomNote();
    }
  }

  // function for handing clicking the amplifier
  @FXML
  private void onClickAmplifier(MouseEvent event) {
    System.out.println("amplifier clicked");
  }

  // function to handle the †oggling of the chat
  @FXML
  private void toggleChat() {
    if (chatOpened) {
      // if the chat is opened, close it
      chatBoxPane.setDisable(true);
      chatBoxPane.setOpacity(0);
    } else {
      // otherwise open the chat
      chatBoxPane.setDisable(false);
      chatBoxPane.setOpacity(0.95);
    }
    chatOpened = !chatOpened;
  }

  // function for handling key presses
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  // function for handling key release
  @FXML
  public void onKeyReleased(KeyEvent event) throws ApiProxyException, IOException {
    // if the key is enter, send the player's message to the chat
    System.out.println("key " + event.getCode() + " released");
    if (event.getCode() == KeyCode.ENTER && chatOpened) {
      System.out.println("Message Sent");
      gameState = GameState.getInstance();
      gameState.chatManager.onSendMessage(textField);
    }
  }

  // function for handling the toggling of the note
  @FXML
  private void onClickNote1() {
    gameState.rockBigTaskManager.setVisibilityNotePanes(true);
    gameState.rockBigTaskManager.setVisibilityArrows(false);
  }

  // function for handling playing of a note
  public void playNote(Colour guitarColour) throws URISyntaxException {
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {

      if (GameState.isRiddleObjectFound && !GameState.isNoteSequenceFound) {
        noteSequence = gameState.rockBigTaskManager.getNoteSequence();
        orderColourMap = gameState.rockBigTaskManager.getOrderColourMap();

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

  // function which plays a random note
  public void playRandomNote() throws URISyntaxException {
    String randomNote = audioNames.get((int) (Math.random() * 7));
    playGuitarNotePlayer(randomNote);
    System.out.println("Random note " + randomNote + " played");
  }

  // function which plays the media for the note
  public void playGuitarNotePlayer(String audioName) throws URISyntaxException {
    Media note =
        new Media(getClass().getResource("/sounds/" + audioName + ".mp3").toURI().toString());
    guitarNotePlayer = new MediaPlayer(note);
    guitarNotePlayer.play();
  }

  // function for checking the guitar sequence
  public void checkGuitarSequence(Colour guitarColour) {
    if (GameState.isRiddleObjectFound && !GameState.isNoteSequenceFound) {
      // check if the guitar clicked is the correct one
      if (orderColourMap.get(guitarColour) - 1 == numberOfCorrectGuitarClicks) {
        // if it is, set the note sequence label to the note sequence
        gameState.rockBigTaskManager.setNoteSequenceLabels(
            noteSequence[orderColourMap.get(guitarColour) - 1].toString());
        numberOfCorrectGuitarClicks++;
      } else {
        // otherwise reset the labels
        gameState.rockBigTaskManager.clearNoteSequenceLabels();
        numberOfCorrectGuitarClicks = 0;
      }

      if (numberOfCorrectGuitarClicks == 4) {
        GameState.isNoteSequenceFound = true;
        System.out.println("Correct sequence played");
      }
    }
  }

  // function for handling the clicking of the note
  @FXML
  public void onClickNote(MouseEvent event) {
    // switches the scene to the note scene
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCKNOTE));
  }

  // function for handling the setting of the circles for the harp event
  public void setCircles() {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    for (int i = 0; i < circles.size(); i++) {
      circles.get(i).setFill(harpController.getColourIndex(i));
      circles.get(i).setOpacity(100);
    }
  }

  // function for handling clicking circle 1
  @FXML
  public void onClickedCircle1(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 1 clicked");
    harpController.setCirclesFound(0);
    circle1.setOpacity(0);
    circle1.setDisable(true);
  }

  // function for handling clicking circle 2
  @FXML
  public void onClickedCircle2(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 2 clicked");
    harpController.setCirclesFound(1);
    circle2.setOpacity(0);
    circle2.setDisable(true);
  }
}
