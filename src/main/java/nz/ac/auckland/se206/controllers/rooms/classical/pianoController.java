package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class PianoController {

  @FXML private ImageView note1;
  @FXML private ImageView note2;
  @FXML private ImageView note3;
  @FXML private ImageView note4;
  @FXML private ImageView note5;
  @FXML private ImageView note6;
  @FXML private ImageView note7;
  @FXML private ImageView note8;
  @FXML private ImageView note9;
  @FXML private ImageView note10;
  @FXML private ImageView note11;
  @FXML private ImageView note12;
  @FXML private ImageView note13;
  @FXML private ImageView note14;
  @FXML private ImageView note1Letter;
  @FXML private ImageView note2Letter;
  @FXML private ImageView note3Letter;
  @FXML private ImageView note4Letter;
  @FXML private ImageView note5Letter;
  @FXML private ImageView note6Letter;
  @FXML private ImageView note7Letter;
  @FXML private ImageView note8Letter;
  @FXML private ImageView note9Letter;
  @FXML private ImageView note10Letter;
  @FXML private ImageView note11Letter;
  @FXML private ImageView note12Letter;
  @FXML private ImageView note13Letter;
  @FXML private ImageView note14Letter;
  @FXML private Pane aKey;
  @FXML private Pane bKey;
  @FXML private Pane cKey;
  @FXML private Pane dKey;
  @FXML private Pane eKey;
  @FXML private Pane fKey;
  @FXML private Pane gKey;
  @FXML private Pane leavePiano;

  public ArrayList<ImageView> notesList;

  public ArrayList<ImageView> notesLetterList;

  @FXML
  private void initialize() {
    notesList =
        new ArrayList<ImageView>(
            List.of(
                note1, note2, note3, note4, note5, note6, note7, note8, note9, note10, note11,
                note12, note13, note14));
    notesLetterList =
        new ArrayList<ImageView>(
            List.of(
                note1Letter,
                note2Letter,
                note3Letter,
                note4Letter,
                note5Letter,
                note6Letter,
                note7Letter,
                note8Letter,
                note9Letter,
                note10Letter,
                note11Letter,
                note12Letter,
                note13Letter,
                note14Letter));
  }

  // winning squence
  public static String notesToPlay = "";

  public String noteLetterUrlGetter(String letter) {
    String url = "..\\images\\classicalRoom\\Notes\\" + letter.toUpperCase() + "Note.png";
    return url;
  }

  @FXML
  public void onClickedAKey(MouseEvent event) throws IOException {
    System.out.println("A Key Pressed");
  }

  @FXML
  public void onClickedBKey(MouseEvent event) throws IOException {
    System.out.println("B Key Pressed");
  }

  @FXML
  public void onClickedCKey(MouseEvent event) throws IOException {
    System.out.println("C Key Pressed");
  }

  @FXML
  public void onClickedDKey(MouseEvent event) throws IOException {
    System.out.println("D Key Pressed");
  }

  @FXML
  public void onClickedEKey(MouseEvent event) throws IOException {
    System.out.println("E Key Pressed");
  }

  @FXML
  public void onClickedFKey(MouseEvent event) throws IOException {
    System.out.println("F Key Pressed");
  }

  @FXML
  public void onClickedGKey(MouseEvent event) throws IOException {
    System.out.println("G Key Pressed");
  }

  @FXML
  public void onClickedLeavePiano(MouseEvent event) throws IOException {
    System.out.println("Leave Piano Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
