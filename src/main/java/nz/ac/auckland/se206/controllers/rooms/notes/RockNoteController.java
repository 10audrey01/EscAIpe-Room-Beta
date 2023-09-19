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

public class RockNoteController {
  @FXML private Text rockHintText;
  @FXML private Button btnReturn;
  @FXML private Label timerLabel;

  private GameState gamestate;

  @FXML
  private void initialize() throws IOException {
    this.gamestate = GameState.getInstance();
    this.gamestate.timeManager.addToTimers(timerLabel);
    this.gamestate.rockNote = this;
  }

  public void setText(String text) {
    this.rockHintText.setText(text);
  }

  // switches back to the classical room
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }
}
