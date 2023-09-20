package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TaskManager.LargeTask;
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
  @FXML private TextArea textArea;
  @FXML private TextField textField;
  @FXML private ToggleButton toggleNoteBtn;
  @FXML private ImageView pointingArrowGif;
  @FXML private boolean chatOpened;
  @FXML private Circle circle3, circle4, circle5;
  @FXML private VBox objectiveList;

  private ArrayList<Circle> circles;

  private GameState gameState;
  private boolean isRedLockUnlocked = false;
  private boolean isGreenLockUnlocked = false;
  private boolean isBlueLockUnlocked = false;
  private boolean isYellowLockUnlocked = false;

  @FXML
  private void initialize() {
    gameState = GameState.getInstance();
    gameState.timeManager.addToTimers(timerLabel);
    gameState.hintManager.addHintLabel(hintLabel);
    gameState.chatManager.addTextArea(textArea);
    gameState.chatManager.addTextField(textField);
    gameState.chatManager.addSprite(gmSprite);
    gameState.objectiveListManager.addObjectiveLabel1(step1Label);
    gameState.objectiveListManager.addObjectiveLabel2(step2Label);
    gameState.objectiveListManager.addObjectiveLabel3(step3Label);
    gameState.objectiveListManager.addObjectiveLabel4(step4Label);
    gameState.objectiveListManager.addStep1Key(step1BlueKey);
    gameState.objectiveListManager.addStep2Key(step2GreenKey);
    gameState.objectiveListManager.addStep3Key(step3RedKey);
    gameState.objectiveListManager.addStep4Key(step4YellowKey);
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
    chatOpened = false;

    objects = new ArrayList<String>();
    objects.add("band poster");
    objects.add("bouncer");
    objects.add("disco ball");
    objects.add("speakers");

    circles = new ArrayList<Circle>(List.of(circle3, circle4, circle5));
    setCircles();
  }

  @FXML
  private void onClickPoster(MouseEvent event) {
    System.out.println("poster clicked");
    isRiddleObject("poster");
  }

  @FXML
  private void onClickDj(MouseEvent event) {
    System.out.println("dj clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.MUSICQUIZ));
  }

  @FXML
  private void onClickBodybuilder(MouseEvent event) {
    System.out.println("bodybuilder clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.BODYBUILDER));
  }

  @FXML
  private void onClickBouncer(MouseEvent event) {
    System.out.println("bouncer clicked");
    isRiddleObject("bouncer");
  }

  @FXML
  private void onClickDisco(MouseEvent event) {
    System.out.println("disco clicked");
    isRiddleObject("disco");
  }

  @FXML
  private void onClickSpeaker(MouseEvent event) {
    System.out.println("speaker clicked");
    isRiddleObject("speaker");
  }

  @FXML
  private void onClickRed(MouseEvent event) {
    if (GameState.isPianoPlayed) {
      gameState.objectiveListManager.setVisibilityKeyRed3(false);
      redLock.setVisible(false);
      isRedLockUnlocked = true;
    }
    System.out.println("red clicked");
  }

  @FXML
  private void onClickGreen(MouseEvent event) {
    if (GameState.isSafeOpened) {
      gameState.objectiveListManager.setVisibilityKeyGreen2(false);
      greenLock.setVisible(false);
      isGreenLockUnlocked = true;
    }
    System.out.println("green clicked");
  }

  @FXML
  private void onClickBlue(MouseEvent event) {
    if (GameState.isMusicQuizCompleted) {
      gameState.objectiveListManager.setVisibilityKeyBlue1(false);
      blueLock.setVisible(false);
      isBlueLockUnlocked = true;
    }
    System.out.println("blue clicked");
  }

  @FXML
  private void onClickYellow(MouseEvent event) {
    if (GameState.isHarpPlayed) {
      gameState.objectiveListManager.setVisibilityKeyYellow4(false);
      yellowLock.setVisible(false);
      isYellowLockUnlocked = true;
    }
    System.out.println("yellow clicked");
  }

  @FXML
  private void onClickDoor(MouseEvent event) {
    if (isRedLockUnlocked && isGreenLockUnlocked && isBlueLockUnlocked && isYellowLockUnlocked) {
      openedDoor.setDisable(false);
      openedDoor.setVisible(true);
      System.out.println("door unlocked");
    }
    System.out.println("door clicked");
  }

  @FXML
  private void onClickOpenedDoor(MouseEvent event) throws IOException {
    GameState.isEscaped = true;
    App.setRoot("end");
    gameState.timeManager.stopCountdown();
    System.out.println("opened door clicked");
  }

  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
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

  public void isRiddleObject(String object) {
    if (gameState.taskManager.largeTask == LargeTask.ROCK) {
      if (riddleObject.equals(object)) { // } && GameState.isRiddleResolved) {
        GameState.isRiddleObjectFound = true;
        gameState.rockBigTaskManager.setLabelColours();
        gameState.rockBigTaskManager.setOrderColourMap();
        gameState.rockBigTaskManager.setDisableNoteButtons(false);
        gameState.rockBigTaskManager.setVisibilityNoteButtons(true);
        gameState.rockBigTaskManager.setVisibilityArrows(true);
      }
    }
  }

  public void setCircles() {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    for (int i = 0; i < circles.size(); i++) {
      circles.get(i).setFill(harpController.getColourIndex(i + 2));
      circles.get(i).setOpacity(100);
    }
  }

  @FXML
  public void onClickedCircle3(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 3 clicked");
    harpController.setCirclesFound(2);
    circle3.setOpacity(0);
    circle3.setDisable(true);
  }

  @FXML
  public void onClickedCircle4(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 4 clicked");
    harpController.setCirclesFound(3);
    circle4.setOpacity(0);
    circle4.setDisable(true);
  }

  @FXML
  public void onClickedCircle5(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 5 clicked");
    harpController.setCirclesFound(4);
    circle5.setOpacity(0);
    circle5.setDisable(true);
  }
}
