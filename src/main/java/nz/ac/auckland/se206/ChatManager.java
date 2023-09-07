package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    runGpt(new ChatMessage("user", GptPromptEngineering.getRiddleWithGivenWord("vase")));
  }

  public void addMessage(ChatMessage msg) {
    messages = messages.concat(msg.getRole() + ": " + msg.getContent() + "\n\n");
    Platform.runLater(
        () -> {
          for (TextArea textArea : TextAreas) {
            textArea.setText(messages);
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
    clearAllTextFields();
    ChatMessage msg = new ChatMessage("user", message);
    addMessage(msg);
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
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
