package nz.ac.auckland.se206.controllers.rooms.rave;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GameState.Difficulty;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class MusicQuizController {
  @FXML private TextArea speechBox;
  @FXML private Button btnReturn;
  @FXML private Button hintBtn;
  @FXML private Button songBtn;
  @FXML private Button optionOneBtn;
  @FXML private Button optionTwoBtn;
  @FXML private Button optionThreeBtn;
  @FXML private Button optionFourBtn;
  @FXML private Button optionFiveBtn;
  @FXML private Button optionSixBtn;
  @FXML private Label timerLabel;
  @FXML private Label hintLabel;

  private GameState gamestate;
  private String[] genres = {
    "techno", "pop", "hip hop", "rnb", "kpop", "funk", "house", "drum and bass"
  };
  private String genreSolution;
  private int correctGenreIndex;
  private MediaPlayer musicPlayer;
  private List<String> selectedGenres = new ArrayList<>();
  private ChatCompletionRequest chatCompletionRequest;

  @FXML
  private void initialize() throws IOException, URISyntaxException, ApiProxyException {
    this.gamestate = GameState.getInstance();
    this.gamestate.timeManager.addToTimers(timerLabel);
    this.gamestate.hintManager.addHintLabel(hintLabel);
    this.speechBox.setText("Hey man, I need your help identifying this music...");
    Random random = new Random();
    int randomNumber = random.nextInt(8);
    this.genreSolution = genres[randomNumber];
    System.out.println("Solution for genre: " + genreSolution);
    this.musicPlayer =
        new MediaPlayer(
            new Media(
                getClass()
                    .getResource("/sounds/songs/" + genreSolution + ".mp3")
                    .toURI()
                    .toString()));

    musicPlayer.setOnEndOfMedia(
        () -> {
          musicPlayer.seek(Duration.ZERO);
        });

    selectOptions();
    if (GameState.difficulty == Difficulty.HARD) {
      hintBtn.setVisible(false);
    }
  }

  private void selectOptions() throws ApiProxyException {
    List<String> availableGenres = new ArrayList<>();
    Collections.addAll(availableGenres, genres);

    availableGenres.remove(genreSolution);

    Random random = new Random();

    Collections.shuffle(availableGenres, random);
    int randomIndex = random.nextInt(availableGenres.size() - 1);
    this.correctGenreIndex = randomIndex + 1;
    availableGenres.add(randomIndex, genreSolution);
    selectedGenres = availableGenres.subList(0, 6);

    List<Button> optionButtons =
        Arrays.asList(
            optionOneBtn, optionTwoBtn, optionThreeBtn, optionFourBtn, optionFiveBtn, optionSixBtn);

    for (int i = 0; i < selectedGenres.size(); i++) {
      String genre = selectedGenres.get(i);
      Button button = optionButtons.get(i);
      button.setText((i + 1) + ". " + genre);
    }

    System.out.println("Correct genre index: " + correctGenreIndex);
  }

  @FXML
  private void onClickSongBtn() {
    System.out.println("sb");
    if (musicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
      musicPlayer.pause();
      songBtn.setText("PLAY SONG");
    } else {
      musicPlayer.play();
      songBtn.setText("PAUSE SONG");
    }
  }

  @FXML
  private void onClickOne() {
    System.out.println("1");
    if (correctGenreIndex == 1) {
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. You can take this key if you want, I guess");
      gamestate.objectiveListManager.completeObjective1();
      System.out.println("correct");
    }
  }

  @FXML
  private void onClickTwo() {
    System.out.println("2");
    if (correctGenreIndex == 2) {
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. You can take this key if you want, I guess");
      gamestate.objectiveListManager.completeObjective1();
      System.out.println("correct");
    }
  }

  @FXML
  private void onClickThree() {
    System.out.println("3");
    if (correctGenreIndex == 3) {
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. You can take this key if you want, I guess");
      gamestate.objectiveListManager.completeObjective1();
      System.out.println("correct");
    }
  }

  @FXML
  private void onClickFour() {
    System.out.println("4");
    if (correctGenreIndex == 4) {
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. You can take this key if you want, I guess");
      gamestate.objectiveListManager.completeObjective1();
      System.out.println("correct");
    }
  }

  @FXML
  private void onClickFive() {
    System.out.println("5");
    if (correctGenreIndex == 5) {
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. You can take this key if you want, I guess");
      gamestate.objectiveListManager.completeObjective1();
      System.out.println("correct");
    }
  }

  @FXML
  private void onClickSix() {
    System.out.println("6");
    if (correctGenreIndex == 6) {
      GameState.isMusicQuizCompleted = true;
      speechBox.setText("Nice work bro. You can take this key if you want, I guess");
      gamestate.objectiveListManager.completeObjective1();
      System.out.println("correct");
    }
  }

  @FXML
  private void onClickHint() {
    System.out.println("hint");
    if (this.gamestate.hintManager.getHintsRemaining() > 0) {
      this.gamestate.hintManager.useHint();
      getMusicQuizHint(
          new ChatMessage("user", GptPromptEngineering.getHintWithMusic(genreSolution)));
    }
  }

  // switches back to the rave room
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
    musicPlayer.pause();
    songBtn.setText("PLAY SONG");
  }

  // get a hint for the music quiz
  public void getMusicQuizHint(ChatMessage msg) {
    Task<Void> getHintTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            Platform.runLater(
                () -> {
                  hintBtn.setText("Loading...");
                  hintBtn.setFont(Font.font(20));
                  hintBtn.setDisable(true);
                });

            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.8)
                    .setTopP(0.5)
                    .setMaxTokens(70);

            ChatMessage res = runGpt(msg);

            Platform.runLater(
                () -> {
                  speechBox.setText("Hey man, I got a hint for you...\n" + res.getContent());
                  hintBtn.setVisible(false);
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
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }
}
