package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class HarpController {
  @FXML
  private Line string1,
      string2,
      string3,
      string4,
      string5,
      string6,
      string7,
      string8,
      string9,
      string10,
      string11,
      string12,
      string13,
      string14,
      string15,
      string16,
      string17,
      string18,
      string19,
      string20,
      string21,
      string22,
      string23,
      string24,
      string25,
      string26,
      string27,
      string28,
      string29,
      string30,
      string31,
      string32,
      string33,
      string34;
  @FXML private Circle circle1, circle2, circle3, circle4, circle5;
  @FXML private Pane leaveHarp;

  private ArrayList<Line> strings;
  private ArrayList<Line> notesToPlay;
  private ArrayList<Paint> noteColours;
  private ArrayList<Boolean> notesToShow;
  private ArrayList<Circle> circles;
  private Line noteToPlay;
  private boolean harpSequencePlayable;
  private Paint noteOrginalColor;

  @FXML
  private void initialize() throws IOException {

    strings =
        new ArrayList<Line>(
            List.of(
                string1, string2, string3, string4, string5, string6, string7, string8, string9,
                string10, string11, string12, string13, string14, string15, string16, string17,
                string18, string19, string20, string21, string22, string23, string24, string25,
                string26, string27, string28, string29, string30, string31, string32, string33,
                string34));
    harpSequencePlayable = false;
    notesToPlay = new ArrayList<Line>();
    notesToShow = new ArrayList<Boolean>(List.of(false, false, false, false, false));
    circles = new ArrayList<Circle>(List.of(circle1, circle2, circle3, circle4, circle5));
    setStrings();
    generateRandomHarpNotes();
    generateRandomColours();
    setNoteColours();
    setCircleColours();
    noteToPlay = notesToPlay.get(0);
  }

  @FXML
  private void generateRandomHarpNotes() {
    for (int i = 0; i < 5; i++) {
      int randomNum = (int) (Math.random() * 34);
      Line randomNote = strings.get(randomNum);
      while (notesToPlay.contains(randomNote)) {
        randomNum = (int) (Math.random() * 34);
        randomNote = strings.get(randomNum);
      }
      notesToPlay.add(randomNote);
      System.out.println(randomNote.getId() + " added");
    }
  }

  private void generateRandomColours() {
    noteColours = new ArrayList<Paint>();
    for (int i = 0; i < notesToPlay.size(); i++) {
      int randomNum = (int) (Math.random() * 20);
      Paint randomColour = getColour(randomNum);
      while (noteColours.contains(randomColour)) {
        randomNum = (int) (Math.random() * 20);
        randomColour = getColour(randomNum);
      }
      noteColours.add(randomColour);
    }
  }

  private void setNoteColours() {
    for (int i = 0; i < notesToPlay.size(); i++) {
      if (notesToShow.get(i)) {
        notesToPlay.get(i).setStroke(noteColours.get(i));
      }
    }
  }

  private Paint getColour(int randomNum) {
    switch (randomNum) {
      case 0:
        return javafx.scene.paint.Color.RED;

      case 1:
        return javafx.scene.paint.Color.BLUE;

      case 2:
        return javafx.scene.paint.Color.GREEN;

      case 3:
        return javafx.scene.paint.Color.YELLOW;

      case 4:
        return javafx.scene.paint.Color.ORANGE;

      case 5:
        return javafx.scene.paint.Color.PURPLE;

      case 6:
        return javafx.scene.paint.Color.PINK;

      case 7:
        return javafx.scene.paint.Color.CYAN;

      case 8:
        return javafx.scene.paint.Color.BROWN;

      case 9:
        return javafx.scene.paint.Color.GOLD;

      case 10:
        return javafx.scene.paint.Color.DARKBLUE;

      case 11:
        return javafx.scene.paint.Color.DARKGREEN;

      case 12:
        return javafx.scene.paint.Color.DARKRED;

      case 13:
        return javafx.scene.paint.Color.DARKVIOLET;

      case 14:
        return javafx.scene.paint.Color.DARKORANGE;

      case 15:
        return javafx.scene.paint.Color.ALICEBLUE;

      case 16:
        return javafx.scene.paint.Color.MAGENTA;

      case 17:
        return javafx.scene.paint.Color.SALMON;

      case 18:
        return javafx.scene.paint.Color.SLATEBLUE;

      case 19:
        return javafx.scene.paint.Color.AQUAMARINE;

      case 20:
        return javafx.scene.paint.Color.TURQUOISE;
      default:
        return null;
    }
  }

  private void setCircleColours() {
    for (int i = 0; i < notesToPlay.size(); i++) {
      if (notesToShow.get(i)) {
        circles.get(i).setFill(noteColours.get(i));
      } else {
        Color paint = new Color(0.1216, 0.5765, 1.0, 0.0);
        circles.get(i).setFill(paint);
      }
    }
  }

  @FXML
  private void setStrings() {
    for (Line string : strings) {
      string.setOnMouseEntered(
          e -> {
            noteOrginalColor = string.getStroke();
            string.setStroke(javafx.scene.paint.Color.GRAY);
            System.out.println(string.getId() + " entered");
          });
      string.setOnMouseExited(
          e -> {
            string.setStroke(noteOrginalColor);
            System.out.println(string.getId() + " left");
          });
      string.setOnMouseClicked(
          e -> {
            System.out.println(string.getId() + " clicked");
            if (!harpSequencePlayable) {
              return;
            }
            if (noteToPlay == string) {
              System.out.println("Correct note played");
              noteOrginalColor = javafx.scene.paint.Color.BLACK;
              correctNotePlayed();
            } else {
              System.out.println("Incorrect note played");
              incorrectNotePlayed();
            }
          });
      string.setCursor(javafx.scene.Cursor.CLOSED_HAND);
    }
  }

  public void correctNotePlayed() {
    notesToShow.set(notesToPlay.indexOf(noteToPlay), false);
    if (notesToPlay.indexOf(noteToPlay) != 4) {
      noteToPlay = notesToPlay.get(notesToPlay.indexOf(noteToPlay) + 1);
    } else {
      System.out.println("Harp completed");
      harpSequencePlayable = false;
      GameState.isHarpPlayed = true;
    }
    setCircleColours();
  }

  public void incorrectNotePlayed() {
    noteToPlay = notesToPlay.get(0);
    notesToShow = new ArrayList<Boolean>(List.of(true, true, true, true, true));
    setNoteColours();
    setCircleColours();
  }

  public void setCirclesFound(int index) {
    notesToShow.set(index, true);
    setNoteColours();
    setCircleColours();
    if (!harpSequencePlayable) {
      for (boolean note : notesToShow) {
        if (note == false) {
          return;
        }
      }
      harpSequencePlayable = true;
      System.out.println("Harp sequence playable");
    }
  }

  public Paint getColourIndex(int index) {
    return noteColours.get(index);
  }

  @FXML
  public void onClickedLeaveHarp(MouseEvent event) throws IOException {
    System.out.println("Leave Harp Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
