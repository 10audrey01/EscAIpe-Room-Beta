package nz.ac.auckland.se206.controllers.rooms.rave;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class MusicQuizController {
  @FXML private TextArea speechBox;
  @FXML private Button btnReturn;

  private GameState gamestate;
  private String[] genres = {
    "techno", "pop", "hip hop", "rnb", "kpop", "funk", "house", "drum and bass"
  };

  @FXML
  private void initialize() throws IOException {
    this.gamestate = GameState.getInstance();
    this.speechBox.setText("Hey man, I need your help identifying this music...");
  }

  // switches back to the rave room
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }
}
