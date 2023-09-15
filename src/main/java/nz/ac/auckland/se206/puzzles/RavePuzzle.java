package nz.ac.auckland.se206.puzzles;

import java.util.Random;
import nz.ac.auckland.se206.GameState;

public class RavePuzzle implements Puzzle {
  // rave puzzle - Find the safe, unlock the code through finding respective object in each of rock
  // and classical rooms
  private GameState gamestate;
  private boolean isSafeFound;
  private boolean isSafeSolved;
  private String safeSolution;

  public RavePuzzle() {
    this.gamestate = GameState.getInstance();
    this.isSafeFound = false;
    this.isSafeSolved = false;

    String solution = "";
    // generate random solution
    Random random = new Random();
    for (int i = 0; i < 6; i++) {
      int currentNumber = random.nextInt(10);
      solution += Integer.toString(currentNumber);
    }

    System.out.println(solution);

    this.safeSolution = solution;
  }

  public String getSolution() {
    return this.safeSolution;
  }

  // sets the isSafeFound to true
  public void setFoundSafe() {
    this.isSafeFound = true;
  }

  // attempts to solve the safe
  public boolean attemptSolveSafe(String input) {
    if (input.equals(safeSolution)) {
      this.isSafeSolved = true;
      this.gamestate.taskManager.completeLargeTask();
      return true;
    }
    return false;
  }

  public void setHints() {
    String firstHalfOfSolution = this.safeSolution.substring(0, 2);
    String secondHalfOfSolution = this.safeSolution.substring(3, 5);

    // TODO: set first note to first half, set second note to second half.
  }
}
