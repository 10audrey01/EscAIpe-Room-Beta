package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

public class HarpTask extends Task {
  public HarpTask() {
    super();
    this.taskDescription = "- Play the harp";
    this.isCompleted = GameState.isHarpPlayed;
  }
}
