package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

/**
 * The `RiddleTask` class represents a specific task related to playing the riddle event within a
 * game. This class extends the abstract `Task` class.
 */
public class RiddleTask extends Task {
  /**
   * Default constructor for the `RiddleTask` class. Initializes the task with a default description
   * and its initial fields.
   */
  public RiddleTask() {
    super();
    this.taskDescription = "- Talk to the guitarist";
    this.isCompleted = GameState.isRiddleSolved;
    this.hasMoreTasks = true;
    this.nextTask = new RiddleObjectTask();
  }
}
