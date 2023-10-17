package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

/**
 * The `pianoTask` class represents a specific task related to playing the piano within a game. This
 * class extends the abstract `Task` class.
 */
public class PianoTask extends Task {
  /**
   * Default constructor for the `PianoTask` class. Initializes the task with a default description
   * and its initial fields.
   */
  public PianoTask() {
    super();
    this.taskDescription = "- Play the piano";
    this.isCompleted = GameState.isPianoPlayed;
    this.hasMoreTasks = false;
  }
}
