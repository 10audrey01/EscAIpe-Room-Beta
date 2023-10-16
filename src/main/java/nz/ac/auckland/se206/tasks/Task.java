package nz.ac.auckland.se206.tasks;

/**
 * The `Task` class serves as a basic representation of a task. It encapsulates properties and
 * methods for managing tasks, including their descriptions, completion status, and related tasks.
 */
public class Task {
  // protected fields related to the progresson of the task.
  protected String taskDescription;
  protected boolean isCompleted;
  protected boolean hasMoreTasks;
  protected Task nextTask;

  /**
   * Retrieves the description of the task.
   *
   * @return The description of the task.
   */
  public String getTaskDescription() {
    return this.taskDescription;
  }

  /**
   * Checks if the task is marked as completed.
   *
   * @return `true` if the task is completed, `false` otherwise.
   */
  public boolean isCompleted() {
    return this.isCompleted;
  }

  /**
   * Sets the completion status of the task.
   *
   * @param isCompleted `true` to mark the task as completed, `false` to mark it as incomplete.
   */
  public void setCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

  /**
   * Determines if there are more related tasks.
   *
   * @return `true` if there are more tasks related to the current task, `false` otherwise.
   */
  public boolean hasMoreTasks() {
    return this.hasMoreTasks;
  }

  /**
   * Retrieves a reference to the next task in the sequence.
   *
   * @return The next task in the sequence, or `null` if there are no more tasks.
   */
  public Task getNextTask() {
    return this.nextTask;
  }
}
