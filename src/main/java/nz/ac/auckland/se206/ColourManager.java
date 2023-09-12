package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.layout.Pane;

public class ColourManager {

  private ArrayList<Pane> colourPanes1;
  private ArrayList<Pane> colourPanes2;
  private ArrayList<Pane> colourPanes3;
  private ArrayList<Pane> colourPanes4;
  private ArrayList<String> colours;

  ColourManager() {
    this.colourPanes1 = new ArrayList<Pane>();
    this.colourPanes2 = new ArrayList<Pane>();
    this.colourPanes3 = new ArrayList<Pane>();
    this.colourPanes4 = new ArrayList<Pane>();
    this.colours = new ArrayList<String>();
    colours.add("#EADE3B");
    colours.add("#2E2EB6");
    colours.add("#7D2DB6");
    colours.add("#18E5CD");
    Collections.shuffle(colours);
  }

  public void addToColourPanes1(Pane pane) {
    colourPanes1.add(pane);
  }

  public void addToColourPanes2(Pane pane) {
    colourPanes2.add(pane);
  }

  public void addToColourPanes3(Pane pane) {
    colourPanes3.add(pane);
  }

  public void addToColourPanes4(Pane pane) {
    colourPanes4.add(pane);
  }

  public void setPaneColours() {
    for (Pane pane : colourPanes1) {
      pane.setStyle("-fx-background-color: " + colours.get(0));
    }

    for (Pane pane : colourPanes2) {
      pane.setStyle("-fx-background-color: " + colours.get(1));
    }

    for (Pane pane : colourPanes3) {
      pane.setStyle("-fx-background-color: " + colours.get(2));
    }

    for (Pane pane : colourPanes4) {
      pane.setStyle("-fx-background-color: " + colours.get(3));
    }
  }
}
