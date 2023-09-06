package nz.ac.auckland.se206;

/** Represents the state of the game. */
public class GameState {
  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }

  public enum PlayTime {
    TWO,
    FOUR,
    SIX
  }

  // static reference to itself
  public static boolean isRiddleResolved = false;
  public static boolean isKeyFound = false;

  private static GameState instance;

  // returns the current instance of the gamestate. Only one will exist
  public static GameState getInstance() {
    if (instance == null) {
      instance = new GameState();
    }
    return instance;
  }

  public Difficulty difficulty;
  public PlayTime time;

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void setTime(PlayTime time) {
    this.time = time;
  }

  public Difficulty getDifficulty() {
    return this.difficulty;
  }

  public PlayTime getTime() {
    return this.time;
  }
}
