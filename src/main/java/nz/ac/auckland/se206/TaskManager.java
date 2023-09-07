package nz.ac.auckland.se206;

import java.util.ArrayList;

public class TaskManager {
  public enum Task {
    FINDOBJECT,
    SAFECODE,
    PLAYPIANO,
    SELECTGUITAR,
    MUSICQUIZ
  }

  public ArrayList<Task> tasks;
  public ArrayList<Task> completedTasks;

  public TaskManager() {
    this.tasks = new ArrayList<Task>();
  }

  public void addTask(Task thisTask) {
    this.tasks.add(thisTask);
  }

  public void completeTask(Task thisTask) {
    this.tasks.remove(thisTask);
    this.completedTasks.add(thisTask);
  }
}
