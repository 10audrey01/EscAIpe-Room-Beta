package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

/**
 * The `TrumpetTask` class represents a specific task related to playing the trumpet within a game.
 * This class extends the abstract `Task` class.
 */
public class TrumpetTask extends Task {
  /**
   * Default constructor for the `TrumpetTask` class. Initializes the task with a default
   * description and its initial fields.
   */
  public TrumpetTask() {
    super();
    this.taskDescription = "- Play the trumpet";
    this.isCompleted = GameState.isTrumpetPlayed;
    this.hasMoreTasks = false;
  }
}
