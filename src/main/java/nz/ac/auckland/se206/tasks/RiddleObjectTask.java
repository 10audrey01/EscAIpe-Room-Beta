package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

public class RiddleObjectTask extends Task {
  public RiddleObjectTask() {
    super();
    this.taskDescription = "- Find the object";
    this.isCompleted = GameState.isRiddleObjectFound;
    this.hasMoreTasks = true;
    this.nextTask = new GuitarTask();
  }
}
