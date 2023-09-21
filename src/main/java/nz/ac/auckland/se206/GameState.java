package nz.ac.auckland.se206;

import java.io.IOException;
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

  // Reset various game variables
  public static void resetVariables() {
    isRiddleResolved = false;
    isRiddleObjectFound = false;
    isNoteSequenceFound = false;
    isKeyFound = false;
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
      ImageView gmSprite) {
    instance.timeManager.addToTimers(timerLabel);
    instance.hintManager.addHintLabel(hintLabel);
    instance.chatManager.addTextArea(textArea);
    instance.chatManager.addTextField(textField);
    instance.chatManager.addSprite(gmSprite);
  }

  // method for adding all objective list labels
  // adds all the respective labels needed for the objective list UI to the gamestate, allowing for
  // them all to be updated at once for every room.
  public void addObjectiveListLabels(
      Label step1Label,
      Label step2Label,
      Label step3Label,
      Label step4Label,
      ImageView step1BlueKey,
      ImageView step2GreenKey,
      ImageView step3RedKey,
      ImageView step4YellowKey) {
    // add all the task labels
    instance.objectiveListManager.addObjectiveLabel1(step1Label);
    instance.objectiveListManager.addObjectiveLabel2(step2Label);
    instance.objectiveListManager.addObjectiveLabel3(step3Label);
    instance.objectiveListManager.addObjectiveLabel4(step4Label);
    // add all the key images
    instance.objectiveListManager.addStep1Key(step1BlueKey);
    instance.objectiveListManager.addStep2Key(step2GreenKey);
    instance.objectiveListManager.addStep3Key(step3RedKey);
    instance.objectiveListManager.addStep4Key(step4YellowKey);
  }
}
