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

  // variables to keep track of the game state
  public static boolean isRiddleResolved = false;
  public static boolean isKeyFound = false;
  public static Difficulty difficulty;
  public static PlayTime time;
}
