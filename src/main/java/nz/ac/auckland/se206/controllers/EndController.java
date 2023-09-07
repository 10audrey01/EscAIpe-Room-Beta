package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.controllers.rooms.ClassicalController;
import nz.ac.auckland.se206.controllers.rooms.RaveController;
import nz.ac.auckland.se206.controllers.rooms.RockController;

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

    // Stop all timers to allow replayability
    RockController.stopTimer();
    RaveController.stopTimer();
    ClassicalController.stopTimer();
    // ChatController.stopTimer();
  }

  @FXML
  private void onBack() throws IOException {
    App.setRoot("start");
  }

  @FXML
  private void onExit() {
    System.exit(0);
  }
}
