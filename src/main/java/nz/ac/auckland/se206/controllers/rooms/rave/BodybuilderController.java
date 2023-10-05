package nz.ac.auckland.se206.controllers.rooms.rave;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GameState.Difficulty;
import nz.ac.auckland.se206.RavePuzzle;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller class for handling the bodybuilder event room. */
public class BodybuilderController {

  @FXML private TextArea speechBox;
  @FXML private ImageView firstRoomHint;
  @FXML private ImageView secondRoomHint;
  @FXML private ImageView hintImage1;
  @FXML private ImageView hintImage2;
  @FXML private Button btnOne;
  @FXML private Button btnTwo;
  @FXML private Button btnThree;
  @FXML private Button btnFour;
  @FXML private Button btnFive;
  @FXML private Button btnSix;
  @FXML private Button btnSeven;
  @FXML private Button btnEight;
  @FXML private Button btnNine;
  @FXML private Button btnZero;
  @FXML private Button btnRemove;
  @FXML private Button btnSub;
  @FXML private Button btnReturn;
  @FXML private Button btnHint;
  @FXML private Text codeInputText;
  @FXML private Text resultText;
  @FXML private Circle digitOne;
  @FXML private Circle digitTwo;
  @FXML private Circle digitThree;
  @FXML private Circle digitFour;
  @FXML private Circle digitFive;
  @FXML private Circle digitSix;
  @FXML private Label timerLabel;
  @FXML private Label hintLabel;

  // instance of gamestate, puzzle, the code and required solution for finishing the game
  private GameState gameState;
  private RavePuzzle puzzleInstance;
  private String code = "";
  private String solution = "";

  /**
   * Initializes the SafeController by setting up the gamestate, labels, and other game-related
   * variables.
   *
   * @throws IOException If there is an issue with input/output.
   */
  @FXML
  private void initialize() throws IOException {
    // gets the gamestate instance, adding the labels for hints/timers to the gamestate
    this.gameState = GameState.getInstance();
    this.gameState.getTimeManager().addToTimers(timerLabel);
    this.gameState.getHintManager().addHintLabel(hintLabel);
    this.gameState.setBodybuilderController(this);
    this.speechBox.setText("Hey bro, I need your help... I need to open this safe.");
    //
    resetSafe();

    // hide the hint button on hard, as hints are not available
    if (GameState.difficulty == Difficulty.HARD) {
      btnHint.setVisible(false);
    }
  }

  /**
   * Initializes the safe puzzle code, retrieves the solution, and updates UI elements based on the
   * puzzle instance.
   *
   * @throws IOException If there is an issue with input/output.
   */
  public void initialiseCode() throws IOException {
    RavePuzzle puzzle = gameState.getRavePuzzle();
    this.puzzleInstance = puzzle;
    this.solution = puzzleInstance.getSolution();
    this.puzzleInstance.setImages(firstRoomHint, secondRoomHint);
    System.out.println("Solution added to controller." + this.solution);
  }

  /**
   * Resets the status of the safe puzzle to its default state.
   *
   * @throws IOException If there is an issue with input/output.
   */
  public void resetSafe() throws IOException {
    // resets the current solution and code.
    this.code = "";
    this.solution = "";

    // sets the fill of all the indicating lights to red.
    digitOne.setFill(Color.RED);
    digitTwo.setFill(Color.RED);
    digitThree.setFill(Color.RED);
    digitFour.setFill(Color.RED);
    digitFive.setFill(Color.RED);
    digitSix.setFill(Color.RED);
  }

  /**
   * Handles key presses, allowing the player to input numbers (0-9), backspace, and enter for safe
   * code submission.
   *
   * @param event The KeyEvent representing the key press event.
   * @throws ApiProxyException If there is an issue with the API proxy.
   * @throws IOException If there is an issue with input/output.
   */
  @FXML
  private void onKeyPressed(KeyEvent event) throws ApiProxyException, IOException {
    // gets the current value of the key pressed
    KeyCode keyCode = event.getCode();
    // switch case which runs the respective event based on the key pressed.
    switch (keyCode) {
      case BACK_SPACE:
        // backspace runs the remove button
        onClickRemove(new ActionEvent());
        break;
      case ENTER:
        // enter runs the submit button
        onClickSubmit(new ActionEvent());
        break;
      case DIGIT0:
      case NUMPAD0:
        // all other digits/numpads run their respective number
        onClickZero(new ActionEvent());
        break;
      case DIGIT1:
      case NUMPAD1:
        onClickOne(new ActionEvent());
        break;
      case DIGIT2:
      case NUMPAD2:
        onClickTwo(new ActionEvent());
        break;
      case DIGIT3:
      case NUMPAD3:
        onClickThree(new ActionEvent());
        break;
      case DIGIT4:
      case NUMPAD4:
        onClickFour(new ActionEvent());
        break;
      case DIGIT5:
      case NUMPAD5:
        onClickFive(new ActionEvent());
        break;
      case DIGIT6:
      case NUMPAD6:
        onClickSix(new ActionEvent());
        break;
      case DIGIT7:
      case NUMPAD7:
        onClickSeven(new ActionEvent());
        break;
      case DIGIT8:
      case NUMPAD8:
        onClickEight(new ActionEvent());
        break;
      case DIGIT9:
      case NUMPAD9:
        onClickNine(new ActionEvent());
        break;
      default:
        break;
    }
  }

  /**
   * Handles the event when the hint button is clicked. Reveals hint images and consumes a hint if
   * available.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickHint(ActionEvent action) {
    // hide the hint button, one time use
    btnHint.setVisible(false);
    if (gameState.getHintManager().getHintsRemaining() > 0) {
      // show the hint images
      hintImage1.setOpacity(1);
      hintImage2.setOpacity(1);
      this.gameState.getHintManager().useHint();
    } else { // if the player doesnt have hints left, notify them through the bodybuilder
      speechBox.setText("Sorry bro, I don't have any hints for you man.");
    }
  }

  /**
   * Handles pressing a number button (0-9) and adds the number to the input code if the code length
   * is less than 6.
   *
   * @param number The number to be added to the input code.
   */
  @FXML
  private void pressNumber(int number) {
    // only adds the number if the code length is less than 6
    if (this.code.length() < 6) {
      this.code += Integer.toString(number);
      codeInputText.setText(code);
    }
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickZero(ActionEvent action) {
    pressNumber(0);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickOne(ActionEvent action) {
    pressNumber(1);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickTwo(ActionEvent action) {
    pressNumber(2);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickThree(ActionEvent action) {
    pressNumber(3);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickFour(ActionEvent action) {
    pressNumber(4);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickFive(ActionEvent action) {
    pressNumber(5);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickSix(ActionEvent action) {
    pressNumber(6);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickSeven(ActionEvent action) {
    pressNumber(7);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickEight(ActionEvent action) {
    pressNumber(8);
  }

  /**
   * Event handler for clicking respective number button, which add the respective number to the
   * input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickNine(ActionEvent action) {
    pressNumber(9);
  }

  /**
   * Handles the event when the remove (backspace) button is clicked. Removes the last digit from
   * the input code.
   *
   * @param action The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickRemove(ActionEvent action) {
    if (this.code.length() > 0) {
      this.code = this.code.substring(0, this.code.length() - 1);
    }
    codeInputText.setText(code);
  }

  /**
   * Handles the event when the submit button is clicked. Checks the correctness of the input code
   * and updates UI accordingly.
   *
   * @param action The ActionEvent representing the button click event.
   * @throws IOException If there is an issue with input/output.
   */
  @FXML
  private void onClickSubmit(ActionEvent action) throws IOException {
    System.out.println("submitted code " + code);
    if (this.code.length() == 6) {
      for (int i = 0; i < 6; i++) {
        if (code.charAt(i) == (solution.charAt(i))) {
          // switch case for setting correct values to the solution to green.
          switch (i) {
            case 0:
              digitOne.setFill(Color.GREEN);
              break;
            case 1:
              digitTwo.setFill(Color.GREEN);
              break;
            case 2:
              digitThree.setFill(Color.GREEN);
              break;
            case 3:
              digitFour.setFill(Color.GREEN);
              break;
            case 4:
              digitFive.setFill(Color.GREEN);
              break;
            case 5:
              digitSix.setFill(Color.GREEN);
              break;
          }
        } else {
          // switch case for setting incorrect values to red.
          switch (i) {
            case 0:
              digitOne.setFill(Color.RED);
              break;
            case 1:
              digitTwo.setFill(Color.RED);
              break;
            case 2:
              digitThree.setFill(Color.RED);
              break;
            case 3:
              digitFour.setFill(Color.RED);
              break;
            case 4:
              digitFive.setFill(Color.RED);
              break;
            case 5:
              digitSix.setFill(Color.RED);
              break;
          }
        }
      }
    }

    // if the code is correct, send the correct message and notify gamestate of completion of the
    // task.
    if (this.code.equals(this.solution)) {
      speechBox.setText("Nice work bro. You can take this key if you want, I guess");
      resultText.setText("CORRECT (" + code + ")");
      resultText.setFill(Color.GREEN);
      GameState.isSafeOpened = true;
      gameState.getObjectiveListManager().completeObjective2();
      btnHint.setVisible(false);
      return;
    }

    // if the code is incorrect, notify the user through the result text
    resultText.setText("INCORRECT (" + code + ")");
    resultText.setFill(Color.RED);
  }

  /**
   * Switches back to the Rave room scene when the "RETURN" button is clicked.
   *
   * @param event The ActionEvent representing the button click event.
   */
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }
}
