package nz.ac.auckland.se206.controllers.rooms.notes;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for handling the rock note room. */
public class RockNoteController {
  @FXML private Text rockHintText;
  @FXML private Button btnReturn;
  @FXML private Label timerLabel;

  private GameState gameState;

  /**
   * Initializes the RockNoteController by setting up the gamestate, labels, and other game-related
   * variables.
   *
   * @throws IOException If there is an issue with input/output.
   */
  @FXML
  private void initialize() throws IOException {
    this.gameState = GameState.getInstance();
    this.gameState.getTimeManager().addToTimers(timerLabel);
    this.gameState.setRockNote(this);
  }

  /**
   * Sets the current hint text to be displayed in the RockNoteController.
   *
   * @param text The text to be displayed as a hint.
   */
  public void setText(String text) {
    this.rockHintText.setText(text);
  }

  /**
   * Switches back to the Rock room scene when the "RETURN" button is clicked.
   *
   * @param event The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }
}
