package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RockBigTaskManager;
import nz.ac.auckland.se206.RockBigTaskManager.Colour;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
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
  @FXML private TextArea textArea;
  @FXML private TextField textField;
  @FXML private ToggleButton toggleNoteBtn;
  @FXML private boolean chatOpened;

  private GameState gameState;

  @FXML
  private void initialize() throws ApiProxyException {
    gameState = GameState.getInstance();
    gameState.timeManager.addToTimers(timerLabel);
    gameState.chatManager.addTextArea(textArea);
    gameState.chatManager.addTextField(textField);
    gameState.chatManager.generateInitialMessage();
    gameState.rockBigTaskManager.addToColourLabels1(colourLabel1);
    gameState.rockBigTaskManager.addToColourLabels2(colourLabel2);
    gameState.rockBigTaskManager.addToColourLabels3(colourLabel3);
    gameState.rockBigTaskManager.addToColourLabels4(colourLabel4);
    gameState.rockBigTaskManager.addToNotePanes(notePane);
    gameState.rockBigTaskManager.addToNoteButtons(toggleNoteBtn);
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
  private void onClickCyanGuitar(MouseEvent event) {
    System.out.println("cyan guitar clicked");
    playNote(Colour.CYAN);
  }

  @FXML
  private void onClickBlueGuitar(MouseEvent event) {
    System.out.println("blue guitar clicked");
    playNote(Colour.BLUE);
  }

  @FXML
  private void onClickPurpleGuitar(MouseEvent event) {
    System.out.println("purple guitar clicked");
    playNote(Colour.PURPLE);
  }

  @FXML
  private void onClickYellowGuitar(MouseEvent event) {
    System.out.println("yellow guitar clicked");
    playNote(Colour.YELLOW);
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
    if (event.getCode() == KeyCode.ENTER) {
      System.out.println("Message Sent");
      gameState = GameState.getInstance();
      gameState.chatManager.onSendMessage(textField);
    }
  }

  @FXML
  private void onToggleNote() {
    if (toggleNoteBtn.isSelected()) {
      gameState.rockBigTaskManager.setVisibilityNotePanes(false);
    } else {
      gameState.rockBigTaskManager.setVisibilityNotePanes(true);
    }
  }

  public void playNote(Colour guitarColour) {
    if (GameState.isRiddleObjectFound) {
      RockBigTaskManager.Note[] noteSequence = gameState.rockBigTaskManager.getNoteSequence();
      HashMap<Colour, Integer> orderColourMap = gameState.rockBigTaskManager.getOrderColourMap();

      RockBigTaskManager.Note noteToPlay = noteSequence[orderColourMap.get(guitarColour) - 1];

      switch (noteToPlay) {
        case C:
          System.out.println("C note played");
          break;
        case D:
          System.out.println("D note played");
          break;
        case E:
          System.out.println("E note played");
          break;
        case F:
          System.out.println("F note played");
          break;
        case G:
          System.out.println("G note played");
          break;
        case A:
          System.out.println("A note played");
          break;
        case B:
          System.out.println("B note played");
          break;
      }
    }
  }
}
