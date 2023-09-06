package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GameState.Difficulty;
import nz.ac.auckland.se206.GameState.PlayTime;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class StartController {

  @FXML private ToggleButton easyBtn;
  @FXML private ToggleButton mediumBtn;
  @FXML private ToggleButton hardBtn;
  @FXML private ToggleButton twoMinsBtn;
  @FXML private ToggleButton fourMinsBtn;
  @FXML private ToggleButton sixMinsBtn;
  @FXML private Button startGameBtn;

  private GameState gamestate = GameState.getInstance();

  @FXML
  private void initialize() {
    onClickMedium();
    onClickFour();
  }

  private void resetDifficulty() {
    easyBtn.selectedProperty().set(false);
    mediumBtn.selectedProperty().set(false);
    hardBtn.selectedProperty().set(false);
  }

  private void resetTime() {
    twoMinsBtn.selectedProperty().set(false);
    fourMinsBtn.selectedProperty().set(false);
    sixMinsBtn.selectedProperty().set(false);
  }

  @FXML
  private void onClickEasy() {
    resetDifficulty();
    easyBtn.selectedProperty().set(true);
    gamestate.setDifficulty(Difficulty.EASY);
  }

  @FXML
  private void onClickMedium() {
    resetDifficulty();
    mediumBtn.selectedProperty().set(true);
    gamestate.setDifficulty(Difficulty.MEDIUM);
  }

  @FXML
  private void onClickHard() {
    resetDifficulty();
    hardBtn.selectedProperty().set(true);
    gamestate.setDifficulty(Difficulty.HARD);
  }

  @FXML
  private void onClickTwo() {
    resetTime();
    twoMinsBtn.selectedProperty().set(true);
    gamestate.setTime(PlayTime.TWO);
  }

  @FXML
  private void onClickFour() {
    resetTime();
    fourMinsBtn.selectedProperty().set(true);
    gamestate.setTime(PlayTime.FOUR);
  }

  @FXML
  private void onClickSix() {
    resetTime();
    sixMinsBtn.selectedProperty().set(true);
    gamestate.setTime(PlayTime.SIX);
  }

  @FXML
  private void onGameStart(ActionEvent event) throws IOException {
    Button current = (Button) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
    System.out.println(
        "Started with difficulty: "
            + gamestate.getDifficulty()
            + " and time: "
            + gamestate.getTime()
            + " minutes.");
  }
}
