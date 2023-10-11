package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

public class PianoTask extends Task {
  public PianoTask() {
    super();
    this.taskDescription = " - Play the piano";
    this.isCompleted = GameState.isPianoPlayed;
  }
}
