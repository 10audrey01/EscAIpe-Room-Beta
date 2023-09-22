package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ObjectiveListManager {

  private ArrayList<Label> objectiveLabel1;
  private ArrayList<Label> objectiveLabel2;
  private ArrayList<Label> objectiveLabel3;
  private ArrayList<Label> objectiveLabel4;
  private ArrayList<ImageView> step1Key;
  private ArrayList<ImageView> step2Key;
  private ArrayList<ImageView> step3Key;
  private ArrayList<ImageView> step4Key;

  public ObjectiveListManager() {
    // Initialize lists for objective labels and step keys
    this.objectiveLabel1 = new ArrayList<Label>();
    this.objectiveLabel2 = new ArrayList<Label>();
    this.objectiveLabel3 = new ArrayList<Label>();
    this.objectiveLabel4 = new ArrayList<Label>();
    this.step1Key = new ArrayList<ImageView>();
    this.step2Key = new ArrayList<ImageView>();
    this.step3Key = new ArrayList<ImageView>();
    this.step4Key = new ArrayList<ImageView>();
  }

  // Add an objective label for step 1
  public void addObjectiveLabel1(Label objectiveLabel) {
    this.objectiveLabel1.add(objectiveLabel);
  }

  // Add an objective label for step 2
  public void addObjectiveLabel2(Label objectiveLabel) {
    this.objectiveLabel2.add(objectiveLabel);
  }

  // Add an objective label for step 3
  public void addObjectiveLabel3(Label objectiveLabel) {
    this.objectiveLabel3.add(objectiveLabel);
  }

  // Add an objective label for step 4
  public void addObjectiveLabel4(Label objectiveLabel) {
    this.objectiveLabel4.add(objectiveLabel);
  }

  // Add an ImageView representing a key for step 1
  public void addStep1Key(ImageView step1Key) {
    this.step1Key.add(step1Key);
  }

  // Add an ImageView representing a key for step 2
  public void addStep2Key(ImageView step2Key) {
    this.step2Key.add(step2Key);
  }

  // Add an ImageView representing a key for step 3
  public void addStep3Key(ImageView step3Key) {
    this.step3Key.add(step3Key);
  }

  // Add an ImageView representing a key for step 4
  public void addStep4Key(ImageView step4Key) {
    this.step4Key.add(step4Key);
  }

  // Mark objective 1 as completed if the music quiz is completed
  public void completeObjective1() {
    if (GameState.isMusicQuizCompleted) {
      strikeThroughLabels(this.objectiveLabel1); // Apply strikethrough effect to labels
      setVisibilityKeyBlue1(true); // Set visibility of step 1 key to true
    }
  }

  // Mark objective 2 as completed if the safe is opened
  public void completeObjective2() {
    if (GameState.isSafeOpened) {
      strikeThroughLabels(this.objectiveLabel2); // Apply strikethrough effect to labels
      setVisibilityKeyGreen2(true); // Set visibility of step 2 key to true
    }
  }

  // Mark objective 3 as completed if the riddle is resolved
  public void completeObjective3() {
    if (GameState.isRiddleResolved) {
      strikeThroughLabels(this.objectiveLabel3); // Apply strikethrough effect to labels
    }
  }

  // Mark objective 4 as completed if the harp is played
  public void completeObjective4() {
    if (GameState.isHarpPlayed) {
      strikeThroughLabels(this.objectiveLabel4); // Apply strikethrough effect to labels
      setVisibilityKeyYellow4(true); // Set visibility of step 4 key to true
    }
  }

  // Apply strikethrough effect to a list of labels
  public void strikeThroughLabels(ArrayList<Label> labels) {
    for (Label label : labels) {
      // go through every label and apply css values to every label in the gui for the objective
      // list.
      Text text = new Text(label.getText());
      text.setStyle(
          "-fx-stroke: black; -fx-stroke-width: 0px; -fx-font-size: 16px; -fx-strikethrough: true;"
              + " -fx-fill: black;");
      label.setText("");
      label.setGraphic(text);
    }
  }

  // Set visibility of step 1 key ImageView(s)
  public void setVisibilityKeyBlue1(boolean visibility) {
    for (ImageView key : step1Key) {
      key.setVisible(visibility);
    }
  }

  // Set visibility of step 2 key ImageView(s)
  public void setVisibilityKeyGreen2(boolean visibility) {
    for (ImageView key : step2Key) {
      key.setVisible(visibility);
    }
  }

  // Set visibility of step 3 key ImageView(s)
  public void setVisibilityKeyRed3(boolean visibility) {
    for (ImageView key : step3Key) {
      key.setVisible(visibility);
    }
  }

  // Set visibility of step 4 key ImageView(s)
  public void setVisibilityKeyYellow4(boolean visibility) {
    for (ImageView key : step4Key) {
      key.setVisible(visibility);
    }
  }
}
