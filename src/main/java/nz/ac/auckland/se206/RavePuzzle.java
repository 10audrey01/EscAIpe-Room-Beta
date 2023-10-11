package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.controllers.rooms.notes.ClassicalNoteController;
import nz.ac.auckland.se206.controllers.rooms.notes.RockNoteController;

/**
 * A class representing the Rave Puzzle, which involves finding a safe and unlocking its code by
 * finding specific objects in both the Rock and Classical rooms.
 */
public class RavePuzzle {

  private String safeSolution; // Stores the randomly generated safe code solution
  private String firstHalfRoom; // Stores the room where the first half of the code is displayed

  /** Initializes a new instance of the RavePuzzle class. Generates a random safe code solution. */
  public RavePuzzle() {
    // Generate random solution
    Random random = new Random();
    StringBuilder solutionBuilder = new StringBuilder();

    for (int i = 0; i < 6; i++) {
      int currentNumber = random.nextInt(10);
      solutionBuilder.append(currentNumber);
    }

    String solution = solutionBuilder.toString();

    System.out.println(solution);

    this.safeSolution = solution; // Initialize the safe code solution
  }

  /**
   * Retrieves the generated safe code solution.
   *
   * @return The safe code solution as a string.
   */
  public String getSolution() {
    return this.safeSolution;
  }

  /**
   * Retrieves the room where the first half of the code is displayed.
   *
   * @return The room name as a string.
   */
  public String getFirstHalfRoom() {
    return this.firstHalfRoom;
  }

  /**
   * Sets hints for the puzzle in the Classical and Rock rooms by distributing the code halves.
   *
   * @param classical The ClassicalNoteController to set the hint in.
   * @param rock The RockNoteController to set the hint in.
   */
  public void setHints(ClassicalNoteController classical, RockNoteController rock) {
    System.out.println("Solution-" + safeSolution);

    // Split the solution into halves
    String firstHalfOfSolution = this.safeSolution.substring(0, 3);
    String secondHalfOfSolution = this.safeSolution.substring(3, 6);

    // Randomly select a room to have the first half of the solution
    Random random = new Random();
    int randomNum = random.nextInt(2);

    if (randomNum == 1) {
      // If the first room will be in the rock room, update the note controllers for the respective
      // rooms
      this.firstHalfRoom = "rock";
      Platform.runLater(
          () -> {
            classical.setText(secondHalfOfSolution);
            rock.setText(firstHalfOfSolution);
          });
    } else {
      // If the first room will be in the classical room, update the note controllers for the
      // respective rooms
      this.firstHalfRoom = "classical";
      Platform.runLater(
          () -> {
            classical.setText(firstHalfOfSolution);
            rock.setText(secondHalfOfSolution);
          });
    }
  }

  /**
   * Sets images for the puzzle in the ImageView elements.
   *
   * @param first The first ImageView element.
   * @param second The second ImageView element.
   * @throws IOException If an error occurs while loading image resources.
   */
  public void setImages(ImageView first, ImageView second) throws IOException {
    // Load the rock / classical image sprites
    Image rock = new Image(App.class.getResource("/images/misc/rockicon.png").openStream());
    Image classical =
        new Image(App.class.getResource("/images/misc/classicalicon.png").openStream());
    Platform.runLater(
        // Update the GUI for each image
        () -> {
          if (this.firstHalfRoom.equals("rock")) {
            // If the first half is in the rock room, the first image should be the rock sprite
            first.setImage(rock);
            second.setImage(classical);
          } else {
            // Otherwise, the order should be reversed
            first.setImage(classical);
            second.setImage(rock);
          }
        });
  }
}
