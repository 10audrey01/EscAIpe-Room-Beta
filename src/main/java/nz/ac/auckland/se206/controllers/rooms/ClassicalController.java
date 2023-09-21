package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.animation.AnimationTimer;
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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.DraggableMaker;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TaskManager.LargeTask;
import nz.ac.auckland.se206.controllers.rooms.classical.PianoController;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class ClassicalController {

  @FXML private Rectangle raveDoor;
  @FXML private Rectangle rockDoor;
  @FXML private Pane celloPane;
  @FXML private Pane celloStrings;
  @FXML private Pane celloBowPane;
  @FXML private Pane grandPianoPane;
  @FXML private Pane clarinetPane;
  @FXML private Pane harpPane;
  @FXML private Pane tambourinePane;
  @FXML private Pane trumpetPane;
  @FXML private Pane chatBoxPane;
  @FXML private Pane notePane;
  @FXML private Pane classicalNotePane;
  @FXML private Label colourLabel1;
  @FXML private Label colourLabel2;
  @FXML private Label colourLabel3;
  @FXML private Label colourLabel4;
  @FXML private Label timerLabel;
  @FXML private Label hintLabel;
  @FXML private Label noteSequenceLabel;
  @FXML private Label step1Label;
  @FXML private Label step2Label;
  @FXML private Label step3Label;
  @FXML private Label step4Label;
  @FXML private TextArea textArea;
  @FXML private TextField textField;
  @FXML private ToggleButton toggleNoteBtn;
  @FXML private ImageView pointingArrowGif;
  @FXML private ImageView noteImage;
  @FXML private ImageView step1BlueKey;
  @FXML private ImageView step2GreenKey;
  @FXML private ImageView step3RedKey;
  @FXML private ImageView step4YellowKey;
  @FXML private ImageView gmSprite;
  @FXML private VBox objectiveList;
  private boolean chatOpened;

  // put these into gamestate later
  private boolean isTambourineBreakable;
  private int numOfTambourinePresses;
  private int tambourineLimit;

  private GameState gameState;

  private DraggableMaker draggableMaker = new DraggableMaker();

  private AnimationTimer celloPlayTimer =
      new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
          checkCollision(celloBowPane, celloStrings);
        }
      };

  // function for initialising room FXML
  @FXML
  private void initialize() {
    // add all relevant labels to gamestate instance
    gameState = GameState.getInstance();
    gameState.addInitialLabels(timerLabel, hintLabel, textArea, textField, gmSprite);

    // add objective labels and steps to the objective list manager
    gameState.objectiveListManager.addObjectiveLabel1(step1Label);
    gameState.objectiveListManager.addObjectiveLabel2(step2Label);
    gameState.objectiveListManager.addObjectiveLabel3(step3Label);
    gameState.objectiveListManager.addObjectiveLabel4(step4Label);
    gameState.objectiveListManager.addStep1Key(step1BlueKey);
    gameState.objectiveListManager.addStep2Key(step2GreenKey);
    gameState.objectiveListManager.addStep3Key(step3RedKey);
    gameState.objectiveListManager.addStep4Key(step4YellowKey);

    // if the  gamestate is the rock large task, add the elements to the rock task manager
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

    // initial states for fields
    chatOpened = false;
    numOfTambourinePresses = 0;
  }

  // function which makes the cello bow and tambourine pane draggable
  private void makeObjectsDraggable() {
    draggableMaker.makeDraggable(celloBowPane);
    draggableMaker.makeDraggable(tambourinePane);
    celloPlayTimer.start();
  }

  // function which checks if the panes for the bow and string collide
  protected void checkCollision(Pane pane1, Pane pane2) {
    if (pane1.getBoundsInParent().intersects(pane2.getBoundsInParent()) && pane1.isPressed()) {
      System.out.println("Bow and Strings Collided!!");
    }
  }

  // function which resets the puzzle state and switches to the rave room
  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    numOfTambourinePresses = 0;
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  // function which resets the puzzle state and switches to the rock room
  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    numOfTambourinePresses = 0;
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

  // function for handling cello clicked
  @FXML
  private void doClickedCello(MouseEvent event) throws IOException {
    System.out.println("Cello Clicked");
  }

  // function for handling cello bow clicked
  @FXML
  private void doClickedCelloBow(MouseEvent event) throws IOException {
    System.out.println("Cello Bow Clicked");
  }

  // function for handling clarinet being clicked
  @FXML
  private void doClickedClarinet(MouseEvent event) throws IOException {
    System.out.println("Clarinet Clicked");
  }

  // function for handling grand piano clicked
  @FXML
  private void doClickedGrandPiano(MouseEvent event) throws IOException {
    // changes the scene to the piano scene controller.
    System.out.println("Grand Piano Clicked");
    PianoController.resetNotesPlayed();

    PianoController pianoController = (PianoController) SceneManager.getController(AppUi.PIANO);
    pianoController.loadRockNotes();

    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();

    currentScene.setRoot(SceneManager.getUiRoot(AppUi.PIANO));
  }

  // function for handling trumpet clicked
  @FXML
  private void doClickedTrumpet(MouseEvent event) throws IOException {
    // changes the scene to the trumpet scene controller.
    System.out.println("Trumpet Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();

    currentScene.setRoot(SceneManager.getUiRoot(AppUi.TRUMPET));
  }

  // function for handling harp clicked
  @FXML
  private void doClickedHarp(MouseEvent event) throws IOException {
    // changes the scene to the harp controller scene
    System.out.println("Harp Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();

    currentScene.setRoot(SceneManager.getUiRoot(AppUi.HARP));
  }

  // function for handling tambourine clicked
  @FXML
  private void doClickedTambourine(MouseEvent event) throws IOException {
    // increments the times tambourine has been clicked
    System.out.println("Tambourine Clicked");
    numOfTambourinePresses++;
  }

  // function which toggles the visibility of the chatbox
  @FXML
  private void toggleChat() {
    if (chatOpened) {
      // if the chat is opened already, close it
      chatBoxPane.setDisable(true);
      chatBoxPane.setOpacity(0);
    } else {
      // otherwise show the chat
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

  // function for handling if the player presses enter - for sending messages to the gm
  @FXML
  public void onKeyReleased(KeyEvent event) throws ApiProxyException, IOException {
    System.out.println("key " + event.getCode() + " released");
    if (event.getCode() == KeyCode.ENTER && chatOpened) {
      System.out.println("Message Sent");
      gameState = GameState.getInstance();
      gameState.chatManager.onSendMessage(textField);
    }
  }

  // function for handling clicking the note
  @FXML
  public void onClickNote(MouseEvent event) {
    // changes the scene to the scene for the note
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICALNOTE));
  }

  // function for handling the note toggling event
  @FXML
  private void onToggleNote() {
    gameState.rockBigTaskManager.setVisibilityNotePanes(true);
    gameState.rockBigTaskManager.setVisibilityArrows(false);
  }
}
