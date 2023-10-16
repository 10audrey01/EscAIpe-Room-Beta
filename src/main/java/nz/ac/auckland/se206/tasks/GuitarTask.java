package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

/**
 * The `GuitarTask` class represents a specific task related to playing the guitar within a game.
 * This class extends the abstract `Task` class.
 */
public class GuitarTask extends Task {
  /**
   * Default constructor for the `GuitarTask` class. Initializes the task with a default description
   * and its initial fields.
   */
  public GuitarTask() {
    super();
    this.taskDescription = "- Play the guitars";
    this.isCompleted = GameState.isGuitarsPlayed;
    this.hasMoreTasks = true;
    this.nextTask = new PianoTask();
  }
}
