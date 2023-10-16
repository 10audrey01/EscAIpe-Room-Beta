package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.rooms.classical.HarpController;
import nz.ac.auckland.se206.tasks.HarpTask;
import nz.ac.auckland.se206.tasks.MusicQuizTask;
import nz.ac.auckland.se206.tasks.RiddleTask;
import nz.ac.auckland.se206.tasks.SafeTask;
import nz.ac.auckland.se206.tasks.Task;

/** Controller class for handling the rave room. */
public class RaveController extends RoomController {
  private static ArrayList<String> objects;
  private static String riddleObject;

  /**
   * Returns a random riddle object from the list of available objects in the room.
   *
   * @return A randomly selected riddle object.
   */
  public static String getRiddleObject() {
    riddleObject = objects.get((int) (Math.random() * objects.size()));
    System.out.println("Riddle object: " + riddleObject);
    return riddleObject;
  }

  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle rockDoor;
  @FXML private Pane posterPane;
  @FXML private Pane djPane;
  @FXML private Pane bodybuilderPane;
  @FXML private Pane bouncerPane;
  @FXML private Pane discoPane;
  @FXML private Pane speakerPane;
  @FXML private Pane chatBoxPane;
  @FXML private ImageView doorImage;
  @FXML private ImageView greenLock;
  @FXML private ImageView redLock;
  @FXML private ImageView blueLock;
  @FXML private ImageView yellowLock;
  @FXML private ImageView openedDoor;
  @FXML private ImageView noteImage1;
  @FXML private Circle circle3;
  @FXML private Circle circle4;
  @FXML private Circle circle5;
  @FXML private VBox objectiveList;

  private ArrayList<Circle> circles;

  // gamestate instance and the state of the exit locks
  private boolean isRedLockUnlocked = false;
  private boolean isGreenLockUnlocked = false;
  private boolean isBlueLockUnlocked = false;
  private boolean isYellowLockUnlocked = false;

  // index of the task in the objective list
  private int taskIndex;

  /** Initializes the FXML file for the rock room and sets up various components and variables. */
  @FXML
  private void initialize() {
    initialiseAllGameStateVariables();
    taskIndex = gameState.getTaskManager().getTaskIndex(RiddleTask.class);

    chatOpened = true;

    // create an array list of objects in the room
    objects = new ArrayList<String>();
    objects.add("band poster");
    objects.add("bouncer");
    objects.add("disco ball");
    objects.add("speakers");

    // set circles for classical room task
    circles = new ArrayList<Circle>(List.of(circle3, circle4, circle5));
    setCircles();
  }

  /**
   * Handles clicking on the poster event in the rock room. Checks if the clicked object matches the
   * riddle object.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickPoster(MouseEvent event) {
    System.out.println("poster clicked");
    isRiddleObject("band poster");
  }

  /**
   * Handles clicking on the DJ in the rock room. Switches the scene to the music quiz scene.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickDj(MouseEvent event) {
    System.out.println("dj clicked");
    // switches the scene to the music quiz scene if the task has been selected
    if (gameState.getTaskManager().getTaskIndex(MusicQuizTask.class) != -1) {
      Pane current = (Pane) event.getSource();
      Scene currentScene = current.getScene();
      currentScene.setRoot(SceneManager.getUiRoot(AppUi.MUSICQUIZ));
    }
  }

  /**
   * Handles clicking on the bodybuilder in the rock room. Switches the scene to the bodybuilder
   * safe minigame scene.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickBodybuilder(MouseEvent event) {
    System.out.println("bodybuilder clicked");
    // switches the scene to the bodybuilder safe minigame scene only if safe task has been selected
    if (gameState.getTaskManager().getTaskIndex(SafeTask.class) != -1) {
      Pane current = (Pane) event.getSource();
      Scene currentScene = current.getScene();
      currentScene.setRoot(SceneManager.getUiRoot(AppUi.BODYBUILDER));
    }
  }

  /**
   * Handles clicking on the bouncer in the rock room. Checks if the clicked object matches the
   * riddle object.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickBouncer(MouseEvent event) {
    System.out.println("bouncer clicked");
    isRiddleObject("bouncer");
  }

  /**
   * Handles clicking on the disco ball in the rock room. Checks if the clicked object matches the
   * riddle object.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickDisco(MouseEvent event) {
    System.out.println("disco clicked");
    isRiddleObject("disco ball");
  }

  /**
   * Handles clicking on the speakers in the rock room. Checks if the clicked object matches the
   * riddle object.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickSpeaker(MouseEvent event) {
    System.out.println("speaker clicked");
    isRiddleObject("speakers");
  }

  /**
   * Handles clicking on the red lock in the room. Removes the lock if the player has completed the
   * piano task.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickRed(MouseEvent event) {
    // removes the lock if the player has completed the piano task
    if (gameState.getTaskManager().getTask(2).isCompleted()) {
      // set the visibility of the key 3 to false
      gameState.getObjectiveListManager().setVisibilityKey(2, false);
      redLock.setVisible(false);
      isRedLockUnlocked = true;
    }
    System.out.println("red clicked");
  }

  /**
   * Handles clicking on the green lock in the room. Removes the lock if the player has completed
   * the safe task.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickGreen(MouseEvent event) {
    // removes the lock if the player has completed the safe task
    if (gameState.getTaskManager().getTask(1).isCompleted()) {
      // set the visibility of the key 2 to false
      gameState.getObjectiveListManager().setVisibilityKey(1, false);
      greenLock.setVisible(false);
      isGreenLockUnlocked = true;
    }
    System.out.println("green clicked");
  }

  /**
   * Handles clicking on the blue lock in the room. Removes the lock if the player has completed the
   * music quiz.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickBlue(MouseEvent event) {
    // removes the lock if the player has completed the music quiz
    if (gameState.getTaskManager().getTask(0).isCompleted()) {
      // set the visibility of the key 1 to false
      gameState.getObjectiveListManager().setVisibilityKey(0, false);
      blueLock.setVisible(false);
      isBlueLockUnlocked = true;
    }
    System.out.println("blue clicked");
  }

  /**
   * Handles clicking on the yellow lock in the room. Removes the lock if the player has completed
   * the harp event.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickYellow(MouseEvent event) {
    // removes the lock if player has completed the harp event
    if (gameState.getTaskManager().getTask(3).isCompleted()) {
      // set the visibility of the key 4 to false
      gameState.getObjectiveListManager().setVisibilityKey(3, false);
      yellowLock.setVisible(false);
      isYellowLockUnlocked = true;
    }
    System.out.println("yellow clicked");
  }

  /**
   * Handles clicking on the door in the room. Unlocks the door if the player has unlocked all the
   * locks.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  private void onClickDoor(MouseEvent event) {
    // unlocks the door if the player has unlocked all the locks
    if (isRedLockUnlocked && isGreenLockUnlocked && isBlueLockUnlocked && isYellowLockUnlocked) {
      openedDoor.setDisable(false);
      openedDoor.setVisible(true);
      System.out.println("door unlocked");
    }
    System.out.println("door clicked");
  }

  /**
   * Handles clicking on the opened door in the room. Sets the game won state to true, changing the
   * scene to the end game scene.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If an I/O error occurs during scene transition.
   */
  @FXML
  private void onClickOpenedDoor(MouseEvent event) throws IOException {
    // sets the game won state to true, changing the scene to the end game scene
    GameState.isEscaped = true;
    App.setRoot("end");
    gameState.getTimeManager().stopCountdown();
    System.out.println("opened door clicked");
  }

  /**
   * Handles clicking on the classical door to transition to the classical room scene.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If an I/O error occurs during scene transition.
   */
  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    // changes the scene to the classical room
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  /**
   * Handles clicking on the rock door to transition to the rock room scene.
   *
   * @param event The MouseEvent representing the click event.
   * @throws IOException If an I/O error occurs during scene transition.
   */
  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    // changes the scene to the rock room
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

  /** Toggles note visibility by showing note panes and hiding arrows. */
  @FXML
  private void onClickNote1() {
    gameState.getRockBigTaskManager().setVisibilityNotePanes(true);
    gameState.getRockBigTaskManager().setVisibilityNoteArrows(false);
  }

  /**
   * Helper function to check if the clicked object matches the riddle object and updates relevant
   * labels.
   *
   * @param object The object clicked by the player.
   */
  public void isRiddleObject(String object) {
    if (riddleObject.equals(object) && GameState.isRiddleSolved && !GameState.isRiddleObjectFound) {
      // if the object is the correct one, set relevant labels to notify the user
      GameState.isRiddleObjectFound = true;
      gameState.getObjectiveListManager().completeObjective(taskIndex);
      gameState.getRockBigTaskManager().setLabelColours();
      gameState.getRockBigTaskManager().setOrderColourMap();
      gameState.getRockBigTaskManager().setVisibilityNoteImages(true);
      gameState.getRockBigTaskManager().setVisibilityNoteArrows(true);
    }
  }

  /**
   * Sets up the circles for the harp game by selecting random colors for each circle and filling
   * them.
   */
  public void setCircles() {
    // select random colours for each circle and fills them
    boolean harpTaskChosen = false;
    for (int i = 0; i < gameState.getTaskManager().taskList.size(); i++) {
      Task task = gameState.getTaskManager().taskList.get(i);
      if (task instanceof HarpTask) {
        harpTaskChosen = true;
      }
    }
    if (harpTaskChosen) {
      HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
      for (int i = 0; i < circles.size(); i++) {
        circles.get(i).setFill(harpController.getColourIndex(i + 2));
        circles.get(i).setOpacity(100);
      }
    }
  }

  /**
   * Handles clicking on the third circle in the harp game.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  public void onClickedCircle3(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 3 clicked");
    harpController.setCirclesFound(2);
    circle3.setOpacity(0);
    circle3.setDisable(true);
  }

  /**
   * Handles clicking on the fourth circle in the harp game.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  public void onClickedCircle4(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 4 clicked");
    harpController.setCirclesFound(3);
    circle4.setOpacity(0);
    circle4.setDisable(true);
  }

  /**
   * Handles clicking on the fifth circle in the harp game.
   *
   * @param event The MouseEvent representing the click event.
   */
  @FXML
  public void onClickedCircle5(MouseEvent event) {
    HarpController harpController = (HarpController) SceneManager.getController(AppUi.HARP);
    System.out.println("circle 5 clicked");
    harpController.setCirclesFound(4);
    circle5.setOpacity(0);
    circle5.setDisable(true);
  }
}
