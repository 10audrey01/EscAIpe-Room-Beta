package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.GameState.Difficulty;

/** Manages hints in the game, including hint counts and hint labels. */
public class HintManager {

  private int hintsRemaining; // Stores the number of hints remaining
  private ArrayList<Label> hintLabels; // Stores labels for displaying hint information

  /** Initializes a new HintManager instance. */
  public HintManager() {
    this.hintLabels = new ArrayList<Label>(); // Initialize the list of hint labels
  }

  /**
   * Initializes the HintManager based on the game difficulty.
   *
   * @param difficulty The game difficulty level.
   */
  public void initialiseManager(GameState.Difficulty difficulty) {
    // Initialize hint count based on difficulty level
    if (difficulty == Difficulty.EASY) {
      this.hintsRemaining = 999; // Set a high number of hints for EASY difficulty
    } else if (difficulty == Difficulty.MEDIUM) {
      this.hintsRemaining = 5; // Set a maximum of 5 hints for MEDIUM difficulty
    } else if (difficulty == Difficulty.HARD) {
      this.hintsRemaining = 0; // No hints available for HARD difficulty
    }

    // Update the hint labels to display the initial hint count
    Platform.runLater(
        () -> {
          for (Label label : hintLabels) {
            label.setText(("Hints Left: " + hintsRemaining));
          }
        });
  }

  /**
   * Adds a hint label to the list for updating hint information.
   *
   * @param label The hint label to add.
   */
  public void addHintLabel(Label label) {
    hintLabels.add(label);
  }

  /** Uses a hint, decrements the hint count, and updates the hint labels. */
  public void useHint() {
    if (this.hintsRemaining == 0) {
      return; // If no hints are remaining, do nothing
    }
    this.hintsRemaining--; // Decrement the hint count
    // Update the hint labels to display the updated hint count
    Platform.runLater(
        () -> {
          for (Label label : hintLabels) {
            label.setText(("Hints Left: " + hintsRemaining));
          }
        });
  }

  /**
   * Gets the number of hints remaining.
   *
   * @return The number of hints remaining.
   */
  public int getHintsRemaining() {
    return this.hintsRemaining;
  }
}
