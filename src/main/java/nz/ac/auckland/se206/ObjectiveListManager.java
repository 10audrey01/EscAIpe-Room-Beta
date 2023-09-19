package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ObjectiveListManager {

  private ArrayList<Label> objectiveLabel1;
  private ArrayList<Label> objectiveLabel2;
  private ArrayList<Label> objectiveLabel3;
  private ArrayList<Label> objectiveLabel4;

  public ObjectiveListManager() {
    this.objectiveLabel1 = new ArrayList<Label>();
    this.objectiveLabel2 = new ArrayList<Label>();
    this.objectiveLabel3 = new ArrayList<Label>();
    this.objectiveLabel4 = new ArrayList<Label>();
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

  public void strikeThroughLabel1() {
    if (GameState.isMusicQuizCompleted) {
      strikeThroughLabels(this.objectiveLabel1);
    }
  }

  public void strikeThroughLabel2() {
    if (GameState.isSafeOpened) {
      strikeThroughLabels(this.objectiveLabel2);
    }
  }

  public void strikeThroughLabel3() {
    if (GameState.isRiddleResolved) {
      strikeThroughLabels(this.objectiveLabel3);
    }
  }

  public void strikeThroughLabel4() {
    if (GameState.isHarpPlayed) {
      strikeThroughLabels(this.objectiveLabel4);
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
}
