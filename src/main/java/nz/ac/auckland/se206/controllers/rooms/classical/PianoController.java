package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import java.net.URISyntaxException;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TaskManager.LargeTask;

/** Controller class for handling the piano room. */
public class PianoController {

  private static final int C_NOTE_LOCATION = 90;
  private static final int D_NOTE_LOCATION = 75;
  private static final int E_NOTE_LOCATION = 60;
  private static final int F_NOTE_LOCATION = 45;
  private static final int G_NOTE_LOCATION = 30;
  private static final int A_NOTE_LOCATION = 15;
  private static final int B_NOTE_LOCATION = 0;

  // winning squence
  public static String notesToPlay = "ABCDEFGA";

  // sequence of notes played by user
  public static String notesPlayed = "";

  /** Resets the notes played by the user. */
  public static void resetNotesPlayed() {
    notesPlayed = "";
  }

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
  @FXML private Pane keyA;
  @FXML private Pane keyB;
  @FXML private Pane keyC;
  @FXML private Pane keyD;
  @FXML private Pane keyE;
  @FXML private Pane keyF;
  @FXML private Pane keyG;
  @FXML private Pane leavePiano;
  @FXML private Label timerLabel;

  private GameState gameState;
  private ArrayList<ImageView> notesList;
  private ArrayList<ImageView> notesLetterList;
  private MediaPlayer pianoNotePlayer;

  /**
   * Initializes the PianoEventController by setting up the game state, labels, and other
   * game-related variables.
   *
   * @throws IOException If there is an issue with input/output.
   */
  @FXML
  private void initialize() throws IOException {
    // gets the gamestate instance, adding the labels for timers to the gamestate
    gameState = GameState.getInstance();
    gameState.getTimeManager().addToTimers(timerLabel);
    // initialise the notes and note letters
    notesList =
        new ArrayList<ImageView>(List.of(note1, note2, note3, note4, note5, note6, note7, note8));
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
                note8Letter));
    loadNotes();

    gameState = GameState.getInstance();

    if (gameState.getTaskManager().getCurrentLargeTask()
        == LargeTask.ROCK) { // execute if the chosen big task is ROCK
      notesToPlay = "";
      String[] noteSequence = gameState.getRockBigTaskManager().getNoteSequence();
      StringBuilder notesBuilder = new StringBuilder();

      for (int i = 0; i < noteSequence.length; i++) {
        notesBuilder.append(noteSequence[i]);
      }

      String notesToPlay = (notesBuilder.append(notesBuilder)).toString();
      System.out.println("Notes to play: " + notesToPlay);
      System.out.println(notesToPlay);
      loadRockNotes();
    }
  }

  /**
   * Loads the notes on the music sheet based on their location on the sheet.
   *
   * @throws IOException If there is an issue with loading note letter images.
   */
  public void loadNotes() throws IOException {
    for (int i = 0; i < notesList.size(); i++) {
      // set the y location of each note
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

      notesList.get(i).setOpacity(100); // make the notes visible

      ImageView current = notesLetterList.get(i);

      // load the note letter image
      Image currentImage =
          new Image(App.class.getResource(noteLetterUrlGetter(notesToPlay.charAt(i))).openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
    }
  }

  /**
   * Loads the notes for the rock task or hides them based on the game state.
   *
   * @throws IOException If there is an issue with loading note letter images.
   */
  public void loadRockNotes() throws IOException {
    if (gameState.getTaskManager().getCurrentLargeTask() == LargeTask.ROCK) {
      if (GameState.isNoteSequenceFound) {
        loadNotes();
        for (int i = 0; i < notesLetterList.size(); i++) {
          notesLetterList.get(i).setOpacity(100);
        }
      } else { // music sheet does not show notes to play
        for (int i = 0; i < notesList.size(); i++) {
          notesList.get(i).setOpacity(0);
        }
        for (int i = 0; i < notesLetterList.size(); i++) {
          notesLetterList.get(i).setOpacity(0);
        }
      }
    }
  }

  /**
   * Gets the URL for the note letter image based on the note letter.
   *
   * @param letter The note letter (A, B, C, etc.).
   * @return The URL of the note letter image.
   */
  public String noteLetterUrlGetter(Character letter) {
    String url = "/images/classicalRoom/Notes/" + Character.toUpperCase(letter) + "Note.png";
    return url;
  }

  /**
   * Handles the event when a piano key is clicked, plays the corresponding note, and checks for a
   * win condition.
   *
   * @param event The MouseEvent representing the key click event.
   * @throws IOException If there is an issue with playing the piano note.
   * @throws URISyntaxException If there is an issue with the audio file's URI.
   */
  @FXML
  public void onClickedKeyA(MouseEvent event) throws IOException, URISyntaxException {
    System.out.println("A Key Pressed");
    notesPlayed += "A";
    playPianoNotePlayer("a6");
    checkWin();
  }

  /**
   * Handles the event when a piano key is clicked, plays the corresponding note, and checks for a
   * win condition.
   *
   * @param event The MouseEvent representing the key click event.
   * @throws IOException If there is an issue with playing the piano note.
   * @throws URISyntaxException If there is an issue with the audio file's URI.
   */
  @FXML
  public void onClickedKeyB(MouseEvent event) throws IOException, URISyntaxException {
    System.out.println("B Key Pressed");
    notesPlayed += "B";
    playPianoNotePlayer("b6");
    checkWin();
  }

  /**
   * Handles the event when a piano key is clicked, plays the corresponding note, and checks for a
   * win condition.
   *
   * @param event The MouseEvent representing the key click event.
   * @throws IOException If there is an issue with playing the piano note.
   * @throws URISyntaxException If there is an issue with the audio file's URI.
   */
  @FXML
  public void onClickedKeyC(MouseEvent event) throws IOException, URISyntaxException {
    System.out.println("C Key Pressed");
    notesPlayed += "C";
    playPianoNotePlayer("c6");
    checkWin();
  }

  /**
   * Handles the event when a piano key is clicked, plays the corresponding note, and checks for a
   * win condition.
   *
   * @param event The MouseEvent representing the key click event.
   * @throws IOException If there is an issue with playing the piano note.
   * @throws URISyntaxException If there is an issue with the audio file's URI.
   */
  @FXML
  public void onClickedKeyD(MouseEvent event) throws IOException, URISyntaxException {
    System.out.println("D Key Pressed");
    notesPlayed += "D";
    playPianoNotePlayer("d6");
    checkWin();
  }

  /**
   * Handles the event when a piano key is clicked, plays the corresponding note, and checks for a
   * win condition.
   *
   * @param event The MouseEvent representing the key click event.
   * @throws IOException If there is an issue with playing the piano note.
   * @throws URISyntaxException If there is an issue with the audio file's URI.
   */
  @FXML
  public void onClickedKeyE(MouseEvent event) throws IOException, URISyntaxException {
    System.out.println("E Key Pressed");
    notesPlayed += "E";
    playPianoNotePlayer("e6");
    checkWin();
  }

  /**
   * Handles the event when a piano key is clicked, plays the corresponding note, and checks for a
   * win condition.
   *
   * @param event The MouseEvent representing the key click event.
   * @throws IOException If there is an issue with playing the piano note.
   * @throws URISyntaxException If there is an issue with the audio file's URI.
   */
  @FXML
  public void onClickedKeyF(MouseEvent event) throws IOException, URISyntaxException {
    System.out.println("F Key Pressed");
    notesPlayed += "F";
    playPianoNotePlayer("f6");
    checkWin();
  }

  /**
   * Handles the event when a piano key is clicked, plays the corresponding note, and checks for a
   * win condition.
   *
   * @param event The MouseEvent representing the key click event.
   * @throws IOException If there is an issue with playing the piano note.
   * @throws URISyntaxException If there is an issue with the audio file's URI.
   */
  @FXML
  public void onClickedKeyG(MouseEvent event) throws IOException, URISyntaxException {
    System.out.println("G Key Pressed");
    notesPlayed += "G";
    playPianoNotePlayer("g6");
    checkWin();
  }

  /**
   * Uses a media player to play the piano note with the given audio name.
   *
   * @param audioName The name of the audio file to play.
   * @throws URISyntaxException If there is an issue with the audio file's URI.
   */
  public void playPianoNotePlayer(String audioName) throws URISyntaxException {
    Media note =
        new Media(getClass().getResource("/sounds/" + audioName + ".mp3").toURI().toString());
    pianoNotePlayer = new MediaPlayer(note);
    pianoNotePlayer.play();
  }

  /**
   * Checks if the user has played the correct note sequence and updates the game state accordingly.
   *
   * @throws IOException If there is an issue with switching scenes.
   */
  public void checkWin() throws IOException {
    System.out.println(notesPlayed);
    if (notesPlayed.contains(notesToPlay)) { // user has played the correct sequence
      System.out.println("You Win");
      GameState.isPianoPlayed = true;
      gameState.getObjectiveListManager().setVisibilityKey(2, true); // key 3 obtained after win
      Pane current = (Pane) leavePiano.getParent();
      Scene currentScene = current.getScene();
      currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
    }
  }

  /**
   * Handles the event when the "Leave Piano" button is clicked to switch back to the classical
   * room.
   *
   * @param event The MouseEvent representing the button click event.
   * @throws IOException If there is an issue with switching scenes.
   */
  @FXML
  public void onClickedLeavePiano(MouseEvent event) throws IOException {
    System.out.println("Leave Piano Clicked");
    notesPlayed = "";
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
