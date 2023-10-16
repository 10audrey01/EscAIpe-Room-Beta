package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

/**
 * The `RiddeObjectTask` class represents a specific task related to playing the RiddeObject event
 * within a game. This class extends the abstract `Task` class.
 */
public class RiddleObjectTask extends Task {
  /**
   * Default constructor for the `RiddleObjectTask` class. Initializes the task with a default
   * description and its initial fields.
   */
  public RiddleObjectTask() {
    super();
    this.taskDescription = "- Find the object";
    this.isCompleted = GameState.isRiddleObjectFound;
    this.hasMoreTasks = true;
    this.nextTask = new GuitarTask();
  }
}
