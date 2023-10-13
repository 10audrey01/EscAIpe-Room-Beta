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
  private GameState gameState;

  /**
   * Initializes a new instance of the ObjectiveListManager class. It initializes lists for
   * objective labels and step keys.
   */
  public ObjectiveListManager() {
    gameState = GameState.getInstance();
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

  /** Initialise the objective list by setting the text of each objective label to the objective. */
  public void initialiseObjectiveList() {
    changeObjectiveLabelText(0, gameState.getTaskManager().getTask(0).getTaskDescription());
    changeObjectiveLabelText(1, gameState.getTaskManager().getTask(1).getTaskDescription());
    changeObjectiveLabelText(2, gameState.getTaskManager().getTask(2).getTaskDescription());
    changeObjectiveLabelText(3, gameState.getTaskManager().getTask(3).getTaskDescription());
  }

  /** Marks an objective completed if corresponding task is completed */
  public void completeObjective(int taskIndex) {
    // check if task has next task
    if (gameState.getTaskManager().getTask(taskIndex).hasMoreTasks()) {
      // if it does, set the next task as the current task
      gameState
          .getTaskManager()
          .updateTask(taskIndex, gameState.getTaskManager().getTask(taskIndex).getNextTask());
      // update the objective label to the next task
      changeObjectiveLabelText(
          taskIndex, gameState.getTaskManager().getTask(taskIndex).getTaskDescription());
    } else {
      // mark the objective as completed
      gameState.getTaskManager().getTask(taskIndex).setCompleted(true);
      // apply strikethrough effect to the objective label
      this.strikeThroughLabels(this.allObjectiveLabels.get(taskIndex));
      // make the key visible
      this.setVisibilityKey(taskIndex, true);
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
