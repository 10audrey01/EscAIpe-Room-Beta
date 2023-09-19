package nz.ac.auckland.se206;

import nz.ac.auckland.se206.GameState.Difficulty;

public class HintManager {
  private int hintsRemaining;

  public HintManager(GameState.Difficulty difficulty) {
    if (difficulty == Difficulty.EASY) {
      this.hintsRemaining = 999;
    }
    if (difficulty == Difficulty.MEDIUM) {
      this.hintsRemaining = 5;
    }
    if (difficulty == Difficulty.HARD) {
      this.hintsRemaining = 0;
    }
  }

  public int getHintsRemaining() {
    return this.hintsRemaining;
  }
}
