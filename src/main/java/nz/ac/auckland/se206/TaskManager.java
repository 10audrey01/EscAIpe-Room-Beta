package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  public void generateTasks() {
    List<Task> availableTasks = new ArrayList<Task>(List.of(Task.values()));
    Collections.shuffle(availableTasks);

    for (int i = 0; i < 4; i++) {
      Task selectedTask = availableTasks.get(i);
      tasks.add(selectedTask);
      System.out.println(selectedTask);
    }
  }

  public void addTask(Task thisTask) {
    this.tasks.add(thisTask);
  }

  public void completeTask(Task thisTask) {
    this.tasks.remove(thisTask);
    this.completedTasks.add(thisTask);
  }
}
