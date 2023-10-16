package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

/**
 * The `HarpTask` class represents a specific task related to playing the harp within a game. This
 * class extends the abstract `Task` class.
 */
public class HarpTask extends Task {
  /**
   * Default constructor for the `HarpTask` class. Initializes the task with a default description
   * to play the harp and its initial fields.
   */
  public HarpTask() {
    super();
    this.taskDescription = "- Play the harp";
    this.isCompleted = GameState.isHarpPlayed;
    this.hasMoreTasks = false;
  }
}
