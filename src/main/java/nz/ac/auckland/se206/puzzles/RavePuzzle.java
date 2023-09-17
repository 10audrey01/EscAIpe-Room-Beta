package nz.ac.auckland.se206.puzzles;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;

public class RavePuzzle {
  // rave puzzle - Find the safe, unlock the code through finding respective object in each of rock
  // and classical rooms
  private GameState gamestate;
  private boolean isSafeFound;
  private boolean isSafeSolved;
  private String safeSolution;
  private ArrayList<Text> notes;

  public RavePuzzle() {
    this.gamestate = GameState.getInstance();
    this.isSafeFound = false;
    this.isSafeSolved = false;
    this.notes = new ArrayList<Text>();

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

  public void addNote(Text note) {
    notes.add(note);
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

    Random random = new Random();
    int randomNum = random.nextInt(3);

    if (notes.size() == 2) {
      if (randomNum == 1) {
        Platform.runLater(
            () -> {
              notes.get(0).setText(firstHalfOfSolution);
              notes.get(1).setText(secondHalfOfSolution);
            });
      } else {
        Platform.runLater(
            () -> {
              notes.get(1).setText(firstHalfOfSolution);
              notes.get(0).setText(secondHalfOfSolution);
            });
      }
    } else {
      System.out.println("Note size erro");
    }
  }
}
