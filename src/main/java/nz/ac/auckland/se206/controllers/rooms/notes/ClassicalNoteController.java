package nz.ac.auckland.se206.controllers.rooms.notes;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class ClassicalNoteController {
  @FXML private Text classicalHintText;
  @FXML private Button btnReturn;

  private GameState gamestate;

  @FXML
  private void initialize() throws IOException {
    this.gamestate = GameState.getInstance();
    this.gamestate.classicalNote = this;
  }

  public void setText(String text) {
    this.classicalHintText.setText(text);
  }

  // switches back to the classical room
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
