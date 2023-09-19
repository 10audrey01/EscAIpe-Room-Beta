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
    this.objectiveLabel1 = new ArrayList<Label>();
    this.objectiveLabel2 = new ArrayList<Label>();
    this.objectiveLabel3 = new ArrayList<Label>();
    this.objectiveLabel4 = new ArrayList<Label>();
    this.step1Key = new ArrayList<ImageView>();
    this.step2Key = new ArrayList<ImageView>();
    this.step3Key = new ArrayList<ImageView>();
    this.step4Key = new ArrayList<ImageView>();
  }

  public void addObjectiveLabel1(Label objectiveLabel) {
    this.objectiveLabel1.add(objectiveLabel);
  }

  public void addObjectiveLabel2(Label objectiveLabel) {
    this.objectiveLabel2.add(objectiveLabel);
  }

  public void addObjectiveLabel3(Label objectiveLabel) {
    this.objectiveLabel3.add(objectiveLabel);
  }

  public void addObjectiveLabel4(Label objectiveLabel) {
    this.objectiveLabel4.add(objectiveLabel);
  }

  public void addStep1Key(ImageView step1Key) {
    this.step1Key.add(step1Key);
  }

  public void addStep2Key(ImageView step2Key) {
    this.step2Key.add(step2Key);
  }

  public void addStep3Key(ImageView step3Key) {
    this.step3Key.add(step3Key);
  }

  public void addStep4Key(ImageView step4Key) {
    this.step4Key.add(step4Key);
  }

  public void completeObjective1() {
    if (GameState.isMusicQuizCompleted) {
      strikeThroughLabels(this.objectiveLabel1);
      setVisibilityKeyBlue1(true);
    }
  }

  public void completeObjective2() {
    if (GameState.isSafeOpened) {
      strikeThroughLabels(this.objectiveLabel2);
      setVisibilityKeyGreen2(true);
    }
  }

  public void completeObjective3() {
    if (GameState.isRiddleResolved) {
      strikeThroughLabels(this.objectiveLabel3);
    }
  }

  public void completeObjective4() {
    if (GameState.isHarpPlayed) {
      strikeThroughLabels(this.objectiveLabel4);
      setVisibilityKeyYellow4(true);
    }
  }

  public void strikeThroughLabels(ArrayList<Label> labels) {
    for (Label label : labels) {
      Text text = new Text(label.getText());
      text.setStyle(
          "-fx-stroke: black; -fx-stroke-width: 0px; -fx-font-size: 16px; -fx-strikethrough: true;"
              + " -fx-fill: black;");
      label.setText("");
      label.setGraphic(text);
    }
  }

  public void setVisibilityKeyBlue1(boolean visibility) {
    for (ImageView key : step1Key) {
      key.setVisible(visibility);
    }
  }

  public void setVisibilityKeyGreen2(boolean visibility) {
    for (ImageView key : step2Key) {
      key.setVisible(visibility);
    }
  }

  public void setVisibilityKeyRed3(boolean visibility) {
    for (ImageView key : step3Key) {
      key.setVisible(visibility);
    }
  }

  public void setVisibilityKeyYellow4(boolean visibility) {
    for (ImageView key : step4Key) {
      key.setVisible(visibility);
    }
  }
}
