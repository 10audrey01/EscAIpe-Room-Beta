package nz.ac.auckland.se206.controllers.rooms.rave;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

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

  private GameState gamestate;
  private String[] genres = {
    "techno", "pop", "hip hop", "rnb", "kpop", "funk", "house", "drum and bass"
  };
  private String genreSolution;
  private MediaPlayer musicPlayer;

  @FXML
  private void initialize() throws IOException, URISyntaxException {
    this.gamestate = GameState.getInstance();
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
  }

  @FXML
  private void onClickTwo() {
    System.out.println("2");
  }

  @FXML
  private void onClickThree() {
    System.out.println("3");
  }

  @FXML
  private void onClickFour() {
    System.out.println("4");
  }

  @FXML
  private void onClickFive() {
    System.out.println("5");
  }

  @FXML
  private void onClickSix() {
    System.out.println("6");
  }

  @FXML
  private void onClickHint() {
    System.out.println("hint");
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
}
