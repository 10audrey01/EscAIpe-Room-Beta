package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

/** Controller class for handling the end screen of the game. */
public class EndController {

  @FXML private Label endLabel;
  @FXML private Button backMainMenuBtn;
  @FXML private Button exitBtn;

  /**
   * Initializes the end screen with the appropriate message based on whether the player escaped or
   * not.
   *
   * @throws URISyntaxException If there is an issue with URI syntax.
   */
  @FXML
  public void initialize() throws URISyntaxException {
    // Present user with different message and music depending on whether they escaped or not
    if (GameState.isEscaped) {
      endLabel.setText("You escaped!");
    } else {
      endLabel.setText("You failed to escape . . .");
    }
  }

  /**
   * Handles the event when the player clicks the "Back to Main Menu" button.
   *
   * @param event The action event triggered by clicking the button.
   * @throws IOException If there is an issue with IO operations.
   */
  @FXML
  private void onBack(ActionEvent event) throws IOException {
    // Reset game state to allow for replay
    GameState.setInstance(null);
    GameState.resetVariables();

    App.setRoot("start");
  }

  /** Handles the event when the player clicks the "Exit" button to close the game. */
  @FXML
  private void onExit() {
    System.exit(0);
  }
}
