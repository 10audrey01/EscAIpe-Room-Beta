package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nz.ac.auckland.se206.tasks.HarpTask;
import nz.ac.auckland.se206.tasks.MusicQuizTask;
import nz.ac.auckland.se206.tasks.PianoTask;
import nz.ac.auckland.se206.tasks.RiddleTask;
import nz.ac.auckland.se206.tasks.Task;
import nz.ac.auckland.se206.tasks.TrumpetTask;

/**
 * The TaskManager class is responsible for managing tasks in a game application. It handles both
 * individual tasks and larger tasks. Individual tasks are represented as enumerated values, and
 * larger tasks are represented as another set of enumerated values. Tasks can be generated, added,
 * and completed through this class.
 */
public class TaskManager {
  /** ArrayList to store tasks in the game. */
  public ArrayList<Task> taskList;

  /** Enum to represent larger tasks in the game. */
  public enum LargeTask {
    RAVE,
    ROCK,
    CLASSICAL
  }

  private LargeTask largeTask; // The current large task

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
    List<Task> availableTasks =
        new ArrayList<Task>(
            List.of(
                new MusicQuizTask(),
                new HarpTask(),
                new RiddleTask(),
                new PianoTask(),
                new TrumpetTask()));
    Collections.shuffle(availableTasks); // Shuffle the available tasks to randomize them

    this.largeTask = LargeTask.ROCK; // Set the initial large task
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
}
