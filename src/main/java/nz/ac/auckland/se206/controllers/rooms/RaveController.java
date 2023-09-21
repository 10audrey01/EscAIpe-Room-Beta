package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.rooms.classical.HarpController;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class RaveController {
  private static ArrayList<String> objects;
  private static String riddleObject;

  public static String getRiddleObject() {
    riddleObject = objects.get((int) (Math.random() * objects.size()));
    System.out.println("Riddle object: " + riddleObject);
    return riddleObject;
  }

  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle rockDoor;
  @FXML private Pane posterPane;
  @FXML private Pane djPane;
  @FXML private Pane bodybuilderPane;
  @FXML private Pane bouncerPane;
  @FXML private Pane discoPane;
  @FXML private Pane speakerPane;
  @FXML private Pane chatBoxPane;
  @FXML private Pane notePane;
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
  @FXML private ImageView gmSprite;
  @FXML private ImageView doorImage;
  @FXML private ImageView greenLock;
  @FXML private ImageView redLock;
  @FXML private ImageView blueLock;
  @FXML private ImageView yellowLock;
  @FXML private ImageView step1BlueKey;
  @FXML private ImageView step2GreenKey;
  @FXML private ImageView step3RedKey;
  @FXML private ImageView step4YellowKey;
  @FXML private ImageView openedDoor;
  @FXML private ImageView noteImage1;
  @FXML private TextArea textArea;
  @FXML private TextField textField;
  @FXML private ImageView pointingArrowGif;
  @FXML private boolean chatOpened;
  @FXML private Circle circle3, circle4, circle5;
  @FXML private VBox objectiveList;

  private ArrayList<Circle> circles;

  // gamestate instance and the state of the exit locks
  private GameState gameState;
  private boolean isRedLockUnlocked = false;
  private boolean isGreenLockUnlocked = false;
  private boolean isBlueLockUnlocked = false;
  private boolean isYellowLockUnlocked = false;

  // initialises the fxml file
  @FXML
  private void initialize() {
    // add the gamestate instance and relevant components to the gamestate/managers
    gameState = GameState.getInstance();
    gameState.addInitialLabels(timerLabel, hintLabel, textArea, textField, gmSprite);
    // add objectives / steps labels to the objective list manager
    gameState.getObjectiveListManager().addObjectiveLabel1(step1Label);
    gameState.getObjectiveListManager().addObjectiveLabel2(step2Label);
    gameState.getObjectiveListManager().addObjectiveLabel3(step3Label);
    gameState.getObjectiveListManager().addObjectiveLabel4(step4Label);
    gameState.getObjectiveListManager().addStep1Key(step1BlueKey);
    gameState.getObjectiveListManager().addStep2Key(step2GreenKey);
    gameState.getObjectiveListManager().addStep3Key(step3RedKey);
    gameState.getObjectiveListManager().addStep4Key(step4YellowKey);
    // add elements needed for the rock room task
    gameState
        .getRockBigTaskManager()
        .addAllRockTaskElements(
            colourLabel1,
            colourLabel2,
            colourLabel3,
            colourLabel4,
            notePane,
            noteImage1,
            noteSequenceLabel,
            pointingArrowGif);

    chatOpened = true;

    // create an array list of objects in the room
    objects = new ArrayList<String>();
    objects.add("band poster");
    objects.add("bouncer");
    objects.add("disco ball");
    objects.add("speakers");

    // set circles for classical room task
    circles = new ArrayList<Circle>(List.of(circle3, circle4, circle5));
    setCircles();
  }

  // function for handling clicking the poster event
  @FXML
  private void onClickPoster(MouseEvent event) {
    System.out.println("poster clicked");
    isRiddleObject("band poster");
  }

  // function for handing clicking the dj
  @FXML
  private void onClickDj(MouseEvent event) {
    System.out.println("dj clicked");
    // switches the scene to the music quiz scene
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.MUSICQUIZ));
  }

  // function for handing clicking the bodybuilder
  @FXML
  private void onClickBodybuilder(MouseEvent event) {
    System.out.println("bodybuilder clicked");
    // switches the scene to the bodybuilder safe minigame scene
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.BODYBUILDER));
  }

  // function for handing clicking the bouncer
  @FXML
  private void onClickBouncer(MouseEvent event) {
    System.out.println("bouncer clicked");
    isRiddleObject("bouncer");
  }

  // function for handing clicking the disco ball
  @FXML
  private void onClickDisco(MouseEvent event) {
    System.out.println("disco clicked");
    isRiddleObject("disco ball");
  }

  // function for handing clicking the speaker
  @FXML
  private void onClickSpeaker(MouseEvent event) {
    System.out.println("speaker clicked");
    isRiddleObject("speakers");
  }

  // function for handing clicking the red lock
  @FXML
  private void onClickRed(MouseEvent event) {
    // removes the lock if the player has completed the piano task
    if (GameState.isPianoPlayed) {
      gameState.getObjectiveListManager().setVisibilityKeyRed3(false);
      redLock.setVisible(false);
      isRedLockUnlocked = true;
    }
    System.out.println("red clicked");
  }

  // function for handing clicking the green lock
  @FXML
  private void onClickGreen(MouseEvent event) {
    // removes the lock if the player has completed the safe task
    if (GameState.isSafeOpened) {
      gameState.getObjectiveListManager().setVisibilityKeyGreen2(false);
      greenLock.setVisible(false);
      isGreenLockUnlocked = true;
    }
    System.out.println("green clicked");
  }

  // function for handing clicking the blue lock
  @FXML
  private void onClickBlue(MouseEvent event) {
    // removes the lock if the player has completed the music quiz
    if (GameState.isMusicQuizCompleted) {
      gameState.getObjectiveListManager().setVisibilityKeyBlue1(false);
      blueLock.setVisible(false);
      isBlueLockUnlocked = true;
    }
    System.out.println("blue clicked");
  }

  // function for handing clicking the yellow lock
  @FXML
  private void onClickYellow(MouseEvent event) {
    // removes the lock if player has completed the harp event
    if (GameState.isHarpPlayed) {
      gameState.getObjectiveListManager().setVisibilityKeyYellow4(false);
      yellowLock.setVisible(false);
      isYellowLockUnlocked = true;
    }
    System.out.println("yellow clicked");
  }

  // function for handing clicking the door
  @FXML
  private void onClickDoor(MouseEvent event) {
    // unlocks the door if the player has unlocked all the locks
    if (isRedLockUnlocked && isGreenLockUnlocked && isBlueLockUnlocked && isYellowLockUnlocked) {
      openedDoor.setDisable(false);
      openedDoor.setVisible(true);
      System.out.println("door unlocked");
    }
    System.out.println("door clicked");
  }

  // function for handing clicking the opened door
  @FXML
  private void onClickOpenedDoor(MouseEvent event) throws IOException {
    // sets the game won state to true, changing the scene to the end game scene
    GameState.isEscaped = true;
    App.setRoot("end");
    gameState.getTimeManager().stopCountdown();
    System.out.println("opened door clicked");
  }

  // function for handing clicking the classical door
  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    // changes the scene to the classical room
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  // function for handing clicking the rock door
  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    // changes the scene to the rock room
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

  // function for handing the toggling of the chat
  @FXML
  private void toggleChat() {
    if (chatOpened) {
      // closes the chat if it is opened
      chatBoxPane.setDisable(true);
      chatBoxPane.setOpacity(0);
    } else {
      // else open the chat
      chatBoxPane.setDisable(false);
      chatBoxPane.setOpacity(0.95);
    }
    chatOpened = !chatOpened;
  }

  // function for handing key presses
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  // function for handing releasing the enter key
  @FXML
  public void onKeyReleased(KeyEvent event) throws ApiProxyException, IOException {
    // sends message to chat if the player releases enter, allowing them to chat with enter.
    System.out.println("key " + event.getCode() + " released");
    if (event.getCode() == KeyCode.ENTER && chatOpened) {
      System.out.println("Message Sent");
      gameState = GameState.getInstance();
      gameState.getChatManager().onSendMessage(textField);
    }
  }

  // function for toggling note visibility
  @FXML
  private void onClickNote1() {
    gameState.getRockBigTaskManager().setVisibilityNotePanes(true);
    gameState.getRockBigTaskManager().setVisibilityArrows(false);
  }

  // helper function to check for if the riddle object selected is the clicked on object
  public void isRiddleObject(String object) {
    if (riddleObject.equals(object) && GameState.isRiddleResolved) {
      // if the object is the correct one, set relevant labels to notify the user
      GameState.isRiddleObjectFound = true;
      gameState.getRockBigTaskManager().setLabelColours();
      gameState.getRockBigTaskManager().setOrderColourMap();
      gameState.getRockBigTaskManager().setVisibilityNoteImages(true);
      gameState.getRockBigTaskManager().setVisibilityArrows(true);
    }
  }

  // function for setting the circles to set up the harp game
  public void setCircles() {
    // select random colours for each circle and fills them
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    for (int i = 0; i < circles.size(); i++) {
      circles.get(i).setFill(harpController.getColourIndex(i + 2));
      circles.get(i).setOpacity(100);
    }
  }

  // function for handing clicking the third circle
  @FXML
  public void onClickedCircle3(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 3 clicked");
    harpController.setCirclesFound(2);
    circle3.setOpacity(0);
    circle3.setDisable(true);
  }

  // function for handing clicking the fourth circle
  @FXML
  public void onClickedCircle4(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 4 clicked");
    harpController.setCirclesFound(3);
    circle4.setOpacity(0);
    circle4.setDisable(true);
  }

  // function for handing clicking the fifth circle
  @FXML
  public void onClickedCircle5(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 5 clicked");
    harpController.setCirclesFound(4);
    circle5.setOpacity(0);
    circle5.setDisable(true);
  }
}
