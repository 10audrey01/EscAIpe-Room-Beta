package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The RockBigTaskManager class is responsible for managing the elements and logic related to the
 * "Rock" big task in a game application. It handles colors, notes, sequences, and user interface
 * elements for this task.
 */
public class RockBigTaskManager {

  /** Enum to represent colors of electric guitars. */
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

  private String[] noteSequence; // Array to store the note sequence
  private ArrayList<Label> colourLabels1;
  private ArrayList<Label> colourLabels2;
  private ArrayList<Label> colourLabels3;
  private ArrayList<Label> colourLabels4;
  private ArrayList<Pane> notePanes;
  private ArrayList<ImageView> noteImages;
  private ArrayList<Label> noteSequenceLabels;
  private ArrayList<ImageView> noteArrows;
  private ArrayList<Colour> colours; // List of available colors
  private ArrayList<String> notes; // List of available notes
  private ArrayList<Integer> order; // Random order of colors/numbers
  private HashMap<Colour, Integer> orderColourMap; // Mapping of colors to numbers
  private String noteSequenceString;

  /**
   * Constructs a RockBigTaskManager instance and initializes various lists and sequences needed for
   * the "Rock" big task.
   */
  public RockBigTaskManager() {
    this.noteSequence = new String[4]; // Initialize note sequence with 4 slots
    this.colourLabels1 = new ArrayList<Label>();
    this.colourLabels2 = new ArrayList<Label>();
    this.colourLabels3 = new ArrayList<Label>();
    this.colourLabels4 = new ArrayList<Label>();
    this.notePanes = new ArrayList<Pane>();
    this.noteImages = new ArrayList<ImageView>();
    this.noteSequenceLabels = new ArrayList<Label>();
    this.noteArrows = new ArrayList<ImageView>();
    this.colours =
        new ArrayList<Colour>(List.of(Colour.YELLOW, Colour.BLUE, Colour.PURPLE, Colour.CYAN));
    Collections.shuffle(colours); // Randomize the order of colors
    this.notes = new ArrayList<String>(List.of("C", "D", "E", "F", "G", "A", "B"));
    this.order = new ArrayList<Integer>(List.of(1, 2, 3, 4));
    Collections.shuffle(order); // Randomize the order of numbers
    this.noteSequenceString = "";
    setNoteSequence();
  }

  /**
   * Adds a set of UI elements related to the "Rock" task to their respective lists for coordinated
   * updates.
   *
   * @param label1 The Label representing the first color element.
   * @param label2 The Label representing the second color element.
   * @param label3 The Label representing the third color element.
   * @param label4 The Label representing the fourth color element.
   * @param pane The Pane representing a note element.
   * @param noteImage The ImageView representing a note image.
   * @param noteSequenceLabel The Label representing the note sequence.
   * @param arrow The ImageView representing an arrow image.
   */
  public void addAllRockTaskElements(
      Label label1,
      Label label2,
      Label label3,
      Label label4,
      Pane pane,
      ImageView noteImage,
      Label noteSequenceLabel,
      ImageView arrow) {
    // add all the relevant labels
    addToColourLabels1(label1);
    addToColourLabels2(label2);
    addToColourLabels3(label3);
    addToColourLabels4(label4);
    // add the panes / images / arrow
    addToNotePanes(pane);
    addToNoteImage(noteImage);
    addToNoteSequenceLabels(noteSequenceLabel);
    addToNoteArrows(arrow);
  }

  /**
   * Adds a Label to the list of labels for the color element 1.
   *
   * @param label The Label representing the color element 1 to add.
   */
  public void addToColourLabels1(Label label) {
    colourLabels1.add(label);
  }

  /**
   * Adds a Label to the list of labels for the color element 2.
   *
   * @param label The Label representing the color element 2 to add.
   */
  public void addToColourLabels2(Label label) {
    colourLabels2.add(label);
  }

  /**
   * Adds a Label to the list of labels for the color element 3.
   *
   * @param label The Label representing the color element 3 to add.
   */
  public void addToColourLabels3(Label label) {
    colourLabels3.add(label);
  }

  /**
   * Adds a Label to the list of labels for the color element 4.
   *
   * @param label The Label representing the color element 4 to add.
   */
  public void addToColourLabels4(Label label) {
    colourLabels4.add(label);
  }

  /**
   * Adds a Pane representing a note element to the list of note panes.
   *
   * @param pane The Pane to add to the list of note panes.
   */
  public void addToNotePanes(Pane pane) {
    notePanes.add(pane);
  }

  /**
   * Adds an ImageView representing a note image to the list of note images.
   *
   * @param note The ImageView to add to the list of note images.
   */
  public void addToNoteImage(ImageView note) {
    noteImages.add(note);
  }

  /**
   * Adds a Label representing a note sequence to the list of note sequence labels.
   *
   * @param label The Label to add to the list of note sequence labels.
   */
  public void addToNoteSequenceLabels(Label label) {
    noteSequenceLabels.add(label);
  }

  /**
   * Adds an ImageView representing an arrow image to the list of note arrows.
   *
   * @param arrow The ImageView to add to the list of arrows.
   */
  public void addToNoteArrows(ImageView arrow) {
    noteArrows.add(arrow);
  }

  /**
   * Sets the visibility and style of note panes based on the specified visibility.
   *
   * @param visibility The visibility flag to set for note panes.
   */
  public void setVisibilityNotePanes(boolean visibility) {
    for (Pane pane : notePanes) {
      // Apply a specific style to note panes and set their visibility.
      // The style includes background color and border.
      pane.setStyle(
          "-fx-background-color: #FFF5D8; -fx-border-color:"
              + " #000000;"
              + " -fx-border-width:"
              + " 1px"); // Style for note panes
      pane.setVisible(visibility);
    }
  }

  /**
   * Sets the visibility of note images based on the specified visibility.
   *
   * @param visibility The visibility flag to set for note images.
   */
  public void setVisibilityNoteImages(boolean visibility) {
    for (ImageView note : noteImages) {
      note.setVisible(visibility);
    }
  }

  /**
   * Sets the visibility of arrow images based on the specified visibility.
   *
   * @param visibility The visibility flag to set for arrow images.
   */
  public void setVisibilityNoteArrows(boolean visibility) {
    for (ImageView arrow : noteArrows) {
      arrow.setVisible(visibility);
    }
  }

  /**
   * Sets the style of labels based on colors for all the color groups. This method calls the
   * 'labelStyle' method for each color group.
   */
  public void setLabelColours() {
    labelStyle(colourLabels1, 0);
    labelStyle(colourLabels2, 1);
    labelStyle(colourLabels3, 2);
    labelStyle(colourLabels4, 3);
  }

  /**
   * Styles a list of labels based on the specified color index.
   *
   * @param labelList The list of labels to style.
   * @param colourIndex The index of the color to apply to the labels.
   */
  public void labelStyle(ArrayList<Label> labelList, int colourIndex) {
    for (Label label : labelList) {
      // Set background color, border color, and a random number as label text.
      label.setStyle(
          "-fx-background-color: transparent"
              + "; -fx-border-color:"
              + colours.get(colourIndex).getHex()
              + "; -fx-border-width: 5px");
      label.setText(Integer.toString(order.get(colourIndex))); // Set a random number
    }
  }

  /**
   * Generates a random note sequence of 4 notes and assigns it to the 'noteSequence' array. Each
   * note is randomly selected from the available notes.
   */
  public void setNoteSequence() {
    for (int i = 0; i < 4; i++) {
      noteSequence[i] = notes.get((int) (Math.random() * notes.size()));
    }
    System.out.println(
        "Note sequence: " + noteSequence[0] + noteSequence[1] + noteSequence[2] + noteSequence[3]);
  }

  /**
   * Retrieves the current note sequence as an array of strings.
   *
   * @return An array of strings representing the note sequence.
   */
  public String[] getNoteSequence() {
    return noteSequence;
  }

  /**
   * Creates and sets a mapping of colors to numbers. The mapping is constructed based on the order
   * of colors and numbers specified in the 'colours' and 'order' lists.
   */
  public void setOrderColourMap() {
    orderColourMap = new HashMap<Colour, Integer>();
    orderColourMap.put(colours.get(0), order.get(0));
    orderColourMap.put(colours.get(1), order.get(1));
    orderColourMap.put(colours.get(2), order.get(2));
    orderColourMap.put(colours.get(3), order.get(3));
  }

  /**
   * Retrieves the mapping of colors to numbers.
   *
   * @return A HashMap containing the mapping of colors to numbers.
   */
  public HashMap<Colour, Integer> getOrderColourMap() {
    return orderColourMap;
  }

  /**
   * Updates the note sequence labels by concatenating the provided note sequence string and setting
   * it as the text for all associated labels.
   *
   * @param noteSequence The string representing the note sequence to be appended.
   */
  public void setNoteSequenceLabels(String noteSequence) {
    noteSequenceString = noteSequenceString.concat(noteSequence);
    for (Label label : noteSequenceLabels) {
      label.setText(noteSequenceString);
    }
  }

  /**
   * Clears the note sequence labels by resetting the note sequence string and setting empty text
   * for all associated labels.
   */
  public void clearNoteSequenceLabels() {
    noteSequenceString = "";
    for (Label label : noteSequenceLabels) {
      label.setText("");
    }
  }
}
