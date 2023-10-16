package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

/**
 * The `MusicQuizTask` class represents a specific task related to the music quiz. This class
 * extends the abstract `Task` class.
 */
public class MusicQuizTask extends Task {
  /**
   * Default constructor for the `MusicQuizTask` class. Initializes the task with a default
   * description and its initial fields.
   */
  public MusicQuizTask() {
    super();
    this.taskDescription = "- Help the DJ";
    this.isCompleted = GameState.isMusicQuizCompleted;
    this.hasMoreTasks = false;
  }
}
