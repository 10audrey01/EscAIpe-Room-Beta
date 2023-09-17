package nz.ac.auckland.se206;

import nz.ac.auckland.se206.controllers.rooms.rave.BodybuilderController;
import nz.ac.auckland.se206.puzzles.RavePuzzle;

/** Represents the state of the game. */
public class GameState {
  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }

  public enum PlayTime {
    TWO(2),
    FOUR(4),
    SIX(6);

    private final Integer time;

    PlayTime(Integer time) {
      this.time = time;
    }

    public Integer getTime() {
      return this.time;
    }
  }

  // variables to keep track of the game state
  private static GameState instance;
  public static boolean isRiddleResolved = false;
  public static boolean isRiddleObjectFound = false;
  public static boolean isNoteSequenceFound = false;
  public static boolean isKeyFound = false;
  public static Difficulty difficulty;
  public static PlayTime time;
  public static boolean isEscaped = false;

  public TimeManager timeManager;
  public TaskManager taskManager;
  public ChatManager chatManager;
  public RockBigTaskManager rockBigTaskManager;
  public RavePuzzle ravePuzzle;

  public BodybuilderController bodybuilderController;

  public static GameState getInstance() {
    if (instance == null) {
      instance = new GameState();
      instance.timeManager = new TimeManager();
      instance.taskManager = new TaskManager();
      instance.chatManager = new ChatManager();
      instance.rockBigTaskManager = new RockBigTaskManager();
      instance.ravePuzzle = new RavePuzzle();
    }
    return instance;
  }

  public static void setInstance(GameState instance) {
    GameState.instance = instance;
  }

  public void startGame() {
    this.taskManager.generateTasks();
    this.timeManager.setTime(time.getTime() * 60);
    this.timeManager.startCountdown();
    this.bodybuilderController.initialiseCode();
    this.ravePuzzle.setHints();
  }
}
