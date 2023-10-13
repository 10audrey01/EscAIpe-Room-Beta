package nz.ac.auckland.se206.tasks;

public class Task {
  protected String taskDescription;
  protected boolean isCompleted;
  protected boolean hasMoreTasks;
  protected Task nextTask;

  public String getTaskDescription() {
    return this.taskDescription;
  }

  public boolean isCompleted() {
    return this.isCompleted;
  }

  public void setCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

  public boolean hasMoreTasks() {
    return this.hasMoreTasks;
  }

  public Task getNextTask() {
    return this.nextTask;
  }
}
