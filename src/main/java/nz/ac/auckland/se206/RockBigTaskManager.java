package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.scene.control.Label;

public class RockBigTaskManager {
  public enum Note {
    C,
    D,
    E,
    F,
    G,
    A,
    B;
  }

  public enum Colour {
    YELLOW("#EADE3B"),
    BLUE("#2E2EB6"),
    PURPLE("#7D2DB6"),
    CYAN("#18E5CD");

    private final String hex;

    Colour(String hex) {
      this.hex = hex;
    }

    public String getHex() {
      return hex;
    }
  }

  private Note[] noteSequence;
  private ArrayList<Label> colourLabels1;
  private ArrayList<Label> colourLabels2;
  private ArrayList<Label> colourLabels3;
  private ArrayList<Label> colourLabels4;
  private ArrayList<Colour> colours;
  private ArrayList<Note> notes;
  private ArrayList<Integer> order;
  private HashMap<Colour, Integer> orderColourMap;

  RockBigTaskManager() {
    this.noteSequence = new Note[4];
    this.colourLabels1 = new ArrayList<Label>();
    this.colourLabels2 = new ArrayList<Label>();
    this.colourLabels3 = new ArrayList<Label>();
    this.colourLabels4 = new ArrayList<Label>();
    this.colours = new ArrayList<Colour>();
    colours.add(Colour.YELLOW);
    colours.add(Colour.BLUE);
    colours.add(Colour.PURPLE);
    colours.add(Colour.CYAN);
    Collections.shuffle(colours);
    this.notes = new ArrayList<Note>();
    notes.add(Note.C);
    notes.add(Note.D);
    notes.add(Note.E);
    notes.add(Note.F);
    notes.add(Note.G);
    notes.add(Note.A);
    notes.add(Note.B);
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
              + colours.get(0).getHex()
              + "; -fx-border-width: 7px");
      label.setText(Integer.toString(order.get(0)));
    }

    for (Label label : colourLabels2) {
      label.setStyle(
          "-fx-background-color: #ffff"
              + "; -fx-border-color:"
              + colours.get(1).getHex()
              + "; -fx-border-width: 7px");
      label.setText(Integer.toString(order.get(1)));
    }

    for (Label label : colourLabels3) {
      label.setStyle(
          "-fx-background-color: #ffff"
              + "; -fx-border-color:"
              + colours.get(2).getHex()
              + "; -fx-border-width: 7px");
      label.setText(Integer.toString(order.get(2)));
    }

    for (Label label : colourLabels4) {
      label.setStyle(
          "-fx-background-color: #ffff"
              + "; -fx-border-color:"
              + colours.get(3).getHex()
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

  public Note[] getNoteSequence() {
    return noteSequence;
  }

  public void setOrderColourMap() {
    orderColourMap = new HashMap<Colour, Integer>();
    orderColourMap.put(colours.get(0), order.get(0));
    orderColourMap.put(colours.get(1), order.get(1));
    orderColourMap.put(colours.get(2), order.get(2));
    orderColourMap.put(colours.get(3), order.get(3));
  }

  public HashMap<Colour, Integer> getOrderColourMap() {
    return orderColourMap;
  }
}
