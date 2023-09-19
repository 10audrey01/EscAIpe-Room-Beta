package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import nz.ac.auckland.se206.controllers.rooms.RaveController;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class ChatManager {

  private ChatCompletionRequest chatCompletionRequest;
  private ArrayList<TextArea> TextAreas;
  private ArrayList<TextField> TextFields;
  private String messages;

  public ChatManager() {
    this.messages = "";
    TextAreas = new ArrayList<TextArea>();
    TextFields = new ArrayList<TextField>();
  }

  public void addTextArea(TextArea textArea) {
    TextAreas.add(textArea);
  }

  public void addTextField(TextField textField) {
    TextFields.add(textField);
  }

  public void clearAllTextFields() {
    Platform.runLater(
        () -> {
          for (TextField textField : TextFields) {
            textField.setText("");
          }
        });
  }

  public void generateInitialMessage() throws ApiProxyException {

    Task<Void> initializeRiddleTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.2)
                    .setTopP(0.5)
                    .setMaxTokens(100);
            runGpt(
                new ChatMessage(
                    "user",
                    GptPromptEngineering.getRiddleWithGivenWord(RaveController.getRiddleObject())));
            return null;
          }
        };

    System.out.println("Generating initial message");
    Thread initializeRiddleThread = new Thread(initializeRiddleTask, "initializeRiddleThread");
    initializeRiddleThread.start();
  }

  public void addMessage(ChatMessage msg) {
    messages = msg.getRole() + ": " + msg.getContent() + "\n\n";
    Platform.runLater(
        () -> {
          for (TextArea textArea : TextAreas) {
            textArea.appendText(messages);
          }
        });
  }

  @FXML
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
            clearAllTextFields();
            ChatMessage msg = new ChatMessage("user", message);
            addMessage(msg);
            ChatMessage response = runGpt(msg);
            if (response.getRole().equals("assistant")) {
              if (response.getContent().startsWith("Correct")) {
                GameState.isRiddleResolved = true;
              }
            }
            return null;
          }
        };
    Thread onSendMessageThread = new Thread(onSendMessageTask, "onSendMessageThread");
    onSendMessageThread.start();
  }

  // get a hint for the music quiz
  public void getMusicQuizHint(ChatMessage msg, Button btn, TextArea speechArea) {
    Task<Void> getHintTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            Platform.runLater(
                () -> {
                  btn.setText("Loading...");
                  btn.setFont(Font.font(20));
                  btn.setDisable(true);
                });
            ChatMessage res = runGpt(msg);
            Platform.runLater(
                () -> {
                  speechArea.setText("Hey man, I got a hint for you...\n" + res.getContent());
                  btn.setVisible(false);
                });
            return null;
          }
        };

    Thread hintThread = new Thread(getHintTask, "hintThread");
    hintThread.start();
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
      addMessage(result.getChatMessage());
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }
}
