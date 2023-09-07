package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class EndController {

  @FXML private Label endLabel;
  @FXML private Button backMainMenuBtn;
  @FXML private Button exitBtn;

  @FXML
  public void initialize() throws URISyntaxException {
    // Present user with different message and music depending on whether they escaped or not
    if (GameState.isEscaped) {
      endLabel.setText("You escaped!");
    } else {
      endLabel.setText("You failed to escape . . .");
    }
  }

  @FXML
  private void onBack(ActionEvent event) throws IOException {
    GameState.isRiddleResolved = false;
    GameState.isKeyFound = false;
    GameState.isEscaped = false;

    Button current = (Button) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.START));
  }

  @FXML
  private void onExit() {
    System.exit(0);
  }
}
