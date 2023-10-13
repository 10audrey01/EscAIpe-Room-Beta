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

/**
 * Represents the state of the game, including game difficulty, playtime, and various game states.
 */
public class GameState {
  /** Difficulty enum to represent the game difficulty. */
  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }

  /** Playtime enum to represent the gme time. Each enum returns its respective time in minutes. */
  public enum PlayTime {
    TWO(2),
    FOUR(4),
    SIX(6);

    private final Integer time;

    PlayTime(Integer time) {
      this.time = time;
    }

    /**
     * Gets the playtime duration in minutes.
     *
     * @return The playtime duration in minutes.
     */
    public Integer getTime() {
      return this.time;
    }
  }

  // Variables to keep track of various game states
  private static GameState instance; // Singleton instance of GameState
  public static Difficulty difficulty; // Current game difficulty
  public static PlayTime time; // Current game playtime

  // task completion variables for long task
  public static boolean isRiddleSolved = false;
  public static boolean isRiddleObjectFound = false;
  public static boolean isGuitarsPlayed = false;
  public static boolean isPianoPlayed = false;

  // task completion variables for short tasks
  public static boolean isMusicQuizCompleted = false;
  public static boolean isSafeOpened = false;
  public static boolean isHarpPlayed = false;
  public static boolean isTrumpetPlayed = false;

  // task completion variables for ending the game
  public static boolean isKeyFound = false;
  public static boolean isEscaped = false;

  /**
   * Returns the singleton instance of the GameState class.
   *
   * @return The GameState instance.
   */
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

  /**
   * Sets the GameState instance to the new given instance of a GameState.
   *
   * @param instance The new GameState instance.
   */
  public static void setInstance(GameState instance) {
    GameState.instance = instance;
  }

  /**
   * Resets various game variables to their initial state, allowing for a new game session. All
   * tasks are reset to their initial incompleted state for the current game.
   */
  public static void resetVariables() {
    // reset riddle fileds
    isRiddleSolved = false;
    isRiddleObjectFound = false;
    // reset note fields
    isGuitarsPlayed = false;
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

  /**
   * Gets the TimeManager responsible for managing game time and time-related events.
   *
   * @return The TimeManager instance.
   */
  public TimeManager getTimeManager() {
    return timeManager;
  }

  /**
   * Gets the HintManager responsible for managing hints and hint-related functionalities in the
   * game.
   *
   * @return The HintManager instance.
   */
  public HintManager getHintManager() {
    return hintManager;
  }

  /**
   * Gets the TaskManager responsible for managing game tasks, objectives, and task-related
   * activities.
   *
   * @return The TaskManager instance.
   */
  public TaskManager getTaskManager() {
    return taskManager;
  }

  /**
   * Gets the ChatManager responsible for handling in-game chat interactions and messaging.
   *
   * @return The ChatManager instance.
   */
  public ChatManager getChatManager() {
    return chatManager;
  }

  /**
   * Gets the RockBigTaskManager responsible for managing the "Rock Big Task" in the game.
   *
   * @return The RockBigTaskManager instance.
   */
  public RockBigTaskManager getRockBigTaskManager() {
    return rockBigTaskManager;
  }

  /**
   * Gets the RavePuzzle responsible for managing the Rave Puzzle mini-game in the game.
   *
   * @return The RavePuzzle instance.
   */
  public RavePuzzle getRavePuzzle() {
    return ravePuzzle;
  }

  /**
   * Gets the ObjectiveListManager responsible for managing the game's objective list and
   * objectives.
   *
   * @return The ObjectiveListManager instance.
   */
  public ObjectiveListManager getObjectiveListManager() {
    return objectiveListManager;
  }

  /**
   * Gets the BodybuilderController responsible for managing the Bodybuilder mini-game in the game.
   *
   * @return The BodybuilderController instance.
   */
  public BodybuilderController getBodybuilderController() {
    return bodybuilderController;
  }

  /**
   * Sets the BodybuilderController responsible for managing the Bodybuilder mini-game in the game.
   *
   * @param bodybuilderController The BodybuilderController instance to set.
   */
  public void setBodybuilderController(BodybuilderController bodybuilderController) {
    this.bodybuilderController = bodybuilderController;
  }

  /**
   * Gets the ClassicalNoteController responsible for managing the Classical Note mini-game in the
   * game.
   *
   * @return The ClassicalNoteController instance.
   */
  public ClassicalNoteController getClassicalNote() {
    return classicalNote;
  }

  /**
   * Sets the ClassicalNoteController responsible for managing the Classical Note mini-game in the
   * game.
   *
   * @param classicalNote The ClassicalNoteController instance to set.
   */
  public void setClassicalNote(ClassicalNoteController classicalNote) {
    this.classicalNote = classicalNote;
  }

  /**
   * Gets the RockNoteController responsible for managing the Rock Note mini-game in the game.
   *
   * @return The RockNoteController instance.
   */
  public RockNoteController getRockNote() {
    return rockNote;
  }

  /**
   * Sets the RockNoteController responsible for managing the Rock Note mini-game in the game.
   *
   * @param rockNote The RockNoteController instance to set.
   */
  public void setRockNote(RockNoteController rockNote) {
    this.rockNote = rockNote;
  }

  /**
   * Starts the game and initializes various game components including tasks, timers, hints, and
   * puzzles.
   *
   * @throws IOException If an I/O error occurs during game initialization.
   */
  public void startGame() throws IOException {
    this.timeManager.setTime(time.getTime() * 60); // Set game time
    this.timeManager.startCountdown(); // Start countdown timer
    this.hintManager.initialiseManager(difficulty); // Initialize hint manager based on difficulty
    this.ravePuzzle.setHints(classicalNote, rockNote); // Set hints for the Rave puzzle
    this.bodybuilderController.initialiseCode(); // Initialize code for the Bodybuilder puzzle
    this.objectiveListManager.initialiseObjectiveList(); // Initialize objective list
  }

  /**
   * Adds initial labels and components needed for each room, including timer labels, hint labels,
   * chat text areas, GM sprite, and text-to-speech checkbox.
   *
   * @param timerLabel The label for displaying the global timer.
   * @param hintLabel The label for displaying hints.
   * @param textArea The text area for displaying chat messages.
   * @param textField The text field for entering chat messages.
   * @param gmSprite The sprite for the game master (GM).
   * @param ttsCheckBox The checkbox for enabling text-to-speech functionality.
   * @param gmArrowGif The arrow gif for the GM.
   */
  public void addInitialLabels(
      Label timerLabel,
      Label hintLabel,
      TextArea textArea,
      TextField textField,
      ImageView gmSprite,
      CheckBox ttsCheckBox,
      ImageView gmArrowGif) {
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
    // add the arrow gif for the gm
    instance.chatManager.addGmArrowGif(gmArrowGif);
  }
}
