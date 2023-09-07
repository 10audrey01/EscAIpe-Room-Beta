package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.scene.control.Label;

public class TimeManager {
  private ArrayList<Label> timers;

  public TimeManager() {
    this.timers = new ArrayList<Label>();
  }

  public void addToTimers(Label label) {
    timers.add(label);
  }
}
