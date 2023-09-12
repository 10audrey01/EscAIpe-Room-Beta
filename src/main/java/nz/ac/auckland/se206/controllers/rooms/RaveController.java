package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
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
  @FXML private Label colourLabel1;
  @FXML private Label colourLabel2;
  @FXML private Label colourLabel3;
  @FXML private Label colourLabel4;
  @FXML private Label timerLabel;
  @FXML private ImageView doorImage;
  @FXML private ImageView greenLock;
  @FXML private ImageView redLock;
  @FXML private ImageView blueLock;
  @FXML private ImageView yellowLock;
  @FXML private TextArea textArea;
  @FXML private TextField textField;
  @FXML private boolean chatOpened;

  private GameState gameState;

  @FXML
  private void initialize() {
    gameState = GameState.getInstance();
    gameState.timeManager.addToTimers(timerLabel);
    gameState.chatManager.addTextArea(textArea);
    gameState.chatManager.addTextField(textField);
    gameState.colourManager.addToColourLabels1(colourLabel1);
    gameState.colourManager.addToColourLabels2(colourLabel2);
    gameState.colourManager.addToColourLabels3(colourLabel3);
    gameState.colourManager.addToColourLabels4(colourLabel4);
    chatOpened = false;

    objects = new ArrayList<String>();
    objects.add("poster");
    objects.add("dj");
    objects.add("bodybuilder");
    objects.add("bouncer");
    objects.add("disco");
    objects.add("speaker");
  }

  @FXML
  private void onClickPoster(MouseEvent event) {
    System.out.println("poster clicked");
    isRiddleObject("poster");
  }

  @FXML
  private void onClickDj(MouseEvent event) {
    System.out.println("dj clicked");
    isRiddleObject("dj");
  }

  @FXML
  private void onClickBodybuilder(MouseEvent event) {
    System.out.println("bodybuilder clicked");
    isRiddleObject("bodybuilder");
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
    System.out.println("red clicked");
  }

  @FXML
  private void onClickGreen(MouseEvent event) {
    System.out.println("green clicked");
  }

  @FXML
  private void onClickBlue(MouseEvent event) {
    System.out.println("blue clicked");
  }

  @FXML
  private void onClickYellow(MouseEvent event) {
    System.out.println("yellow clicked");
  }

  @FXML
  private void onClickDoor(MouseEvent event) {
    System.out.println("door clicked");
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
    if (event.getCode() == KeyCode.ENTER) {
      System.out.println("Message Sent");
      gameState = GameState.getInstance();
      gameState.chatManager.onSendMessage(textField);
    }
  }

  public void isRiddleObject(String object) {
    if (riddleObject.equals(object) && GameState.isRiddleResolved) {
      GameState.isRiddleObjectFound = true;
      gameState.colourManager.setLabelColours();
    }
  }
}
