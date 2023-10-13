package nz.ac.auckland.se206.tasks;

import nz.ac.auckland.se206.GameState;

public class MusicQuizTask extends Task {
  public MusicQuizTask() {
    super();
    this.taskDescription = "- Help the DJ";
    this.isCompleted = GameState.isMusicQuizCompleted;
    this.hasMoreTasks = false;
  }
}
