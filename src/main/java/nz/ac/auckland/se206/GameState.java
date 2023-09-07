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

    private final Integer time;

    PlayTime(Integer time) {
      this.time = time;
    }

    public Integer getTime() {
      return this.time;
    }
  }

  public static final String App = null;

  // variables to keep track of the game state
  public static Difficulty difficulty;
  public static PlayTime time;
  public static boolean isRiddleResolved = false;
  public static boolean isKeyFound = false;
  public static boolean isEscaped = false;
}
