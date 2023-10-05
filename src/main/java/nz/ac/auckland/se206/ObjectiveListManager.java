package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ObjectiveListManager {

  private ArrayList<ArrayList<Label>> allObjectiveLabels;
  private ArrayList<ArrayList<ImageView>> allStepKeys;

  public ObjectiveListManager() {
    // Initialize lists for objective labels and step keys
    this.allObjectiveLabels = new ArrayList<ArrayList<Label>>();
    this.allStepKeys = new ArrayList<ArrayList<ImageView>>();
  }

  // Add an objective label for any step
  public void addObjectiveLabel(int labelIndex, Label objectiveLabel) {
    this.allObjectiveLabels.get(labelIndex).add(objectiveLabel);
  }

  // Add an objective labels for all steps
  public void addObjectiveLabels(ArrayList<Label> objectiveLabels) {
    this.addObjectiveLabel(0, objectiveLabels.get(0));
    this.addObjectiveLabel(1, objectiveLabels.get(1));
    this.addObjectiveLabel(2, objectiveLabels.get(2));
    this.addObjectiveLabel(3, objectiveLabels.get(3));
  }

  // Add an ImageView representing a key for any step
  public void addStepKey(int keyIndex, ImageView stepKeys) {
    this.allStepKeys.get(keyIndex).add(stepKeys);
  }

  // Add an ImageView representing a key for all steps
  public void addStepKeys(ArrayList<ImageView> stepKeys) {
    this.addStepKey(0, stepKeys.get(0));
    this.addStepKey(1, stepKeys.get(1));
    this.addStepKey(2, stepKeys.get(2));
    this.addStepKey(3, stepKeys.get(3));
  }

  // Mark objective 1 as completed if the music quiz is completed
  public void completeObjective1() {
    if (GameState.isMusicQuizCompleted) {
      strikeThroughLabels(this.allObjectiveLabels.get(0)); // Apply strikethrough effect to labels
      setVisibilityKeyBlue1(true); // Set visibility of step 1 key to true
    }
  }

  // Mark objective 2 as completed if the safe is opened
  public void completeObjective2() {
    if (GameState.isSafeOpened) {
      strikeThroughLabels(this.allObjectiveLabels.get(1)); // Apply strikethrough effect to labels
      setVisibilityKeyGreen2(true); // Set visibility of step 2 key to true
    }
  }

  // Mark objective 3 as completed if the riddle is resolved
  public void completeObjective3() {
    if (GameState.isRiddleResolved) {
      strikeThroughLabels(this.allObjectiveLabels.get(2)); // Apply strikethrough effect to labels
    }
  }

  // Mark objective 4 as completed if the harp is played
  public void completeObjective4() {
    if (GameState.isHarpPlayed) {
      strikeThroughLabels(this.allObjectiveLabels.get(3)); // Apply strikethrough effect to labels
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
    for (ImageView key : allStepKeys.get(0)) {
      key.setVisible(visibility);
    }
  }

  // Set visibility of step 2 key ImageView(s)
  public void setVisibilityKeyGreen2(boolean visibility) {
    for (ImageView key : allStepKeys.get(1)) {
      key.setVisible(visibility);
    }
  }

  // Set visibility of step 3 key ImageView(s)
  public void setVisibilityKeyRed3(boolean visibility) {
    for (ImageView key : allStepKeys.get(2)) {
      key.setVisible(visibility);
    }
  }

  // Set visibility of step 4 key ImageView(s)
  public void setVisibilityKeyYellow4(boolean visibility) {
    for (ImageView key : allStepKeys.get(3)) {
      key.setVisible(visibility);
    }
  }
}
