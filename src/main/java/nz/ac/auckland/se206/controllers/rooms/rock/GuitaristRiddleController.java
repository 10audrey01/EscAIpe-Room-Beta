package nz.ac.auckland.se206.controllers.rooms.rock;

import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.rooms.RaveController;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/** Controller class for handling the bodybuilder event room. */
public class GuitaristRiddleController {

  @FXML private TextField textField;
  @FXML private TextArea textArea;
  @FXML private Button btnReturn;
  @FXML private Label timerLabel;
  @FXML private Label hintLabel;

  private GameState gameState;
  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Initializes the RockController, setting up the game state, timer label, and generating the
   * initial message from the guitarist.
   *
   * @throws IOException If there is an issue with input/output.
   * @throws ApiProxyException If there is an issue with the API proxy.
   */
  @FXML
  private void initialize() throws IOException, ApiProxyException {
    // gets the gamestate instance, adding the labels for hints/timers to the gamestate
    this.gameState = GameState.getInstance();
    gameState.getTimeManager().addToTimers(timerLabel);
    gameState.getHintManager().addHintLabel(hintLabel);
    generateInitialMessage();
  }

  /**
   * Handles key presses and prints the key code to the console.
   *
   * @param event The KeyEvent representing the key press event.
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles key releases and sends a message when the Enter key is released.
   *
   * @param event The KeyEvent representing the key release event.
   * @throws ApiProxyException If there is an issue with the API proxy.
   * @throws IOException If there is an I/O error.
   */
  @FXML
  public void onKeyReleased(KeyEvent event) throws ApiProxyException, IOException {
    System.out.println("key " + event.getCode() + " released");
    if (event.getCode() == KeyCode.ENTER) {
      System.out.println("Message Sent");
      onSendMessage(textField);
    }
  }

  /**
   * Switches back to the rock room when the "Return" button is clicked.
   *
   * @param event The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

  /**
   * Generates the initial message from the guitarist, which is the riddle.
   *
   * @throws ApiProxyException If there is an issue with the API proxy.
   */
  public void generateInitialMessage() throws ApiProxyException {
    Task<Void> initializeRiddleTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            // task which creates a completion request from gpt for the initial message
            // create a new completion request with respective prompt values
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.2)
                    .setTopP(0.5)
                    .setMaxTokens(100);
            // run gpt with the desired prompt for a riddle with the desired object
            runGpt(
                new ChatMessage(
                    "user",
                    GptPromptEngineering.getRiddleWithGivenWord(RaveController.getRiddleObject())));
            return null;
          }
        };

    // run thread for concurrency
    Thread initializeRiddleThread = new Thread(initializeRiddleTask, "initializeRiddleThread");
    initializeRiddleThread.start();
  }

  /** Clears the text field used for chat input. */
  public void clearTextField() {
    Platform.runLater(
        () -> {
          textField.setText("");
        });
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
    if (msg.getRole().equals("user")) {
      textArea.appendText("You: " + msg.getContent() + "\n\n");
    } else if (msg.getRole().equals("assistant")) {
      textArea.appendText("Guitarist: " + msg.getContent() + "\n\n");
    }
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  public void onSendMessage(TextField inputText) throws ApiProxyException, IOException {
    String message = inputText.getText();
    System.out.println(message);
    if (message.trim().isEmpty()) {
      System.out.println("Whoops");
      return;
    }

    Task<Void> onSendMessageTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            ChatMessage response = null;

            clearTextField();
            ChatMessage msg = new ChatMessage("user", message);
            appendChatMessage(msg);

            Platform.runLater(
                () -> {
                  textField.setText("Loading . . .");
                  textField.setEditable(false); // user cannot type while loading
                });

            if (message.toLowerCase().contains("help")
                || message.toLowerCase().contains("hint")
                || message
                    .toLowerCase()
                    .contains("clue")) { // if the user asks for a hint or similar
              if (gameState.getHintManager().getHintsRemaining() > 0) {
                response = runGpt(msg);
              } else {
                // gpt will tell user they are out of hints
                runGpt(new ChatMessage("user", GptPromptEngineering.getGmNoHint()));
              }
            } else {
              response = runGpt(msg);
            }

            Platform.runLater(
                () -> {
                  textField.setText("");
                  textField.setEditable(true);
                });

            if (response.getRole().equals("assistant")) {
              if (response
                  .getContent()
                  .startsWith("Here's a hint")) { // way to recognise whether response is a hint
                gameState.getHintManager().useHint();
              }
              if (response.getContent().startsWith("Correct")) {
                GameState.isRiddleSolved = true;
                gameState
                    .getObjectiveListManager()
                    .completeObjective3(); // objective 3 is to talk to guitarist
              }
            }
            return null;
          }
        };
    Thread onSendMessageThread = new Thread(onSendMessageTask, "onSendMessageThread");
    onSendMessageThread.start();
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    if (chatCompletionRequest.getMessages().size() > 3) {
      chatCompletionRequest.getMessages().remove(2);
    }

    chatCompletionRequest.addMessage(msg);
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      appendChatMessage(result.getChatMessage());
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }
}
