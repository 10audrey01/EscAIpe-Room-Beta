package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TaskManager.LargeTask;

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

  // private static final int C_NOTE_LOCATION = 193;
  // private static final int D_NOTE_LOCATION = 178;
  // private static final int E_NOTE_LOCATION = 163;
  // private static final int F_NOTE_LOCATION = 148;
  // private static final int G_NOTE_LOCATION = 133;
  // private static final int A_NOTE_LOCATION = 118;
  // private static final int B_NOTE_LOCATION = 103;

  private static final int C_NOTE_LOCATION = 90;
  private static final int D_NOTE_LOCATION = 75;
  private static final int E_NOTE_LOCATION = 60;
  private static final int F_NOTE_LOCATION = 45;
  private static final int G_NOTE_LOCATION = 30;
  private static final int A_NOTE_LOCATION = 15;
  private static final int B_NOTE_LOCATION = 0;

  // winning squence
  public static String notesToPlay = "ABCDEFGABCDEFG";

  // sequence of notes played by user
  public static String notesPlayed = "";

  private GameState gameState;
  public ArrayList<ImageView> notesList;
  public ArrayList<ImageView> notesLetterList;

  @FXML
  private void initialize() throws IOException {
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
    loadNotes();

    gameState = GameState.getInstance();
    System.out.println(gameState.taskManager.largeTask);
    // gameState.timeManager.addToTimers(timerLabel);
    if (gameState.taskManager.largeTask
        == LargeTask.ROCK) { // execute if the chosen big task is ROCK
      notesToPlay = "";
      String[] noteSequence = gameState.rockBigTaskManager.getNoteSequence();
      for (int i = 0; i < noteSequence.length; i++) {
        notesToPlay += noteSequence[i];
      }
      notesToPlay += notesToPlay; // repeat the sequence twice for 8 notes
      System.out.println(notesToPlay);
      loadRockNotes();
    }
  }

  public static void resetNotesPlayed() {
    notesPlayed = "";
  }

  public void loadNotes() throws IOException {
    for (int i = 0; i < notesList.size(); i++) {
      switch (notesToPlay.charAt(i)) {
        case 'A':
          notesList.get(i).setY(A_NOTE_LOCATION);
          break;
        case 'B':
          notesList.get(i).setY(B_NOTE_LOCATION);
          break;
        case 'C':
          notesList.get(i).setY(C_NOTE_LOCATION);
          break;
        case 'D':
          notesList.get(i).setY(D_NOTE_LOCATION);
          break;
        case 'E':
          notesList.get(i).setY(E_NOTE_LOCATION);
          break;
        case 'F':
          notesList.get(i).setY(F_NOTE_LOCATION);
          break;
        case 'G':
          notesList.get(i).setY(G_NOTE_LOCATION);
          break;
        default:
          break;
      }

      notesList.get(i).setOpacity(100);

      ImageView current = (ImageView) notesLetterList.get(i);

      Image currentImage =
          new Image(App.class.getResource(noteLetterUrlGetter(notesToPlay.charAt(i))).openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
    }
  }

  public void loadRockNotes() throws IOException {
    if (GameState.isNoteSequenceFound) {
      loadNotes();
    } else {
      for (int i = 0; i < notesList.size(); i++) {
        notesList.get(i).setOpacity(0);
        notesLetterList.get(i).setOpacity(0);
      }
    }
    System.out.println("Rock Notes Loaded");
  }

  public String noteLetterUrlGetter(Character letter) {
    String url = "/images/classicalRoom/Notes/" + Character.toUpperCase(letter) + "Note.png";
    return url;
  }

  @FXML
  public void onClickedAKey(MouseEvent event) throws IOException {
    System.out.println("A Key Pressed");
    notesPlayed += "A";
    checkWin();
  }

  @FXML
  public void onClickedBKey(MouseEvent event) throws IOException {
    System.out.println("B Key Pressed");
    notesPlayed += "B";
    checkWin();
  }

  @FXML
  public void onClickedCKey(MouseEvent event) throws IOException {
    System.out.println("C Key Pressed");
    notesPlayed += "C";
    checkWin();
  }

  @FXML
  public void onClickedDKey(MouseEvent event) throws IOException {
    System.out.println("D Key Pressed");
    notesPlayed += "D";
    checkWin();
  }

  @FXML
  public void onClickedEKey(MouseEvent event) throws IOException {
    System.out.println("E Key Pressed");
    notesPlayed += "E";
    checkWin();
  }

  @FXML
  public void onClickedFKey(MouseEvent event) throws IOException {
    System.out.println("F Key Pressed");
    notesPlayed += "F";
    checkWin();
  }

  @FXML
  public void onClickedGKey(MouseEvent event) throws IOException {
    System.out.println("G Key Pressed");
    notesPlayed += "G";
    checkWin();
  }

  public void checkWin() throws IOException {
    System.out.println(notesPlayed);
    if (notesPlayed.contains(notesToPlay)) {
      System.out.println("You Win");
      Pane current = (Pane) leavePiano.getParent();
      Scene currentScene = current.getScene();
      currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
    }
  }

  @FXML
  public void onClickedLeavePiano(MouseEvent event) throws IOException {
    System.out.println("Leave Piano Clicked");
    notesPlayed = "";
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
