package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

public class GuitarTask extends Task {
  public GuitarTask() {
    super();
    this.taskDescription = "- Play the guitars";
    this.isCompleted = GameState.isGuitarsPlayed;
  }
}
