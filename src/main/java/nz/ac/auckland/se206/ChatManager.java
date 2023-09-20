package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class ChatManager {

  private GameState gameState;
  private ChatCompletionRequest chatCompletionRequest;
  private ArrayList<TextArea> TextAreas;
  private ArrayList<TextField> TextFields;
  private ArrayList<ImageView> gmSprites;
  private String messages;

  public ChatManager() {
    this.gameState = GameState.getInstance();
    this.messages = "";
    TextAreas = new ArrayList<TextArea>();
    TextFields = new ArrayList<TextField>();
    gmSprites = new ArrayList<ImageView>();
  }

  public void addTextArea(TextArea textArea) {
    TextAreas.add(textArea);
  }

  public void addTextField(TextField textField) {
    TextFields.add(textField);
  }

  public void addSprite(ImageView image) {
    gmSprites.add(image);
  }

  public void clearAllTextFields() {
    Platform.runLater(
        () -> {
          for (TextField textField : TextFields) {
            textField.setText("");
          }
        });
  }

  private void setToLoading() throws IOException {
    Image image = new Image(App.class.getResource("/images/gm/gmloading.gif").openStream());
    Platform.runLater(
        () -> {
          for (ImageView sprite : gmSprites) {
            sprite.setImage(image);
          }
        });
  }

  private void setToDefault() throws IOException {
    Image image = new Image(App.class.getResource("/images/gm/gmdefault.png").openStream());
    Platform.runLater(
        () -> {
          for (ImageView sprite : gmSprites) {
            sprite.setImage(image);
          }
        });
  }

  public void generateInitialMessage() throws ApiProxyException {

    Task<Void> initializeRiddleTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            setToLoading();
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.8)
                    .setTopP(0.5)
                    .setMaxTokens(70);
            runGpt(new ChatMessage("user", GptPromptEngineering.getGmGreeting()));
            setToDefault();
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
            setToLoading();
            clearAllTextFields();
            ChatMessage msg = new ChatMessage("user", message);
            addMessage(msg);
            ChatMessage response =
                runGpt(new ChatMessage("user", GptPromptEngineering.getGmInteraction(message)));
            if (response.getRole().equals("assistant")) {
              if (response.getContent().startsWith("Here's a hint")) {
                gameState.hintManager.useHint();
                // GameState.isRiddleResolved = true; // TODO: add this to GuitaristRiddleController
                // gameState.objectiveListManager.completeObjective3();
              }
            }
            setToDefault();
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
