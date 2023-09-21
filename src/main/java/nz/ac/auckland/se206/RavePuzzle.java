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

  private String safeSolution; // Stores the randomly generated safe code solution
  private String firstHalfRoom; // Stores the room where the first half of the code is displayed

  public RavePuzzle() {

    String solution = "";
    // generate random solution
    Random random = new Random();
    for (int i = 0; i < 6; i++) {
      int currentNumber = random.nextInt(10);
      solution += Integer.toString(currentNumber);
    }

    System.out.println(solution);

    this.safeSolution = solution; // Initialize the safe code solution
  }

  public String getSolution() {
    return this.safeSolution; // Retrieve the safe code solution
  }

  public String getFirstHalfRoom() {
    return this.firstHalfRoom; // Retrieve the room where the first half of the code is displayed
  }

  // Set hints for the puzzle in the Classical and Rock rooms
  public void setHints(ClassicalNoteController classical, RockNoteController rock) {
    System.out.println("Solution-" + safeSolution);

    // split the solution into halves
    String firstHalfOfSolution = this.safeSolution.substring(0, 3);
    String secondHalfOfSolution = this.safeSolution.substring(3, 6);

    // randomly select a room to have the first half of the solution
    Random random = new Random();
    int randomNum = random.nextInt(2);

    if (randomNum == 1) {
      // if the first room will be in the rock room, update the note controllers for the respective
      // rooms
      this.firstHalfRoom = "rock";
      Platform.runLater(
          () -> {
            classical.setText(secondHalfOfSolution);
            rock.setText(firstHalfOfSolution);
          });
    } else {
      // if the first room will be in the classical room, update the note controllers for the
      // respective rooms
      this.firstHalfRoom = "classical";
      Platform.runLater(
          () -> {
            classical.setText(firstHalfOfSolution);
            rock.setText(secondHalfOfSolution);
          });
    }
  }

  // Set images for the puzzle in the ImageView elements
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
