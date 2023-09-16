package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RockBigTaskManager.Colour;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TaskManager.LargeTask;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class RockController {

  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle raveDoor;
  @FXML private Pane gameMasterPane;
  @FXML private Pane drumsPane;
  @FXML private Pane cyanGuitarPane;
  @FXML private Pane blueGuitarPane;
  @FXML private Pane purpleGuitarPane;
  @FXML private Pane yellowGuitarPane;
  @FXML private Pane amplifierPane;
  @FXML private Pane notePane;
  @FXML private Pane chatBoxPane;
  @FXML private Label colourLabel1;
  @FXML private Label colourLabel2;
  @FXML private Label colourLabel3;
  @FXML private Label colourLabel4;
  @FXML private Label timerLabel;
  @FXML private Label noteSequenceLabel;
  @FXML private TextArea textArea;
  @FXML private TextField textField;
  @FXML private ToggleButton toggleNoteBtn;
  @FXML private ImageView pointingArrowGif;
  @FXML private boolean chatOpened;

  private GameState gameState;
  private MediaPlayer guitarNotePlayer;
  private HashMap<Colour, Integer> orderColourMap;
  private ArrayList<String> audioNames;
  private String[] noteSequence;
  private int numberOfCorrectGuitarClicks = 0;

  @FXML
  private void initialize() throws ApiProxyException {
    gameState = GameState.getInstance();
    gameState.timeManager.addToTimers(timerLabel);
    gameState.chatManager.addTextArea(textArea);
    gameState.chatManager.addTextField(textField);
    gameState.chatManager.generateInitialMessage();
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      gameState.rockBigTaskManager.addAllRockTaskElements(
          colourLabel1,
          colourLabel2,
          colourLabel3,
          colourLabel4,
          notePane,
          toggleNoteBtn,
          noteSequenceLabel,
          pointingArrowGif);
    }
    audioNames = new ArrayList<String>();
    audioNames.add("c2");
    audioNames.add("d2");
    audioNames.add("e2");
    audioNames.add("f2");
    audioNames.add("g2");
    audioNames.add("a2");
    audioNames.add("b2");
    chatOpened = false;
  }

  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  @FXML
  private void onClickGameMaster(MouseEvent event) {
    System.out.println("game master clicked");
    toggleChat();
  }

  @FXML
  private void onClickDrums(MouseEvent event) {
    System.out.println("drums clicked");
  }

  @FXML
  private void onClickCyanGuitar(MouseEvent event) throws URISyntaxException {
    System.out.println("cyan guitar clicked");
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      playNote(Colour.CYAN);
      checkGuitarSequence(Colour.CYAN);
    } else {
      playRandomNote();
    }
  }

  @FXML
  private void onClickBlueGuitar(MouseEvent event) throws URISyntaxException {
    System.out.println("blue guitar clicked");
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      playNote(Colour.BLUE);
      checkGuitarSequence(Colour.BLUE);
    } else {
      playRandomNote();
    }
  }

  @FXML
  private void onClickPurpleGuitar(MouseEvent event) throws URISyntaxException {
    System.out.println("purple guitar clicked");
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      playNote(Colour.PURPLE);
      checkGuitarSequence(Colour.PURPLE);
    } else {
      playRandomNote();
    }
  }

  @FXML
  private void onClickYellowGuitar(MouseEvent event) throws URISyntaxException {
    System.out.println("yellow guitar clicked");
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      playNote(Colour.YELLOW);
      checkGuitarSequence(Colour.YELLOW);
    } else {
      playRandomNote();
    }
  }

  @FXML
  private void onClickAmplifier(MouseEvent event) {
    System.out.println("amplifier clicked");
  }

  @FXML
  private void toggleChat() {
    if (chatOpened) {
      chatBoxPane.setDisable(true);
      chatBoxPane.setOpacity(0);
    } else {
      chatBoxPane.setDisable(false);
      chatBoxPane.setOpacity(0.95);
    }
    chatOpened = !chatOpened;
  }

  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  @FXML
  public void onKeyReleased(KeyEvent event) throws ApiProxyException, IOException {
    System.out.println("key " + event.getCode() + " released");
    if (event.getCode() == KeyCode.ENTER && chatOpened) {
      System.out.println("Message Sent");
      gameState = GameState.getInstance();
      gameState.chatManager.onSendMessage(textField);
    }
  }

  @FXML
  private void onToggleNote() {
    gameState.rockBigTaskManager.setVisibilityNotePanes(true);
    gameState.rockBigTaskManager.setVisibilityArrows(false);
  }

  public void playNote(Colour guitarColour) throws URISyntaxException {
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {

      if (GameState.isRiddleObjectFound && !GameState.isNoteSequenceFound) {
        noteSequence = gameState.rockBigTaskManager.getNoteSequence();
        orderColourMap = gameState.rockBigTaskManager.getOrderColourMap();

        // play the note of the guitar clicked according to the note sequence
        String noteToPlay = noteSequence[orderColourMap.get(guitarColour) - 1];

        switch (noteToPlay) { // switch statement to play the note
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

  public void playRandomNote() throws URISyntaxException {
    String randomNote = audioNames.get((int) (Math.random() * 7));
    playGuitarNotePlayer(randomNote);
    System.out.println("Random note " + randomNote + " played");
  }

  public void playGuitarNotePlayer(String audioName) throws URISyntaxException {
    Media note =
        new Media(getClass().getResource("/sounds/" + audioName + ".mp3").toURI().toString());
    guitarNotePlayer = new MediaPlayer(note);
    guitarNotePlayer.play();
  }

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
}
