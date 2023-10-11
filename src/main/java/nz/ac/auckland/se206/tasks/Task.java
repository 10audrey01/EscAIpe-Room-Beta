package nz.ac.auckland.se206.tasks;

public class Task {
  protected String taskDescription;
  protected boolean isCompleted;

  public Task() {
    this.isCompleted = false;
  }

  public String getTaskDescription() {
    return this.taskDescription;
  }
}
