package nz.ac.auckland.se206.tasks;

/**
 * The `SafeTask` class represents a specific task related to playing the safe task within a game.
 * This class extends the abstract `Task` class.
 */
public class SafeTask extends Task {
  /**
   * Default constructor for the `SafeTask` class. Initializes the task with a default description
   * and its initial fields.
   */
  public SafeTask() {
    super();
    this.taskDescription = "- Help the bodybuilder";
    this.isCompleted = false;
    this.hasMoreTasks = false;
  }
}
