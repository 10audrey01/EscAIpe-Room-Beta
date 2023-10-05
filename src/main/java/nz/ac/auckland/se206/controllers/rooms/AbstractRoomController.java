package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.speech.TextToSpeech;

public abstract class AbstractRoomController {

  @FXML protected Pane chatBoxPane;
  @FXML protected Pane notePane;
  @FXML protected TextField textField;
  @FXML protected TextArea textArea;
  @FXML protected Label step1Label;
  @FXML protected Label step2Label;
  @FXML protected Label step3Label;
  @FXML protected Label step4Label;
  @FXML protected Label colourLabel1;
  @FXML protected Label colourLabel2;
  @FXML protected Label colourLabel3;
  @FXML protected Label colourLabel4;
  @FXML protected Label timerLabel;
  @FXML protected Label hintLabel;
  @FXML protected Label noteSequenceLabel;
  @FXML protected ImageView gmSprite;
  @FXML protected ImageView pointingArrowGif;
  @FXML protected ImageView noteImage;
  @FXML protected ImageView noteImage1;
  @FXML protected ImageView step1BlueKey;
  @FXML protected ImageView step2GreenKey;
  @FXML protected ImageView step3RedKey;
  @FXML protected ImageView step4YellowKey;
  @FXML protected CheckBox ttsCheckBox;

  protected boolean chatOpened = false;
  protected GameState gameState;
  protected ArrayList<Label> objectiveLabels;
  protected ArrayList<ImageView> stepKeys;

  // function which toggles the visibility of the chatbox
  @FXML
  protected void toggleChat() {
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

  @FXML
  public void onClickTts() {
    TextToSpeech.isTtsEnabled.set(!TextToSpeech.isTtsEnabled.get()); // Toggle TTS
    gameState
        .getChatManager()
        .setCheckboxSelected(TextToSpeech.isTtsEnabled.get()); // Update checkbox
  }

  // initialise all gamestate variables
  public void initialiseAllGameStateVariables() {

    objectiveLabels = new ArrayList<Label>(List.of(step1Label, step2Label, step3Label, step4Label));
    stepKeys =
        new ArrayList<ImageView>(List.of(step1BlueKey, step2GreenKey, step3RedKey, step4YellowKey));

    // add all relevant labels to gamestate instance
    gameState = GameState.getInstance();
    gameState.addInitialLabels(timerLabel, hintLabel, textArea, textField, gmSprite, ttsCheckBox);

    // add objective labels and steps to the objective list manager
    gameState.getObjectiveListManager().addObjectiveLabels(objectiveLabels);
    gameState.getObjectiveListManager().addStepKeys(stepKeys);

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
  }
}
