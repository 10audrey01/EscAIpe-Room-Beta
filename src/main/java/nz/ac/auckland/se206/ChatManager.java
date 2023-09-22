package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;
import nz.ac.auckland.se206.speech.TextToSpeech;

public class ChatManager {

  private GameState gameState;
  private ChatCompletionRequest chatCompletionRequest;
  private ArrayList<TextArea> textAreas;
  private ArrayList<TextField> textFields;
  private ArrayList<ImageView> gmSprites;
  private String messages;
  private ChatMessage lastMsg;

  public ChatManager() {
    this.gameState = GameState.getInstance();
    this.messages = "";
    textAreas = new ArrayList<TextArea>();
    textFields = new ArrayList<TextField>();
    gmSprites = new ArrayList<ImageView>();
  }

  // Add a TextArea to the list for displaying chat messages
  public void addTextArea(TextArea textArea) {
    textAreas.add(textArea);
  }

  // Add a TextField to the list for user input
  public void addTextField(TextField textField) {
    textFields.add(textField);
  }

  // Add an ImageView for displaying game sprites
  public void addSprite(ImageView image) {
    gmSprites.add(image);
  }

  // Clear the text in all textFields
  public void clearAllTextFields() {
    Platform.runLater(
        () -> {
          for (TextField textField : textFields) {
            textField.setText("");
          }
        });
  }

  // Set game sprites to loading state
  private void setToLoading() throws IOException {
    Image image = new Image(App.class.getResource("/images/gm/gmloading.gif").openStream());
    Platform.runLater(
        () -> {
          for (ImageView sprite : gmSprites) {
            sprite.setImage(image);
          }
        });
  }

  // Set game sprites to default state
  private void setToDefault() throws IOException {
    Image image = new Image(App.class.getResource("/images/gm/gmdefault.png").openStream());
    Platform.runLater(
        () -> {
          for (ImageView sprite : gmSprites) {
            sprite.setImage(image);
          }
        });
  }

  // Generate the initial chat message to start the conversation
  public void generateInitialMessage() throws ApiProxyException {

    Task<Void> initializeMsgTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            setToLoading();
            // create a new chat completion request to reduce token usage
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.8)
                    .setTopP(0.5)
                    .setMaxTokens(70);
            ChatMessage intro =
                runGpt(new ChatMessage("user", GptPromptEngineering.getGmGreeting()));

            if (intro.getRole().equals("assistant")) {
              // run the gm greeting with text to speech
              Task<Void> textToSpeechTask =
                  new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                      TextToSpeech.main(new String[] {intro.getContent()});
                      return null;
                    }
                  };
              Thread textToSpeechThread = new Thread(textToSpeechTask, "textToSpeechThread");
              textToSpeechThread.start();
            }
            setToDefault();
            return null;
          }
        };

    System.out.println("Generating initial message");
    Thread initializeRiddleThread = new Thread(initializeMsgTask, "initializeMsgThread");
    initializeRiddleThread.start();
  }

  // Add a user or AI chat message to the chat boxes
  public void addMessage(ChatMessage msg) {
    // check if the role of the message is the user or the gamemaster
    if (msg.getRole().equals("assistant")) {
      messages = "Game Master: " + msg.getContent() + "\n\n";
    } else {
      messages = "You: " + msg.getContent() + "\n\n";
    }
    // append the message to the GUI of all chatboxes in the game.
    Platform.runLater(
        () -> {
          for (TextArea textArea : textAreas) {
            textArea.appendText(messages);
          }
        });
  }

  // Handle user's message input and initiate a conversation with the AI
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
            // reset the chatting field on sending message
            setToLoading();
            clearAllTextFields();
            ChatMessage msg = new ChatMessage("user", message);
            addMessage(msg);

            // create a new chat completion request to reduce token usage
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.8)
                    .setTopP(0.5)
                    .setMaxTokens(70);

            // If the user asks for a hint or similar
            if (message.toLowerCase().contains("help")
                || message.toLowerCase().contains("hint")
                || message.toLowerCase().contains("clue")) {
              // If the user asks for a hint or similar
              if (gameState.getHintManager().getHintsRemaining() > 0) {
                // run gpt with the hint prompt
                System.out.println("hint used");
                gameState.getHintManager().useHint();
                lastMsg = runGpt(new ChatMessage("user", GptPromptEngineering.getGmHint()));
              } else {
                // gpt will tell user they are out of hints
                lastMsg = runGpt(new ChatMessage("user", GptPromptEngineering.getGmNoHint()));
              }
            } else {
              lastMsg = runGpt(msg);
            }

            if (lastMsg.getRole().equals("assistant")) {
              // run every gpt response with text to speech
              Task<Void> textToSpeechTask =
                  new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                      TextToSpeech.main(new String[] {lastMsg.getContent()});
                      return null;
                    }
                  };
              Thread textToSpeechThread = new Thread(textToSpeechTask, "textToSpeechThread");
              textToSpeechThread.start();
            }

            setToDefault();
            return null;
          }
        };
    Thread onSendMessageThread = new Thread(onSendMessageTask, "onSendMessageThread");
    onSendMessageThread.start();
  }

  // Run the GPT model with a given chat message
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    if (chatCompletionRequest.getMessages().size() > 3) {
      chatCompletionRequest.getMessages().remove(2);
    }

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
