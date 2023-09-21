package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public abstract class AbstractController {

  @FXML private Pane chatBoxPane;
  @FXML private TextField textField;

  private boolean chatOpened = false;
  private GameState gameState;

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
      gameState.getChatManager().onSendMessage(textField);
    }
  }
}
