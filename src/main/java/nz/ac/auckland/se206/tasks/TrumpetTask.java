package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

public class TrumpetTask extends Task {
  public TrumpetTask() {
    super();
    this.taskDescription = "- Play the trumpet";
    this.isCompleted = GameState.isTrumpetPlayed;
  }
}
