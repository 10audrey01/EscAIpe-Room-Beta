package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * A class responsible for managing objective labels and step keys for different game steps. It
 * provides methods to add and manipulate objective labels and step keys, mark objectives as
 * completed, apply strikethrough effect to labels, and set the visibility of step keys.
 */
public class ObjectiveListManager {

  private ArrayList<ArrayList<Label>> allObjectiveLabels; // Lists of objective labels for each step
  private ArrayList<ArrayList<ImageView>> allStepKeys; // Lists of step keys for each step

  /**
   * Initializes a new instance of the ObjectiveListManager class. It initializes lists for
   * objective labels and step keys.
   */
  public ObjectiveListManager() {
    this.allObjectiveLabels =
        new ArrayList<ArrayList<Label>>(
            List.of(
                new ArrayList<Label>(),
                new ArrayList<Label>(),
                new ArrayList<Label>(),
                new ArrayList<Label>()));
    this.allStepKeys =
        new ArrayList<ArrayList<ImageView>>(
            List.of(
                new ArrayList<ImageView>(),
                new ArrayList<ImageView>(),
                new ArrayList<ImageView>(),
                new ArrayList<ImageView>()));
  }

  /**
   * Adds an objective label for a specific step.
   *
   * @param labelIndex The index of the step for which the objective label is added.
   * @param objectiveLabel The objective label to add.
   */
  public void addObjectiveLabel(int labelIndex, Label objectiveLabel) {
    this.allObjectiveLabels.get(labelIndex).add(objectiveLabel);
  }

  /**
   * Adds a list of objective labels for all steps.
   *
   * @param objectiveLabels A list of objective labels for all steps.
   */
  public void addObjectiveLabels(ArrayList<Label> objectiveLabels) {
    this.addObjectiveLabel(0, objectiveLabels.get(0));
    this.addObjectiveLabel(1, objectiveLabels.get(1));
    this.addObjectiveLabel(2, objectiveLabels.get(2));
    this.addObjectiveLabel(3, objectiveLabels.get(3));
  }

  /**
   * Changing text of an objective label for every objective label in a specific step.
   *
   * @param labelIndex The index of the step for which the objective label is changed.
   * @param text The text to change the objective label to.
   */
  public void changeObjectiveLabelText(int labelIndex, String text) {
    for (Label label : this.allObjectiveLabels.get(labelIndex)) {
      label.setText(text);
    }
  }

  /**
   * Adds an ImageView representing a key for a specific step.
   *
   * @param keyIndex The index of the step for which the key ImageView is added.
   * @param stepKeys The key ImageView to add.
   */
  public void addStepKey(int keyIndex, ImageView stepKeys) {
    this.allStepKeys.get(keyIndex).add(stepKeys);
  }

  /**
   * Adds a list of ImageView representing keys for all steps.
   *
   * @param stepKeys A list of ImageView representing keys for all steps.
   */
  public void addStepKeys(ArrayList<ImageView> stepKeys) {
    this.addStepKey(0, stepKeys.get(0));
    this.addStepKey(1, stepKeys.get(1));
    this.addStepKey(2, stepKeys.get(2));
    this.addStepKey(3, stepKeys.get(3));
  }

  /** Marks objective 1 as completed if the music quiz is completed. */
  public void completeObjective1() {
    if (GameState.isMusicQuizCompleted) {
      strikeThroughLabels(this.allObjectiveLabels.get(0)); // Apply strikethrough effect to labels
      setVisibilityKey(0, true); // Set visibility of step 1 key to true
    }
  }

  /** Marks objective 2 as completed if the safe is opened. */
  public void completeObjective2() {
    if (GameState.isSafeOpened) {
      strikeThroughLabels(this.allObjectiveLabels.get(1)); // Apply strikethrough effect to labels
      setVisibilityKey(1, true); // Set visibility of step 2 key to true
    }
  }

  /** Marks objective 3 as completed if the riddle is resolved. */
  public void completeObjective3() {
    if (GameState.isRiddleResolved) {
      // set the text of objective 3 to "Find the riddle object" if the riddle is resolved
      changeObjectiveLabelText(2, "- Find the riddle object");
    }
    if (GameState.isRiddleObjectFound) {
      // set the text of objective 3 to "Find the note sequence" if the riddle object is found
      changeObjectiveLabelText(2, "- Find the note sequence");
    }
    if (GameState.isNoteSequenceFound) {
      // set the text of objective 3 to "Play the piano" if the note sequence is found
      changeObjectiveLabelText(2, "- Play the piano");
    }
    if (GameState.isPianoPlayed) {
      strikeThroughLabels(this.allObjectiveLabels.get(2)); // Apply strikethrough effect to labels
    }
  }

  /** Marks objective 4 as completed if the harp is played. */
  public void completeObjective4() {
    if (GameState.isHarpPlayed) {
      strikeThroughLabels(this.allObjectiveLabels.get(3)); // Apply strikethrough effect to labels
      setVisibilityKey(3, true); // Set visibility of step 4 key to true
    }
  }

  /**
   * Applies a strikethrough effect to a list of labels.
   *
   * @param labels The list of labels to which the strikethrough effect is applied.
   */
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

  /**
   * Sets the visibility of key ImageView(s) for a specific step.
   *
   * @param keyIndex The index of the step for which the key ImageView(s) visibility is set.
   * @param visibility The visibility value to set (true for visible, false for hidden).
   */
  public void setVisibilityKey(int keyIndex, boolean visibility) {
    for (ImageView key : allStepKeys.get(keyIndex)) {
      key.setVisible(visibility);
    }
  }
}
