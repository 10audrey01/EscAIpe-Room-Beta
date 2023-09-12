package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.control.Label;

public class ColourManager {

  private String[] noteSequence;
  private ArrayList<Label> colourLabels1;
  private ArrayList<Label> colourLabels2;
  private ArrayList<Label> colourLabels3;
  private ArrayList<Label> colourLabels4;
  private ArrayList<String> colours;
  private ArrayList<String> notes;
  private ArrayList<Integer> order;

  ColourManager() {
    this.noteSequence = new String[4];
    this.colourLabels1 = new ArrayList<Label>();
    this.colourLabels2 = new ArrayList<Label>();
    this.colourLabels3 = new ArrayList<Label>();
    this.colourLabels4 = new ArrayList<Label>();
    this.colours = new ArrayList<String>();
    colours.add("#EADE3B");
    colours.add("#2E2EB6");
    colours.add("#7D2DB6");
    colours.add("#18E5CD");
    Collections.shuffle(colours);
    this.notes = new ArrayList<String>();
    notes.add("C");
    notes.add("D");
    notes.add("E");
    notes.add("F");
    notes.add("G");
    notes.add("A");
    notes.add("B");
    setNoteSequence();
    this.order = new ArrayList<Integer>();
    order.add(1);
    order.add(2);
    order.add(3);
    order.add(4);
    Collections.shuffle(order);
  }

  public void addToColourLabels1(Label label) {
    colourLabels1.add(label);
  }

  public void addToColourLabels2(Label label) {
    colourLabels2.add(label);
  }

  public void addToColourLabels3(Label label) {
    colourLabels3.add(label);
  }

  public void addToColourLabels4(Label label) {
    colourLabels4.add(label);
  }

  public void setLabelColours() {
    for (Label label : colourLabels1) {
      label.setStyle(
          "-fx-background-color: #ffff"
              + "; -fx-border-color:"
              + colours.get(0)
              + "; -fx-border-width: 7px");
      label.setText(Integer.toString(order.get(0)));
    }

    for (Label label : colourLabels2) {
      label.setStyle(
          "-fx-background-color: #ffff"
              + "; -fx-border-color:"
              + colours.get(1)
              + "; -fx-border-width: 7px");
      label.setText(Integer.toString(order.get(1)));
    }

    for (Label label : colourLabels3) {
      label.setStyle(
          "-fx-background-color: #ffff"
              + "; -fx-border-color:"
              + colours.get(2)
              + "; -fx-border-width: 7px");
      label.setText(Integer.toString(order.get(2)));
    }

    for (Label label : colourLabels4) {
      label.setStyle(
          "-fx-background-color: #ffff"
              + "; -fx-border-color:"
              + colours.get(3)
              + "; -fx-border-width: 7px");
      label.setText(Integer.toString(order.get(3)));
    }
  }

  public void setNoteSequence() {
    for (int i = 0; i < 4; i++) {
      noteSequence[i] = notes.get((int) (Math.random() * notes.size()));
    }
    System.out.println(
        "Note sequence: " + noteSequence[0] + noteSequence[1] + noteSequence[2] + noteSequence[3]);
  }
}
