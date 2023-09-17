package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GameState.Difficulty;
import nz.ac.auckland.se206.GameState.PlayTime;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class StartController {

  @FXML private Label gameNameLabel;
  @FXML private Label difficultyDescriptionLabel;
  @FXML private ToggleButton easyBtn;
  @FXML private ToggleButton mediumBtn;
  @FXML private ToggleButton hardBtn;
  @FXML private ToggleButton twoMinsBtn;
  @FXML private ToggleButton fourMinsBtn;
  @FXML private ToggleButton sixMinsBtn;
  @FXML private Button startGameBtn;

  private GameState gameState;

  @FXML
  private void initialize() {
    this.gameState = GameState.getInstance();
  }

  @FXML
  private void onClickEasy() {
    GameState.difficulty = Difficulty.EASY;
  }

  @FXML
  private void onEasyEnter() {
    difficultyDescriptionLabel.setText("You can ask for as many hints as you want!");
  }

  @FXML
  private void onEasyExit() {
    difficultyDescriptionLabel.setText("");
  }

  @FXML
  private void onClickMedium() {
    GameState.difficulty = Difficulty.MEDIUM;
  }

  @FXML
  private void onMediumEnter() {
    difficultyDescriptionLabel.setText("You can ask for a maximum of 5 hints!");
  }

  @FXML
  private void onMediumExit() {
    difficultyDescriptionLabel.setText("");
  }

  @FXML
  private void onClickHard() {
    GameState.difficulty = Difficulty.HARD;
  }

  @FXML
  private void onHardEnter() {
    difficultyDescriptionLabel.setText("No hints will be given!");
  }

  @FXML
  private void onHardExit() {
    difficultyDescriptionLabel.setText("");
  }

  @FXML
  private void onClickTwo() {
    GameState.time = PlayTime.TWO;
  }

  @FXML
  private void onClickFour() {
    GameState.time = PlayTime.FOUR;
  }

  @FXML
  private void onClickSix() {
    GameState.time = PlayTime.SIX;
  }

  @FXML
  private void onGameStart(ActionEvent event) throws IOException {
    if (easyBtn.getToggleGroup().getSelectedToggle() == null) { // no difficulty selected
      difficultyDescriptionLabel.setText("Please select a difficulty!");
      return;
    } else if (twoMinsBtn.getToggleGroup().getSelectedToggle() == null) { // no time selected
      difficultyDescriptionLabel.setText("Please select a time limit!");
      return;
    }
    gameState = GameState.getInstance();

    SceneManager.addUi(AppUi.CLASSICAL, App.loadFxml("classical"));
    SceneManager.addUi(AppUi.RAVE, App.loadFxml("rave"));
    SceneManager.addUi(AppUi.ROCK, App.loadFxml("rock"));
    SceneManager.addUi(AppUi.BODYBUILDER, App.loadFxml("bodybuilder"));
    SceneManager.addUi(AppUi.ROCKNOTE, App.loadFxml("rocknote"));
    SceneManager.addUi(AppUi.CLASSICALNOTE, App.loadFxml("classicalnote"));

    FXMLLoader pianoLoader = new FXMLLoader(App.class.getResource("/fxml/piano.fxml"));
    SceneManager.addUi(AppUi.PIANO, pianoLoader.load());

    // add reference to piano controller to use methods that are not static
    SceneManager.addController(AppUi.PIANO, pianoLoader.getController());

    Button current = (Button) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
    System.out.println(
        "Started with difficulty: "
            + GameState.difficulty
            + " and time: "
            + GameState.time
            + " minutes.");
    gameState.startGame(); // including generating tasks
  }
}
