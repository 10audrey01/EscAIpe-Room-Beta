package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.controllers.rooms.notes.ClassicalNoteController;
import nz.ac.auckland.se206.controllers.rooms.notes.RockNoteController;
import nz.ac.auckland.se206.controllers.rooms.rave.BodybuilderController;

/** Represents the state of the game. */
public class GameState {
  // Enum to represent game difficulty levels
  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }

  // Enum to represent game playtime options
  public enum PlayTime {
    TWO(2),
    FOUR(4),
    SIX(6);

    private final Integer time;

    PlayTime(Integer time) {
      this.time = time;
    }

    public Integer getTime() {
      return this.time;
    }
  }

  // Variables to keep track of various game states
  private static GameState instance; // Singleton instance of GameState
  public static Difficulty difficulty; // Current game difficulty
  public static PlayTime time; // Current game playtime
  public static boolean isRiddleResolved = false;
  public static boolean isRiddleObjectFound = false;
  public static boolean isNoteSequenceFound = false;
  public static boolean isKeyFound = false;
  public static boolean isMusicQuizCompleted = false;
  public static boolean isSafeOpened = false;
  public static boolean isPianoPlayed = false;
  public static boolean isHarpPlayed = false;
  public static boolean isEscaped = false;
  public static boolean isTrumpetPlayed = false;

  // Singleton pattern: Get an instance of the GameState
  public static GameState getInstance() {
    if (instance == null) {
      instance = new GameState();
      // Initialize various game managers and components
      instance.timeManager = new TimeManager();
      instance.taskManager = new TaskManager();
      instance.chatManager = new ChatManager();
      instance.rockBigTaskManager = new RockBigTaskManager();
      instance.ravePuzzle = new RavePuzzle();
      instance.objectiveListManager = new ObjectiveListManager();
      instance.hintManager = new HintManager();
    }
    return instance;
  }

  // Setter for the GameState instance
  public static void setInstance(GameState instance) {
    GameState.instance = instance;
  }

  // Reset various game variables to the initial state
  // Resetting all the tasks to incompleted for the current game
  public static void resetVariables() {
    // reset riddle fileds
    isRiddleResolved = false;
    isRiddleObjectFound = false;
    // reset note fields
    isNoteSequenceFound = false;
    isKeyFound = false;
    // reset minitask fields
    isMusicQuizCompleted = false;
    isSafeOpened = false;
    isPianoPlayed = false;
    isHarpPlayed = false;
    isEscaped = false;
  }

  // Managers and controllers to handle game components
  private TimeManager timeManager;
  private HintManager hintManager;
  private TaskManager taskManager;
  private ChatManager chatManager;
  private RockBigTaskManager rockBigTaskManager;
  private RavePuzzle ravePuzzle;
  private ObjectiveListManager objectiveListManager;
  private BodybuilderController bodybuilderController;
  private ClassicalNoteController classicalNote;
  private RockNoteController rockNote;

  public TimeManager getTimeManager() {
    return timeManager;
  }

  public HintManager getHintManager() {
    return hintManager;
  }

  public TaskManager getTaskManager() {
    return taskManager;
  }

  public ChatManager getChatManager() {
    return chatManager;
  }

  public RockBigTaskManager getRockBigTaskManager() {
    return rockBigTaskManager;
  }

  public RavePuzzle getRavePuzzle() {
    return ravePuzzle;
  }

  public ObjectiveListManager getObjectiveListManager() {
    return objectiveListManager;
  }

  public BodybuilderController getBodybuilderController() {
    return bodybuilderController;
  }

  public void setBodybuilderController(BodybuilderController bodybuilderController) {
    this.bodybuilderController = bodybuilderController;
  }

  public ClassicalNoteController getClassicalNote() {
    return classicalNote;
  }

  public void setClassicalNote(ClassicalNoteController classicalNote) {
    this.classicalNote = classicalNote;
  }

  public RockNoteController getRockNote() {
    return rockNote;
  }

  public void setRockNote(RockNoteController rockNote) {
    this.rockNote = rockNote;
  }

  // Method to start the game and initialize various components
  public void startGame() throws IOException {
    this.taskManager.generateTasks(); // Generate game tasks
    this.timeManager.setTime(time.getTime() * 60); // Set game time
    this.timeManager.startCountdown(); // Start countdown timer
    this.hintManager.initialiseManager(difficulty); // Initialize hint manager based on difficulty
    this.ravePuzzle.setHints(classicalNote, rockNote); // Set hints for the Rave puzzle
    this.bodybuilderController.initialiseCode(); // Initialize code for the Bodybuilder puzzle
  }

  // method for adding all initial labels needed for each room
  // adds the labels for global timer, aswell as hints.
  // adds the text areas and sprites for the chatmanager to update for all rooms.
  public void addInitialLabels(
      Label timerLabel,
      Label hintLabel,
      TextArea textArea,
      TextField textField,
      ImageView gmSprite,
      CheckBox ttsCheckBox) {
    // add the timer labels for the global timer
    instance.timeManager.addToTimers(timerLabel);
    // add the hint labels for the hint manager
    instance.hintManager.addHintLabel(hintLabel);
    // add the text areas and the gm sprite for the chat
    instance.chatManager.addTextArea(textArea);
    instance.chatManager.addTextField(textField);
    instance.chatManager.addSprite(gmSprite);
    // add the checkbox for the text to speech
    instance.chatManager.addTtsCheckBox(ttsCheckBox);
  }
}
