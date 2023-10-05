package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The TaskManager class is responsible for managing tasks in a game application. It handles both
 * individual tasks and larger tasks. Individual tasks are represented as enumerated values, and
 * larger tasks are represented as another set of enumerated values. Tasks can be generated, added,
 * and completed through this class.
 */
public class TaskManager {
  /** Enum to represent individual tasks in the game. */
  public enum Task {
    FINDOBJECT,
    SAFECODE,
    PLAYPIANO,
    SELECTGUITAR,
    MUSICQUIZ,
    PLAYHARP
  }

  /** Enum to represent larger tasks in the game. */
  public enum LargeTask {
    RAVE,
    ROCK,
    CLASSICAL
  }

  private ArrayList<Task> tasks; // List to store individual tasks
  private ArrayList<Task> completedTasks; // List to store completed individual tasks
  private LargeTask largeTask; // The current large task

  /**
   * Constructs a TaskManager instance and initializes the list of individual tasks by generating
   * them in a randomized order.
   */
  public TaskManager() {
    this.tasks = new ArrayList<Task>();
    generateTasks(); // Initialize tasks when creating a TaskManager instance
  }

  /**
   * Retrieves the current large task.
   *
   * @return The current large task.
   */
  public LargeTask getCurrentLargeTask() {
    return this.largeTask; // Get the current large task
  }

  /** Generates a randomized list of individual tasks and sets the current large task. */
  public void generateTasks() {
    List<Task> availableTasks = new ArrayList<Task>(List.of(Task.values()));
    Collections.shuffle(availableTasks); // Shuffle the available tasks to randomize them

    for (int i = 0; i < availableTasks.size(); i++) {
      Task selectedTask = availableTasks.get(i);
      tasks.add(selectedTask); // Add selected tasks to the list
      System.out.println(selectedTask); // Print the selected task (for debugging)
    }

    this.largeTask = LargeTask.ROCK; // Set the initial large task
  }

  /**
   * Adds an individual task to the list of tasks.
   *
   * @param thisTask The individual task to add.
   */
  public void addTask(Task thisTask) {
    this.tasks.add(thisTask); // Add an individual task to the list
  }

  /**
   * Marks an individual task as completed by removing it from the list of tasks and adding it to
   * the list of completed tasks.
   *
   * @param thisTask The individual task to mark as completed.
   */
  public void completeTask(Task thisTask) {
    this.tasks.remove(thisTask); // Remove a completed individual task from the list
    this.completedTasks.add(thisTask); // Add the completed task to the completed tasks list
  }
}
