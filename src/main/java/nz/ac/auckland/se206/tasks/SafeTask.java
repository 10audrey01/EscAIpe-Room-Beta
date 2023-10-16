package nz.ac.auckland.se206.tasks;

public class SafeTask extends Task {
  public SafeTask() {
    super();
    this.taskDescription = "- Help the bodybuilder";
    this.isCompleted = false;
    this.hasMoreTasks = false;
  }
}
