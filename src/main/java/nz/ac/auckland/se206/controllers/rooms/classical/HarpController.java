package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for handling the harp room. */
public class HarpController {
  @FXML private Line string1;
  @FXML private Line string2;
  @FXML private Line string3;
  @FXML private Line string4;
  @FXML private Line string5;
  @FXML private Line string6;
  @FXML private Line string7;
  @FXML private Line string8;
  @FXML private Line string9;
  @FXML private Line string10;
  @FXML private Line string11;
  @FXML private Line string12;
  @FXML private Line string13;
  @FXML private Line string14;
  @FXML private Line string15;
  @FXML private Line string16;
  @FXML private Line string17;
  @FXML private Line string18;
  @FXML private Line string19;
  @FXML private Line string20;
  @FXML private Line string21;
  @FXML private Line string22;
  @FXML private Line string23;
  @FXML private Line string24;
  @FXML private Line string25;
  @FXML private Line string26;
  @FXML private Line string27;
  @FXML private Line string28;
  @FXML private Line string29;
  @FXML private Line string30;
  @FXML private Line string31;
  @FXML private Line string32;
  @FXML private Line string33;
  @FXML private Line string34;
  @FXML private Circle circle1;
  @FXML private Circle circle2;
  @FXML private Circle circle3;
  @FXML private Circle circle4;
  @FXML private Circle circle5;
  @FXML private Pane leaveHarp;
  @FXML private Label timerLabel;

  private GameState gameState;
  private ArrayList<Line> strings;
  private ArrayList<Line> notesToPlay;
  private ArrayList<Paint> colours;
  private ArrayList<Paint> noteColours;
  private ArrayList<Boolean> notesToShow;
  private ArrayList<Circle> circles;
  private Line noteToPlay;
  private boolean harpSequencePlayable;
  private Paint noteOrginalColor;
  private MediaPlayer harpNotePlayer;

  // function which initialises the harp controller
  @FXML
  private void initialize() throws IOException {
    // get instance of gamestate and add the timer label to the gamestate
    this.gameState = GameState.getInstance();
    gameState.getTimeManager().addToTimers(timerLabel);

    // list of all available strings of the harp
    strings =
        new ArrayList<Line>(
            List.of(
                string1, string2, string3, string4, string5, string6, string7, string8, string9,
                string10, string11, string12, string13, string14, string15, string16, string17,
                string18, string19, string20, string21, string22, string23, string24, string25,
                string26, string27, string28, string29, string30, string31, string32, string33,
                string34));
    harpSequencePlayable = false;
    // list of all available colours
    colours =
        new ArrayList<Paint>(
            List.of(
                Color.RED,
                Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.ORANGE,
                Color.PURPLE,
                Color.PINK,
                Color.CYAN,
                Color.BROWN,
                Color.GOLD,
                Color.ALICEBLUE,
                Color.MAGENTA,
                Color.SALMON,
                Color.SLATEBLUE,
                Color.TURQUOISE));

    // initial states of fields
    notesToPlay = new ArrayList<Line>();
    notesToShow = new ArrayList<Boolean>(List.of(false, false, false, false, false));
    circles = new ArrayList<Circle>(List.of(circle1, circle2, circle3, circle4, circle5));

    // set up the initial state of the harp for the current game
    setStrings();
    generateRandomHarpNotes();
    generateRandomColours();
    setNoteColours();
    setCircleColours();
    noteToPlay = notesToPlay.get(0);
  }

  // helper function to generate the random sequence of notes for the harp
  @FXML
  private void generateRandomHarpNotes() {
    // generate 5 random unique notes for the user to play
    for (int i = 0; i < 5; i++) {
      int randomNum = (int) (Math.random() * 34);
      Line randomNote = strings.get(randomNum);
      // ensure the notes are unique
      while (notesToPlay.contains(randomNote)) {
        randomNum = (int) (Math.random() * 34);
        randomNote = strings.get(randomNum);
      }
      notesToPlay.add(randomNote);
      System.out.println(randomNote.getId() + " added");
    }
  }

  // generate random colours
  private void generateRandomColours() {
    noteColours = new ArrayList<Paint>();
    // select a random colour for every note to play
    for (int i = 0; i < notesToPlay.size(); i++) {
      int randomNum = (int) (Math.random() * 14);
      Paint randomColour = getColour(randomNum);
      // ensure the colours are unique
      while (noteColours.contains(randomColour)) {
        randomNum = (int) (Math.random() * 14);
        randomColour = getColour(randomNum);
      }
      // add to the colours list array
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
    return (colours.get(randomNum));
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

  // helper function to handle events related to the player interacting with the harp strings
  @FXML
  private void setStrings() {
    for (Line string : strings) {
      // on mouse enter, set the current string to the gray selected colours
      string.setOnMouseEntered(
          e -> {
            noteOrginalColor = string.getStroke();
            string.setStroke(javafx.scene.paint.Color.GRAY);
            System.out.println(string.getId() + " entered");
            try {
              playHarpNotePlayer(string.getId());
            } catch (URISyntaxException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
          });

      // on mouse exit, revert the strings colour to the original
      string.setOnMouseExited(
          e -> {
            string.setStroke(noteOrginalColor);
            System.out.println(string.getId() + " left");
          });

      // on the click of a string
      string.setOnMouseClicked(
          e -> {
            System.out.println(string.getId() + " clicked");
            if (!harpSequencePlayable) {
              return;
            }
            if (noteToPlay == string) {
              // if the note is the correct note, handle the correct note played event
              System.out.println("Correct note played");
              noteOrginalColor = javafx.scene.paint.Color.BLACK;
              correctNotePlayed();
            } else {
              // otherwise handle the incorrect note played event
              System.out.println("Incorrect note played");
              incorrectNotePlayed();
            }
          });
      // update the mouse cursor for the user
      string.setCursor(javafx.scene.Cursor.CLOSED_HAND);
    }
  }

  // helper function which handles the event of the correct note being played
  public void correctNotePlayed() {
    // changes the colour of the correct note thats been played
    notesToShow.set(notesToPlay.indexOf(noteToPlay), false);
    if (notesToPlay.indexOf(noteToPlay) != 4) {
      // if there are more notes to play, shift the note to play to the next note
      noteToPlay = notesToPlay.get(notesToPlay.indexOf(noteToPlay) + 1);
    } else {
      // otherwise the harp should be completed - update the relevant gamestate
      System.out.println("Harp completed");
      harpSequencePlayable = false;
      GameState.isHarpPlayed = true;
      gameState.getObjectiveListManager().completeObjective4();
    }
    // set the circle colours
    setCircleColours();
  }

  // function for handling if an incorrect note is played
  public void incorrectNotePlayed() {
    // reset the sequence of notes the player has played, as they have to restart
    noteToPlay = notesToPlay.get(0);
    notesToShow = new ArrayList<Boolean>(List.of(true, true, true, true, true));
    // reset the colours for the gui
    setNoteColours();
    setCircleColours();
  }

  // sets the colours for the circles that the player has found through the map
  public void setCirclesFound(int index) {
    notesToShow.set(index, true);
    // set the notes and colours for the found circles
    setNoteColours();
    setCircleColours();
    if (!harpSequencePlayable) {
      // check if the player has every note before allowing playing
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

  public void playHarpNotePlayer(String audioName) throws URISyntaxException {
    Media note =
        new Media(
            getClass().getResource("/sounds/harpSounds/" + audioName + ".wav").toURI().toString());
    harpNotePlayer = new MediaPlayer(note);
    harpNotePlayer.setVolume(.05);
    harpNotePlayer.play();
  }
}
