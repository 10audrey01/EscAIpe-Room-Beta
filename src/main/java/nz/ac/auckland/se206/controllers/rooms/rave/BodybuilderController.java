package nz.ac.auckland.se206.controllers.rooms.rave;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RavePuzzle;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class BodybuilderController {

  @FXML private TextArea speechBox;
  @FXML private ImageView firstRoomHint;
  @FXML private ImageView secondRoomHint;
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
  @FXML private Text codeInputText;
  @FXML private Circle digitOne;
  @FXML private Circle digitTwo;
  @FXML private Circle digitThree;
  @FXML private Circle digitFour;
  @FXML private Circle digitFive;
  @FXML private Circle digitSix;
  @FXML private Label timerLabel;

  private GameState gamestate;
  private RavePuzzle puzzleInstance;
  private String code = "";
  private String solution = "";

  @FXML
  private void initialize() throws IOException {
    System.out.println("Start");
    this.gamestate = GameState.getInstance();
    this.gamestate.timeManager.addToTimers(timerLabel);
    this.gamestate.bodybuilderController = this;
    this.speechBox.setText("Hey bro, I need your help... I need to open this safe.");
    resetSafe();
  }

  public void initialiseCode() throws IOException {
    RavePuzzle puzzle = gamestate.ravePuzzle;
    this.puzzleInstance = puzzle;
    this.solution = puzzleInstance.getSolution();
    this.puzzleInstance.setImages(firstRoomHint, secondRoomHint);
    System.out.println("Solution added to controller." + this.solution);
  }

  public void resetSafe() throws IOException {
    this.code = "";
    this.solution = "";

    digitOne.setFill(Color.RED);
    digitTwo.setFill(Color.RED);
    digitThree.setFill(Color.RED);
    digitFour.setFill(Color.RED);
    digitFive.setFill(Color.RED);
    digitSix.setFill(Color.RED);
  }

  // Handles the event where any number is pressed.
  @FXML
  private void pressNumber(int number) {
    if (this.code.length() < 6) {
      this.code += Integer.toString(number);
      codeInputText.setText(code);
    }
  }

  // ons clicking of the 0
  @FXML
  private void onClickZero(ActionEvent action) {
    pressNumber(0);
  }

  // ons clicking of the 1
  @FXML
  private void onClickOne(ActionEvent action) {
    pressNumber(1);
  }

  // ons clicking of the 2
  @FXML
  private void onClickTwo(ActionEvent action) {
    pressNumber(2);
  }

  // ons clicking of the 3
  @FXML
  private void onClickThree(ActionEvent action) {
    pressNumber(3);
  }

  // ons clicking of the 4
  @FXML
  private void onClickFour(ActionEvent action) {
    pressNumber(4);
  }

  // ons clicking of the 5
  @FXML
  private void onClickFive(ActionEvent action) {
    pressNumber(5);
  }

  // ons clicking of the 6
  @FXML
  private void onClickSix(ActionEvent action) {
    pressNumber(6);
  }

  // ons clicking of the 7
  @FXML
  private void onClickSeven(ActionEvent action) {
    pressNumber(7);
  }

  // ons clicking of the 8
  @FXML
  private void onClickEight(ActionEvent action) {
    pressNumber(8);
  }

  // ons clicking of the 9
  @FXML
  private void onClickNine(ActionEvent action) {
    pressNumber(9);
  }

  // ons clicking of the delete
  @FXML
  private void onClickRemove(ActionEvent action) {
    if (this.code.length() > 0) {
      this.code = this.code.substring(0, this.code.length() - 1);
    }
    codeInputText.setText(code);
  }

  // ons clicking of the submit
  // checks for the correct numbers at each relative digit to green or red based on the correctness
  // of the input.
  // this way hinting for the player the correctness of the inputs that they
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

    if (this.code.equals(this.solution)) {
      speechBox.setText("Nice work bro. You can take this key if you want, I guess");
      GameState.isSafeOpened = true;
      gamestate.objectiveListManager.strikeThroughLabel2();
    }
  }

  // switches back to the rave room
  @FXML
  private void onClickReturn(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  @FXML
  private void onKeyPressed() {
    System.out.println("key pressed");
  }
}
