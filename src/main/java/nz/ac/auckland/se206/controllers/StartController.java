package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class StartController {

  @FXML private RadioButton easyBtn;
  @FXML private RadioButton mediumBtn;
  @FXML private RadioButton hardBtn;
  @FXML private RadioButton twoMinsBtn;
  @FXML private RadioButton fourMinsBtn;
  @FXML private RadioButton sixMinsBtn;
  @FXML private Button startGameBtn;

  @FXML
  private void initialize() {}

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
  }

  @FXML
  private void onClickMedium() {
    resetDifficulty();
    mediumBtn.selectedProperty().set(true);
  }

  @FXML
  private void onClickHard() {
    resetDifficulty();
    hardBtn.selectedProperty().set(true);
  }

  @FXML
  private void onClickTwo() {
    resetTime();
    twoMinsBtn.selectedProperty().set(true);
  }

  @FXML
  private void onClickFour() {
    resetTime();
    fourMinsBtn.selectedProperty().set(true);
  }

  @FXML
  private void onClickSix() {
    resetTime();
    sixMinsBtn.selectedProperty().set(true);
  }

  @FXML
  private void onGameStart() {}
}
