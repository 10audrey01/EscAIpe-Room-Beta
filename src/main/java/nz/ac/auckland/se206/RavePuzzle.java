package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.controllers.rooms.notes.ClassicalNoteController;
import nz.ac.auckland.se206.controllers.rooms.notes.RockNoteController;

public class RavePuzzle {
  // rave puzzle - Find the safe, unlock the code through finding respective object in each of rock
  // and classical rooms
  private GameState gamestate;
  private boolean isSafeSolved;
  private String safeSolution;
  private String firstHalfRoom;

  public RavePuzzle() {
    this.gamestate = GameState.getInstance();
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

  // attempts to solve the safe
  public boolean attemptSolveSafe(String input) {
    if (input.equals(safeSolution)) {
      this.isSafeSolved = true;
      return true;
    }
    return false;
  }

  public String getFirstHalfRoom() {
    return this.firstHalfRoom;
  }

  public void setHints(ClassicalNoteController classical, RockNoteController rock) {
    System.out.println("Solution-" + safeSolution);
    String firstHalfOfSolution = this.safeSolution.substring(0, 3);
    String secondHalfOfSolution = this.safeSolution.substring(3, 6);

    Random random = new Random();
    int randomNum = random.nextInt(2);

    if (randomNum == 1) {
      this.firstHalfRoom = "rock";
      Platform.runLater(
          () -> {
            classical.setText(secondHalfOfSolution);
            rock.setText(firstHalfOfSolution);
          });
      System.out.println("Order = rock classical");
    } else {
      this.firstHalfRoom = "classical";
      Platform.runLater(
          () -> {
            classical.setText(firstHalfOfSolution);
            rock.setText(secondHalfOfSolution);
          });
      System.out.println("Order = classical roc");
    }
  }

  public void setImages(ImageView first, ImageView second) throws IOException {
    Image rock = new Image(App.class.getResource("/images/misc/rockicon.png").openStream());
    Image classical =
        new Image(App.class.getResource("/images/misc/classicalicon.png").openStream());
    Platform.runLater(
        () -> {
          if (this.firstHalfRoom.equals("rock")) {
            first.setImage(rock);
            second.setImage(classical);
          } else {
            first.setImage(classical);
            second.setImage(rock);
          }
        });
  }
}
