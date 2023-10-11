package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

public class RiddleTask extends Task {
  public RiddleTask() {
    super();
    this.taskDescription = "- Talk to the guitarist";
    this.isCompleted = GameState.isRiddleSolved;
  }
}
