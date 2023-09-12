package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class ClassicalController {

  @FXML private Rectangle raveDoor;
  @FXML private Rectangle rockDoor;
  @FXML private Pane celloPane;
  @FXML private Pane celloBowPane;
  @FXML private Pane grandPianoPane;
  @FXML private Pane clarinetPane;
  @FXML private Pane harpPane;
  @FXML private Pane tambourinePane;
  @FXML private Pane trumpetPane;
  @FXML private Pane chatBoxPane;
  @FXML private Pane colourPane1;
  @FXML private Pane colourPane2;
  @FXML private Pane colourPane3;
  @FXML private Pane colourPane4;
  @FXML private Label timerLabel;
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
    gameState.colourManager.addToColourPanes1(colourPane1);
    gameState.colourManager.addToColourPanes2(colourPane2);
    gameState.colourManager.addToColourPanes3(colourPane3);
    gameState.colourManager.addToColourPanes4(colourPane4);
    chatOpened = false;
  }

  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

  @FXML
  private void doClickedCello(MouseEvent event) throws IOException {
    System.out.println("Cello Clicked");
  }

  @FXML
  private void doClickedCelloBow(MouseEvent event) throws IOException {
    System.out.println("Cello Bow Clicked");
  }

  @FXML
  private void doClickedClarinet(MouseEvent event) throws IOException {
    System.out.println("Clarinet Clicked");
  }

  @FXML
  private void doClickedGrandPiano(MouseEvent event) throws IOException {
    System.out.println("Grand Piano Clicked");
  }

  @FXML
  private void doClickedTrumpet(MouseEvent event) throws IOException {
    System.out.println("Trumpet Clicked");
  }

  @FXML
  private void doClickedHarp(MouseEvent event) throws IOException {
    System.out.println("Harp Clicked");
  }

  @FXML
  private void doClickedTambourine(MouseEvent event) throws IOException {
    System.out.println("Tambourine Clicked");
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
}
