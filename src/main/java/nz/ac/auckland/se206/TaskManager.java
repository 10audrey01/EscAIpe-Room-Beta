package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nz.ac.auckland.se206.tasks.HarpTask;
import nz.ac.auckland.se206.tasks.MusicQuizTask;
import nz.ac.auckland.se206.tasks.RiddleTask;
import nz.ac.auckland.se206.tasks.SafeTask;
import nz.ac.auckland.se206.tasks.Task;
import nz.ac.auckland.se206.tasks.TrumpetTask;

/**
 * The TaskManager class is responsible for managing tasks in a game application. It handles both
 * individual tasks and larger tasks. Individual tasks are represented as enumerated values, and
 * larger tasks are represented as another set of enumerated values. Tasks can be generated, added,
 * and completed through this class.
 */
public class TaskManager {

  /** Enum to represent larger tasks in the game. */
  public enum LargeTask {
    RAVE,
    ROCK,
    CLASSICAL
  }

  /** ArrayList to store tasks in the game. */
  private ArrayList<Task> taskList;

  private LargeTask largeTask; // The current large task

  /** ArrayList to store tasks in the game. */
  private ArrayList<Task> taskList;

  /**
   * Constructs a TaskManager instance and initializes the list of individual tasks by generating
   * them in a randomized order.
   */
  public TaskManager() {
    this.taskList = new ArrayList<Task>();
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
    ArrayList<Task> availableTasks =
        new ArrayList<Task>(
            List.of(new MusicQuizTask(), new HarpTask(), new TrumpetTask(), new SafeTask()));
    Collections.shuffle(availableTasks); // Shuffle the available tasks to randomize them
    taskList = availableTasks;

    this.largeTask = LargeTask.ROCK; // Set the initial large task
    // change a random task in the tasklist to be the large task (RiddleTask)
    int randomTaskIndex = (int) (Math.random() * taskList.size());
    taskList.set(randomTaskIndex, new RiddleTask());
  }

  /**
   * Updates task at specific index.
   *
   * @param taskIndex The index of the task to update.
   * @param task The task to update to.
   */
  public void updateTask(int taskIndex, Task task) {
    this.taskList.set(taskIndex, task);
  }

  /**
   * Gets the task at specific index.
   *
   * @param taskIndex The index of the task to get.
   */
  public Task getTask(int taskIndex) {
    return this.taskList.get(taskIndex);
  }

  /** Gets the current task list instance. */
  public ArrayList<Task> getTaskList() {
    return this.taskList;
  }

  /**
   * Get index of a specific task.
   *
   * @param task The task to get the index of.
   */
  public int getTaskIndex(Class<? extends Task> task) {
    // check what the task is and return its index in taskList
    for (int i = 0; i < this.taskList.size(); i++) {
      if (this.taskList.get(i).getClass() == task) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Gets the task list.
   *
   * @return The task list.
   */
  public ArrayList<Task> getTaskList() {
    return this.taskList;
  }
}
