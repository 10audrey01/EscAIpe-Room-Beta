package nz.ac.auckland.se206;

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

    private final int time;

    PlayTime(int time) {
      this.time = time;
    }

    public int getTime() {
      return this.time;
    }
  }

  // variables to keep track of the game state
  private static GameState instance;
  public static boolean isRiddleResolved = false;
  public static boolean isKeyFound = false;
  public static Difficulty difficulty;
  public static PlayTime time;

  public TimeManager timeManager;

  public static GameState getInstance() {
    if (instance == null) {
      instance = new GameState();
    }
    return instance;
  }

  public void resetGame() {
    this.timeManager = new TimeManager();
  }
}
