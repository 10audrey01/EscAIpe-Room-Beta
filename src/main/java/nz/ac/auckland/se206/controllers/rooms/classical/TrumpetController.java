package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class TrumpetController {

  @FXML private ImageView trumpetButton1, trumpetButton2, trumpetButton3;
  @FXML private ImageView note1Symbol, note2Symbol, note3Symbol;
  @FXML private ImageView beamNote1, beamNote2, beamNote3, beamNote4, beamNote5;
  @FXML private Pane leaveTrumpet, playTrumpet;
  @FXML private Label timerLabel;
  private boolean isButton1Down;
  private boolean isButton2Down;
  private boolean isButton3Down;

  private ArrayList<ImageView> beamNotes;
  private ArrayList<Integer> noteSequence;
  private Integer noteToPlay;
  private int currentNoteIndex;

  private GameState gameState;

  @FXML
  private void initialize() throws IOException {
    gameState = GameState.getInstance();
    gameState.getTimeManager().addToTimers(timerLabel);
    isButton1Down = false;
    isButton2Down = false;
    isButton3Down = false;
    beamNotes =
        new ArrayList<ImageView>(List.of(beamNote1, beamNote2, beamNote3, beamNote4, beamNote5));

    generateNoteSequence();
    noteToPlay = noteSequence.get(0);
    currentNoteIndex = 0;
    setSymbols();
    setBeamNotes();
  }

  private void generateNoteSequence() {
    int numberToAdd;
    noteSequence = new ArrayList<Integer>();
    for (int i = 0; i < 5; i++) {
      numberToAdd =
          (int) (Math.random() * 2)
              + 10 * (int) (Math.random() * 2)
              + 100 * (int) (Math.random() * 2);
      while (noteSequence.contains(numberToAdd)) {
        numberToAdd =
            (int) (Math.random() * 2)
                + 10 * (int) (Math.random() * 2)
                + 100 * (int) (Math.random() * 2);
      }
      noteSequence.add(numberToAdd);
    }
  }

  private void setSymbols() throws IOException {
    Image onNoteImage =
        new Image(App.class.getResource("/images/classicalRoom/Note.png").openStream());
    Image offNoteImage =
        new Image(App.class.getResource("/images/classicalRoom/RestNote.png").openStream());
    if (noteToPlay / 100 == 1) {
      Platform.runLater(
          () -> {
            note1Symbol.setImage(onNoteImage);
          });
    } else {
      Platform.runLater(
          () -> {
            note1Symbol.setImage(offNoteImage);
          });
    }
    if (noteToPlay / 10 % 10 == 1) {
      Platform.runLater(
          () -> {
            note2Symbol.setImage(onNoteImage);
          });
    } else {
      Platform.runLater(
          () -> {
            note2Symbol.setImage(offNoteImage);
          });
    }
    if (noteToPlay % 10 == 1) {
      Platform.runLater(
          () -> {
            note3Symbol.setImage(onNoteImage);
          });
    } else {
      Platform.runLater(
          () -> {
            note3Symbol.setImage(offNoteImage);
          });
    }
  }

  @FXML
  private void onClickedTrumpetButton1(MouseEvent event) throws IOException {
    if (!isButton1Down) {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton1Down.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton1Down = true;
    } else {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton1Up.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton1Down = false;
    }
  }

  @FXML
  private void onClickedTrumpetButton2(MouseEvent event) throws IOException {
    if (!isButton2Down) {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton2Down.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton2Down = true;
    } else {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton2Up.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton2Down = false;
    }
  }

  @FXML
  private void onClickedTrumpetButton3(MouseEvent event) throws IOException {
    if (!isButton3Down) {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton3Down.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton3Down = true;
    } else {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton3Up.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton3Down = false;
    }
  }

  private boolean checkNote1() {
    if (noteToPlay / 100 == 1) {
      if (isButton1Down) {
        return true;
      }
    } else {
      if (!isButton1Down) {
        return true;
      }
    }
    return false;
  }

  private boolean checkNote2() {
    if (noteToPlay / 10 % 10 == 1) {
      if (isButton2Down) {
        return true;
      }
    } else {
      if (!isButton2Down) {
        return true;
      }
    }
    return false;
  }

  private boolean checkNote3() {
    if (noteToPlay % 10 == 1) {
      if (isButton3Down) {
        return true;
      }
    } else {
      if (!isButton3Down) {
        return true;
      }
    }
    return false;
  }

  @FXML
  public void onClickedPlayTrumpet(MouseEvent event) {
    System.out.println("Play Trumpet Clicked");
    if (checkNote1() && checkNote2() && checkNote3()) {
      System.out.println("Correct note played");
      correctNotePlayed();
    } else {
      System.out.println("Incorrect note played");
      incorrectNotePlayed();
    }
  }

  public void correctNotePlayed() {
    currentNoteIndex++;
    if (currentNoteIndex <= 4) {
      noteToPlay = noteSequence.get(currentNoteIndex);
    } else {
      System.out.println("Trumpet completed");
      GameState.isTrumpetPlayed = true;
      hideAllSymbols();
    }
    try {
      setSymbols();
      setBeamNotes();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void incorrectNotePlayed() {
    System.out.println(noteToPlay);
  }

  private void setBeamNotes() {
    for (int i = 0; i < beamNotes.size(); i++) {
      if (currentNoteIndex > i) {
        beamNotes.get(i).setOpacity(1);
        System.out.println("Show");
      } else {
        beamNotes.get(i).setOpacity(0);
        System.out.println("Hide");
      }
    }
  }

  private void hideAllSymbols() {
    Platform.runLater(
        () -> {
          note1Symbol.setOpacity(0);
          note2Symbol.setOpacity(0);
          note3Symbol.setOpacity(0);
        });
  }

  @FXML
  public void onClickedLeaveTrumpet(MouseEvent event) throws IOException {
    System.out.println("Leave Trumpet Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
