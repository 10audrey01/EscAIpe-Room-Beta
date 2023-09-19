package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

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
    GameState.setInstance(null);
    GameState.resetVariables();

    App.setRoot("start");
  }

  @FXML
  private void onExit() {
    System.exit(0);
  }
}
