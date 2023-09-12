package nz.ac.auckland.se206.puzzles;

import java.util.Random;

public class RavePuzzle {
  // rave puzzle - Find the safe, unlock the code through finding respective object in each of rock
  // and classical rooms
  private boolean isSafeFound;
  private boolean isSafeSolved;
  private String safeSolution;

  public RavePuzzle() {
    this.isSafeFound = false;
    this.isSafeSolved = false;

    String solution = "";
    // generate random solution
    Random random = new Random();
    for (int i = 0; i < 6; i++) {
      int currentNumber = random.nextInt(10);
      solution += Integer.toString(currentNumber);
    }

    this.safeSolution = solution;
  }

  // sets the isSafeFound to true
  public void setFoundSafe() {
    this.isSafeFound = true;
  }

  // attempts to solve the safe
  public boolean attemptSolveSafe(String input) {
    if (input.equals(safeSolution)) {
      this.isSafeSolved = true;
      return true;
    }
    return false;
  }
}
