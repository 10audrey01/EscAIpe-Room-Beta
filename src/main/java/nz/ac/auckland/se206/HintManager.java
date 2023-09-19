package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.GameState.Difficulty;

public class HintManager {
  private int hintsRemaining;
  private ArrayList<Label> hintLabels;

  public HintManager() {
    this.hintLabels = new ArrayList<Label>();
  }

  public void initialiseManager(GameState.Difficulty difficulty) {
    if (difficulty == Difficulty.EASY) {
      this.hintsRemaining = 999;
    }
    if (difficulty == Difficulty.MEDIUM) {
      this.hintsRemaining = 5;
    }
    if (difficulty == Difficulty.HARD) {
      this.hintsRemaining = 0;
    }

    Platform.runLater(
        () -> {
          for (Label label : hintLabels) label.setText(("Hints Left: " + hintsRemaining));
        });
  }

  public void addHintLabel(Label label) {
    hintLabels.add(label);
  }

  public void useHint() {
    this.hintsRemaining--;
    Platform.runLater(
        () -> {
          for (Label label : hintLabels) label.setText(("Hints Left: " + hintsRemaining));
        });
  }

  public int getHintsRemaining() {
    return this.hintsRemaining;
  }
}
