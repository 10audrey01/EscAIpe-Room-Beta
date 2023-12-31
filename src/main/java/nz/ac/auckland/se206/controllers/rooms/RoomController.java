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
import nz.ac.auckland.se206.tasks.HarpTask;
import nz.ac.auckland.se206.tasks.Task;

/** Abstract class for the room controllers. */
public abstract class RoomController {

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
  @FXML protected ImageView noteArrowGif;
  @FXML protected ImageView gmArrowGif;
  @FXML protected ImageView noteImage;
  @FXML protected ImageView noteImage1;
  @FXML protected ImageView step1BlueKey;
  @FXML protected ImageView step2GreenKey;
  @FXML protected ImageView step3RedKey;
  @FXML protected ImageView step4YellowKey;
  @FXML protected CheckBox ttsCheckBox;

  protected boolean chatOpened = false;
  protected boolean gmClicked = false;
  protected GameState gameState;
  protected ArrayList<Label> objectiveLabels;
  protected ArrayList<ImageView> stepKeys;

  /**
   * Toggles the visibility of the chatbox, allowing the player to show or hide the in-game chat
   * interface. Also hides the GM arrows once the GM has been clicked.
   */
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

    // hide gm arrows once gm has been clicked
    if (!gmClicked) {
      gameState.getChatManager().setVisibilityGmArrows(false);
    }
    gmClicked = true;
  }

  /**
   * Handles key presses and logs the pressed key's code.
   *
   * @param event The KeyEvent representing the key press.
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles key releases and logs the released key's code. If the Enter key is released and the
   * chat is open, sends the entered message to the game manager.
   *
   * @param event The KeyEvent representing the key release.
   * @throws ApiProxyException If there is an issue with the API proxy.
   * @throws IOException If there is an issue with input/output.
   */
  @FXML
  public void onKeyReleased(KeyEvent event) throws ApiProxyException, IOException {
    System.out.println("key " + event.getCode() + " released");
    if (event.getCode() == KeyCode.ENTER && chatOpened) {
      System.out.println("Message Sent");
      gameState = GameState.getInstance();
      gameState.getChatManager().onSendMessage(textField);
    }
  }

  /**
   * Toggles the Text-to-Speech (TTS) feature on or off and updates the checkbox state accordingly.
   */
  @FXML
  public void onClickTts() {
    TextToSpeech.isTtsEnabled.set(!TextToSpeech.isTtsEnabled.get()); // Toggle TTS
    gameState
        .getChatManager()
        .setCheckboxSelected(TextToSpeech.isTtsEnabled.get()); // Update checkbox
  }

  /**
   * Checks if the harp task is selected.
   *
   * @return True if the harp task is selected, false otherwise.
   */
  public boolean isHarpTaskChosen() {
    // check if harp task is selected
    boolean harpTaskChosen = false;
    // circles only appear if harp task is selected
    for (int i = 0; i < gameState.getTaskManager().getTaskList().size(); i++) {
      Task task = gameState.getTaskManager().getTaskList().get(i);
      if (task instanceof HarpTask) {
        harpTaskChosen = true;
      }
    }
    return harpTaskChosen;
  }

  /**
   * Initializes all gamestate variables, adding necessary labels, elements, and components to the
   * gamestate instance for proper functionality.
   */
  public void initialiseAllGameStateVariables() {

    objectiveLabels = new ArrayList<Label>(List.of(step1Label, step2Label, step3Label, step4Label));
    stepKeys =
        new ArrayList<ImageView>(List.of(step1BlueKey, step2GreenKey, step3RedKey, step4YellowKey));

    // add all relevant labels to gamestate instance
    gameState = GameState.getInstance();
    gameState.addInitialLabels(
        timerLabel, hintLabel, textArea, textField, gmSprite, ttsCheckBox, gmArrowGif);

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
            noteArrowGif);

    gameState
        .getChatManager()
        .setCheckboxSelected(TextToSpeech.isTtsEnabled.get()); // Update checkbox
  }
}
