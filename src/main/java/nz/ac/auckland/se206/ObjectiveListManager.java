package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.scene.control.Label;

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
}
