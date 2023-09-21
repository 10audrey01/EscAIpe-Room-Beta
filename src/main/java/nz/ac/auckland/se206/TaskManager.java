package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskManager {
  // Enum to represent individual tasks
  public enum Task {
    FINDOBJECT,
    SAFECODE,
    PLAYPIANO,
    SELECTGUITAR,
    MUSICQUIZ,
    PLAYHARP
  }

  // Enum to represent larger tasks
  public enum LargeTask {
    RAVE,
    ROCK,
    CLASSICAL
  }

  public ArrayList<Task> tasks; // List to store individual tasks
  public ArrayList<Task> completedTasks; // List to store completed individual tasks
  public LargeTask largeTask; // The current large task

  public TaskManager() {
    this.tasks = new ArrayList<Task>();
    generateTasks(); // Initialize tasks when creating a TaskManager instance
  }

  public LargeTask getCurrentLargeTask() {
    return this.largeTask; // Get the current large task
  }

  public void generateTasks() {
    List<Task> availableTasks = new ArrayList<Task>(List.of(Task.values()));
    Collections.shuffle(availableTasks); // Shuffle the available tasks to randomize them

    for (int i = 0; i < availableTasks.size(); i++) {
      Task selectedTask = availableTasks.get(i);
      tasks.add(selectedTask); // Add selected tasks to the list
      System.out.println(selectedTask); // Print the selected task (for debugging)
    }

    this.largeTask = LargeTask.ROCK;
  }

  public void addTask(Task thisTask) {
    this.tasks.add(thisTask); // Add an individual task to the list
  }

  public void completeTask(Task thisTask) {
    this.tasks.remove(thisTask); // Remove a completed individual task from the list
    this.completedTasks.add(thisTask); // Add the completed task to the completed tasks list
  }
}
