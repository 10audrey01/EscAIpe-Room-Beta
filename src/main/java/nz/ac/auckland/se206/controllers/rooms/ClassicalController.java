package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.DraggableMaker;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.rooms.classical.PianoController;

public class ClassicalController extends AbstractRoomController {

  @FXML private Rectangle raveDoor;
  @FXML private Rectangle rockDoor;
  @FXML private Pane celloPane;
  @FXML private Pane celloStrings;
  @FXML private Pane celloBowPane;
  @FXML private Pane grandPianoPane;
  @FXML private Pane clarinetPane;
  @FXML private Pane harpPane;
  @FXML private Pane tambourinePane;
  @FXML private Pane trumpetPane;
  @FXML private Pane chatBoxPane;

  @FXML private Pane classicalNotePane;
  @FXML private VBox objectiveList;

  // put these into gamestate later
  private boolean isTambourineBreakable;
  private int numOfTambourinePresses;
  private int tambourineLimit;

  private GameState gameState;

  private DraggableMaker draggableMaker = new DraggableMaker();

  private AnimationTimer celloPlayTimer =
      new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
          checkCollision(celloBowPane, celloStrings);
        }
      };

  // function for initialising room FXML
  @FXML
  private void initialize() {
    initialiseAllGameStateVariables();
    // initial states for fields
    numOfTambourinePresses = 0;
  }

  // function which makes the cello bow and tambourine pane draggable
  private void makeObjectsDraggable() {
    draggableMaker.makeDraggable(celloBowPane);
    draggableMaker.makeDraggable(tambourinePane);
    celloPlayTimer.start();
  }

  // function which checks if the panes for the bow and string collide
  protected void checkCollision(Pane pane1, Pane pane2) {
    if (pane1.getBoundsInParent().intersects(pane2.getBoundsInParent()) && pane1.isPressed()) {
      System.out.println("Bow and Strings Collided!!");
    }
  }

  // function which resets the puzzle state and switches to the rave room
  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    numOfTambourinePresses = 0;
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  // function which resets the puzzle state and switches to the rock room
  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    numOfTambourinePresses = 0;
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

  // function for handling cello clicked
  @FXML
  private void doClickedCello(MouseEvent event) throws IOException {
    System.out.println("Cello Clicked");
  }

  // function for handling cello bow clicked
  @FXML
  private void doClickedCelloBow(MouseEvent event) throws IOException {
    System.out.println("Cello Bow Clicked");
  }

  // function for handling clarinet being clicked
  @FXML
  private void doClickedClarinet(MouseEvent event) throws IOException {
    System.out.println("Clarinet Clicked");
  }

  // function for handling grand piano clicked
  @FXML
  private void doClickedGrandPiano(MouseEvent event) throws IOException {
    // changes the scene to the piano scene controller.
    System.out.println("Grand Piano Clicked");
    PianoController.resetNotesPlayed();

    PianoController pianoController = (PianoController) SceneManager.getController(AppUi.PIANO);
    pianoController.loadRockNotes();

    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();

    currentScene.setRoot(SceneManager.getUiRoot(AppUi.PIANO));
  }

  // function for handling trumpet clicked
  @FXML
  private void doClickedTrumpet(MouseEvent event) throws IOException {
    // changes the scene to the trumpet scene controller.
    System.out.println("Trumpet Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();

    currentScene.setRoot(SceneManager.getUiRoot(AppUi.TRUMPET));
  }

  // function for handling harp clicked
  @FXML
  private void doClickedHarp(MouseEvent event) throws IOException {
    // changes the scene to the harp controller scene
    System.out.println("Harp Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();

    currentScene.setRoot(SceneManager.getUiRoot(AppUi.HARP));
  }

  // function for handling tambourine clicked
  @FXML
  private void doClickedTambourine(MouseEvent event) throws IOException {
    // increments the times tambourine has been clicked
    System.out.println("Tambourine Clicked");
    numOfTambourinePresses++;
  }

  // function for handling clicking the note
  @FXML
  public void onClickNote(MouseEvent event) {
    // changes the scene to the scene for the note
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICALNOTE));
  }

  // function for handling the note toggling event
  @FXML
  private void onClickNote1() {
    gameState.getRockBigTaskManager().setVisibilityNotePanes(true);
    gameState.getRockBigTaskManager().setVisibilityArrows(false);
  }
}
