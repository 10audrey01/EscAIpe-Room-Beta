package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nz.ac.auckland.se206.puzzles.Puzzle;
import nz.ac.auckland.se206.puzzles.RavePuzzle;

public class TaskManager {
  public enum Task {
    FINDOBJECT,
    SAFECODE,
    PLAYPIANO,
    SELECTGUITAR,
    MUSICQUIZ
  }

  public enum LargeTask {
    RAVE,
    ROCK,
    CLASSICAL
  }

  public ArrayList<Task> tasks;
  public ArrayList<Task> completedTasks;
  public LargeTask largeTask;
  private boolean isLargeTaskSolved;
  private Puzzle largeTaskInstance;

  public TaskManager() {
    this.tasks = new ArrayList<Task>();
    generateTasks();
  }

  public LargeTask getCurrentLargeTask() {
    return this.largeTask;
  }

  public Puzzle getLargeTaskInstance() {
    return this.largeTaskInstance;
  }

  public void generateTasks() {
    List<Task> availableTasks = new ArrayList<Task>(List.of(Task.values()));
    Collections.shuffle(availableTasks);

    for (int i = 0; i < 4; i++) {
      Task selectedTask = availableTasks.get(i);
      tasks.add(selectedTask);
      System.out.println(selectedTask);
    }

    // LargeTask[] possibleLargeTasks = {LargeTask.RAVE, LargeTask.ROCK, LargeTask.CLASSICAL};
    // Random random = new Random();
    // int randomNumber = random.nextInt(3);
    // this.largeTask = possibleLargeTasks[randomNumber];

    this.largeTask = LargeTask.RAVE;

    if (this.largeTask == LargeTask.RAVE) {
      this.largeTaskInstance = new RavePuzzle();
    }
  }

  public void addTask(Task thisTask) {
    this.tasks.add(thisTask);
  }

  public void completeTask(Task thisTask) {
    this.tasks.remove(thisTask);
    this.completedTasks.add(thisTask);
  }

  public void completeLargeTask() {
    this.isLargeTaskSolved = true;
  }
}
