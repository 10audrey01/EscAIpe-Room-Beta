package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.DraggableMaker;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.rooms.classical.PianoController;

/** Controller class for handling the classical room. */
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
  @FXML private ImageView tambourineImage;

  @FXML private Pane classicalNotePane;
  @FXML private VBox objectiveList;

  // put these into gamestate later
  private boolean isTambourineBroken;
  private boolean isDraggable;
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

  /**
   * Initializes the room's FXML, sets initial values for room-specific variables, and prepares the
   * room for interaction.
   */
  @FXML
  private void initialize() {

    initialiseAllGameStateVariables();

    // initial states for fields
    numOfTambourinePresses = 0;
    isTambourineBroken = false;
    isDraggable = false;
    tambourineLimit = 100;
  }

  /** Makes the cello bow and tambourine panes draggable and starts the cello play timer. */
  private void makeObjectsDraggable() {
    draggableMaker.makeDraggable(celloBowPane);
    draggableMaker.makeDraggable(tambourinePane);
    celloPlayTimer.start();
  }

  /**
   * Checks if two panes intersect and one of them is pressed.
   *
   * @param pane1 The first Pane to check for collision.
   * @param pane2 The second Pane to check for collision.
   */
  protected void checkCollision(Pane pane1, Pane pane2) {
    if (pane1.getBoundsInParent().intersects(pane2.getBoundsInParent()) && pane1.isPressed()) {
      System.out.println("Bow and Strings Collided!!");
    }
  }

  /**
   * Resets the puzzle state and switches to the rave room.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception while switching scenes.
   */
  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    numOfTambourinePresses = 0;
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  /**
   * Resets the puzzle state and switches to the rock room.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception while switching scenes.
   */
  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    numOfTambourinePresses = 0;
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

  /**
   * Handles clicking on the cello in the classical room.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception.
   */
  @FXML
  private void doClickedCello(MouseEvent event) throws IOException {
    System.out.println("Cello Clicked");
  }

  /**
   * Handles clicking on the cello bow in the classical room.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception.
   */
  @FXML
  private void doClickedCelloBow(MouseEvent event) throws IOException {
    System.out.println("Cello Bow Clicked");
  }

  /**
   * Handles clicking on the clarinet in the classical room.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception.
   */
  @FXML
  private void doClickedClarinet(MouseEvent event) throws IOException {
    System.out.println("Clarinet Clicked");
  }

  /**
   * Handles clicking on the grand piano in the classical room.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception while switching scenes.
   */
  @FXML
  private void doClickedGrandPiano(MouseEvent event) throws IOException {
    // changes the scene to the piano scene controller.
    System.out.println("Grand Piano Clicked");
    

    PianoController pianoController = (PianoController) SceneManager.getController(AppUi.PIANO);
    pianoController.resetNotesPlayed();
    pianoController.loadRockNotes();

    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();

    currentScene.setRoot(SceneManager.getUiRoot(AppUi.PIANO));
  }

  /**
   * Handles clicking on the trumpet in the classical room.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception.
   */
  @FXML
  private void doClickedTrumpet(MouseEvent event) throws IOException {
    // changes the scene to the trumpet scene controller.
    System.out.println("Trumpet Clicked");
  }

  /**
   * Handles clicking on the harp in the classical room and switches to the harp controller scene.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception while switching scenes.
   */
  @FXML
  private void doClickedHarp(MouseEvent event) throws IOException {
    // changes the scene to the harp controller scene
    System.out.println("Harp Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();

    currentScene.setRoot(SceneManager.getUiRoot(AppUi.HARP));
  }

  /**
   * Handles clicking on the tambourine in the classical room, increments click count, and makes
   * objects draggable if conditions are met.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If there is an I/O exception.
   */
  @FXML
  private void doClickedTambourine(MouseEvent event) throws IOException {
    // increments the times tambourine has been clicked
    System.out.println("Tambourine Clicked");
    if (GameState.isHarpPlayed && !isDraggable) {
      makeObjectsDraggable();
    }
    if (numOfTambourinePresses >= tambourineLimit && !isTambourineBroken) {
      // if the tambourine has been clicked enough times, break it
      System.out.println("Tambourine Broken");
      Image currentImage =
          new Image(
              App.class.getResource("/images/classicalRoom/BrokenTambourine.png").openStream());
      Platform.runLater(
          () -> {
            tambourineImage.setImage(currentImage);
          });
      isTambourineBroken = true;
    }
    numOfTambourinePresses++;
  }

  /**
   * Handles clicking on the note in the classical room, switching to the note scene.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  public void onClickNote(MouseEvent event) {
    // changes the scene to the scene for the note
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICALNOTE));
  }

  /** Handles the note toggling event to set note panes visible and arrows invisible. */
  @FXML
  private void onClickNote1() {
    gameState.getRockBigTaskManager().setVisibilityNotePanes(true);
    gameState.getRockBigTaskManager().setVisibilityNoteArrows(false);
  }
}
