package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RockBigTaskManager {

  public enum Colour { // Enum for colors of electric guitars
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

  private String[] noteSequence; // Array to store the note sequence
  private ArrayList<Label> colourLabels1;
  private ArrayList<Label> colourLabels2;
  private ArrayList<Label> colourLabels3;
  private ArrayList<Label> colourLabels4;
  private ArrayList<Pane> notePanes;
  private ArrayList<ToggleButton> noteButtons;
  private ArrayList<Label> noteSequenceLabels;
  private ArrayList<ImageView> arrows;
  private ArrayList<Colour> colours; // List of available colors
  private ArrayList<String> notes; // List of available notes
  private ArrayList<Integer> order; // Random order of colors/numbers
  private HashMap<Colour, Integer> orderColourMap; // Mapping of colors to numbers
  private String noteSequenceString;

  RockBigTaskManager() {
    this.noteSequence = new String[4]; // Initialize note sequence with 4 slots
    this.colourLabels1 = new ArrayList<Label>();
    this.colourLabels2 = new ArrayList<Label>();
    this.colourLabels3 = new ArrayList<Label>();
    this.colourLabels4 = new ArrayList<Label>();
    this.notePanes = new ArrayList<Pane>();
    this.noteButtons = new ArrayList<ToggleButton>();
    this.noteSequenceLabels = new ArrayList<Label>();
    this.arrows = new ArrayList<ImageView>();
    this.colours = new ArrayList<Colour>();
    colours.add(Colour.YELLOW);
    colours.add(Colour.BLUE);
    colours.add(Colour.PURPLE);
    colours.add(Colour.CYAN);
    Collections.shuffle(colours); // Randomize the order of colors
    this.notes = new ArrayList<String>();
    notes.add("C");
    notes.add("D");
    notes.add("E");
    notes.add("F");
    notes.add("G");
    notes.add("A");
    notes.add("B");
    this.order = new ArrayList<Integer>();
    order.add(1);
    order.add(2);
    order.add(3);
    order.add(4);
    Collections.shuffle(order); // Randomize the order of numbers
    this.noteSequenceString = "";
    setNoteSequence();
  }

  // Methods to add UI elements to the corresponding lists
  public void addAllRockTaskElements(
      Label label1,
      Label label2,
      Label label3,
      Label label4,
      Pane pane,
      ToggleButton button,
      Label noteSequenceLabel,
      ImageView arrow) {
    addToColourLabels1(label1);
    addToColourLabels2(label2);
    addToColourLabels3(label3);
    addToColourLabels4(label4);
    addToNotePanes(pane);
    addToNoteButtons(button);
    addToNoteSequenceLabels(noteSequenceLabel);
    addToArrows(arrow);
  }

  // adds a label to the label for colour 1
  public void addToColourLabels1(Label label) {
    colourLabels1.add(label);
  }

  // adds a label to the label for colour 2
  public void addToColourLabels2(Label label) {
    colourLabels2.add(label);
  }

  // adds a label to the label for colour 3
  public void addToColourLabels3(Label label) {
    colourLabels3.add(label);
  }

  // adds a label to the label for colour 4
  public void addToColourLabels4(Label label) {
    colourLabels4.add(label);
  }

  // adds a pane to the note panes
  public void addToNotePanes(Pane pane) {
    notePanes.add(pane);
  }

  // adds a button to the note buttons
  public void addToNoteButtons(ToggleButton button) {
    noteButtons.add(button);
  }

  // adds a label to the sequence labels
  public void addToNoteSequenceLabels(Label label) {
    noteSequenceLabels.add(label);
  }

  // adds an arrow imageview to the arrows
  public void addToArrows(ImageView arrow) {
    arrows.add(arrow);
  }

  // Methods to set visibility and style of UI elements
  public void setVisibilityNotePanes(boolean visibility) {
    for (Pane pane : notePanes) {
      pane.setStyle(
          "-fx-background-color: #FFF5D8; -fx-border-color:"
              + " #000000;"
              + " -fx-border-width:"
              + " 1px"); // Style for note panes
      pane.setVisible(visibility);
    }
  }

  public void setVisibilityNoteButtons(boolean visibility) {
    for (ToggleButton button : noteButtons) {
      button.setVisible(visibility);
    }
  }

  public void setVisibilityArrows(boolean visibility) {
    for (ImageView arrow : arrows) {
      arrow.setVisible(visibility);
    }
  }

  public void setDisableNoteButtons(boolean disable) {
    for (ToggleButton button : noteButtons) {
      button.setDisable(disable);
    }
  }

  // Methods to style labels based on colors
  public void setLabelColours() {
    labelStyle(colourLabels1, 0);
    labelStyle(colourLabels2, 1);
    labelStyle(colourLabels3, 2);
    labelStyle(colourLabels4, 3);
  }

  public void labelStyle(ArrayList<Label> labelList, int colourIndex) {
    for (Label label : labelList) {
      label.setStyle( // Set background color and border color
          "-fx-background-color: transparent"
              + "; -fx-border-color:"
              + colours.get(colourIndex).getHex()
              + "; -fx-border-width: 5px");
      label.setText(Integer.toString(order.get(colourIndex))); // Set a random number
    }
  }

  // Generate a random note sequence
  public void setNoteSequence() {
    for (int i = 0; i < 4; i++) {
      noteSequence[i] = notes.get((int) (Math.random() * notes.size()));
    }
    System.out.println(
        "Note sequence: " + noteSequence[0] + noteSequence[1] + noteSequence[2] + noteSequence[3]);
  }

  public String[] getNoteSequence() {
    return noteSequence;
  }

  // Create a mapping of colors to numbers
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

  // Update note sequence labels as guitars are clicked
  public void setNoteSequenceLabels(String noteSequence) {
    noteSequenceString = noteSequenceString.concat(noteSequence);
    for (Label label : noteSequenceLabels) {
      label.setText(noteSequenceString);
    }
  }

  // clears the note sequence labels
  public void clearNoteSequenceLabels() {
    noteSequenceString = "";
    for (Label label : noteSequenceLabels) {
      label.setText("");
    }
  }
}
