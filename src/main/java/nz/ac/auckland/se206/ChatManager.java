package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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

/**
 * Manages the chat interface in the game, including text display, user input, and game sprite
 * updates.
 */
public class ChatManager {

  private GameState gameState;
  private ChatCompletionRequest chatCompletionRequest;
  private ArrayList<TextArea> textAreas;
  private ArrayList<TextField> textFields;
  private ArrayList<ImageView> gmSprites;
  private ArrayList<CheckBox> ttsCheckBoxes;
  private ArrayList<ImageView> gmArrows;
  private String messages;
  private ChatMessage lastMsg;

  /** Constructs a new ChatManager, initializing its components and state. */
  public ChatManager() {
    this.gameState = GameState.getInstance();
    this.messages = "";
    textAreas = new ArrayList<TextArea>();
    textFields = new ArrayList<TextField>();
    gmSprites = new ArrayList<ImageView>();
    ttsCheckBoxes = new ArrayList<CheckBox>();
    gmArrows = new ArrayList<ImageView>();
  }

  /**
   * Adds a TextArea to the list for displaying chat messages.
   *
   * @param textArea The TextArea to be added.
   */
  public void addTextArea(TextArea textArea) {
    textAreas.add(textArea);
  }

  /**
   * Adds a TextField to the list for user input.
   *
   * @param textField The TextField to be added.
   */
  public void addTextField(TextField textField) {
    textFields.add(textField);
  }

  /**
   * Adds an ImageView for displaying game sprites.
   *
   * @param image The ImageView for game sprites to be added.
   */
  public void addSprite(ImageView image) {
    gmSprites.add(image);
  }

  /**
   * Adds a checkbox for toggling text-to-speech (TTS).
   *
   * @param checkBox The CheckBox for TTS to be added.
   */
  public void addTtsCheckBox(CheckBox checkBox) {
    ttsCheckBoxes.add(checkBox);
  }

  public void addGmArrowGif(ImageView arrowGmGif) {
    gmArrows.add(arrowGmGif);
  }

  /** Clears the text in all textFields. */
  public void clearAllTextFields() {
    Platform.runLater(
        () -> {
          for (TextField textField : textFields) {
            textField.setText("");
          }
        });
  }

  /**
   * Sets the checkbox selection state for text-to-speech (TTS).
   *
   * @param selected True if TTS checkbox should be selected, false otherwise.
   */
  public void setCheckboxSelected(boolean selected) {
    Platform.runLater(
        () -> {
          for (CheckBox checkBox : ttsCheckBoxes) {
            checkBox.setSelected(selected);
          }
        });
  }

  public void setVisibilityGmArrows(boolean visible) {
    Platform.runLater(
        () -> {
          for (ImageView arrow : gmArrows) {
            arrow.setVisible(visible);
          }
        });
  }

  /**
   * Sets the game sprites to a loading state by updating their images with a loading GIF.
   *
   * @throws IOException If there is an issue loading the image.
   */
  private void setToLoading() throws IOException {
    Image image = new Image(App.class.getResource("/images/gm/gmloading.gif").openStream());
    Platform.runLater(
        () -> {
          for (ImageView sprite : gmSprites) {
            sprite.setImage(image);
          }
        });
  }

  /**
   * Sets the game sprites to their default state by updating their images with a default image.
   *
   * @throws IOException If there is an issue loading the image.
   */
  private void setToDefault() throws IOException {
    Image image = new Image(App.class.getResource("/images/gm/gmdefault.png").openStream());
    Platform.runLater(
        () -> {
          for (ImageView sprite : gmSprites) {
            sprite.setImage(image);
          }
        });
  }

  /**
   * Generates the initial chat message to start the conversation with the game master (GM). This
   * method initializes the conversation by sending a greeting message to the GM, which is generated
   * using GPT. It also handles text-to-speech conversion if the GM responds with an audio message.
   *
   * @throws ApiProxyException If there is an issue with the API proxy while communicating with
   */
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

  /**
   * Adds a chat message to the chat boxes in the user interface. The message can be from the user
   * or the game master (GM), and this method appends the message to the appropriate chat boxes.
   *
   * @param msg The chat message to be added to the chat boxes.
   */
  public void addMessage(ChatMessage msg) {
    // check if the role of the message is the user or the gamemaster
    if (msg.getRole().equals("assistant")) {
      messages = "Game Master: " + msg.getContent() + "\n\n";
    } else {
      clearAllTextArea();
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

  /**
   * Clears the text content of all text areas used for displaying chat messages in the user
   * interface.
   */
  public void clearAllTextArea() {
    Platform.runLater(
        () -> {
          for (TextArea textArea : textAreas) {
            textArea.clear();
          }
        });
  }

  /**
   * Handles the user's message input and initiates a conversation with the AI. It processes the
   * user's input, sends it to the AI, and displays the AI's response in the chat interface.
   *
   * @param inputText The TextField containing the user's message input.
   * @throws ApiProxyException If there is an issue with the API proxy.
   * @throws IOException If there is an issue with IO operations.
   */
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

  /**
   * Runs the GPT model with a given chat message. It sends the message to the AI, receives a
   * response, and returns the AI's response.
   *
   * @param msg The chat message to be sent to the AI.
   * @return The AI's response as a ChatMessage.
   * @throws ApiProxyException If there is an issue with the API proxy.
   */
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
