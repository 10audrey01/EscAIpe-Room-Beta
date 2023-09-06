package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class StartController {
  //       <RadioButton fx:id="easyBtn" layoutX="162.0" layoutY="99.0" mnemonicParsing="false"
  // onMouseClicked="#onClickEasy" text="Easy" />
  //       <RadioButton fx:id="mediumBtn" layoutX="256.0" layoutY="99.0" mnemonicParsing="false"
  // text="Medium" />
  //       <RadioButton fx:id="hardBtn" layoutX="373.0" layoutY="99.0" mnemonicParsing="false"
  // text="Hard" />
  //       <RadioButton fx:id="twoMinsBtn" layoutX="162.0" layoutY="234.0" mnemonicParsing="false"
  // onMouseClicked="#onClickTwo" text="2 mins" />
  //       <RadioButton fx:id="fourMinsBtn" layoutX="275.0" layoutY="234.0" mnemonicParsing="false"
  // onMouseClicked="#onClickFour" text="4 mins" />
  //       <RadioButton fx:id="sixMinsBtn" layoutX="383.0" layoutY="234.0" mnemonicParsing="false"
  // onMouseClicked="#onClickSix" text="6 mins" />
  //       <Button fx:id="startGameBtn" layoutX="259.0" layoutY="333.0" mnemonicParsing="false"
  // onAction="#onGameStart" text="Start Game!" />
  @FXML private RadioButton easyBtn;
  @FXML private RadioButton mediumBtn;
  @FXML private RadioButton hardBtn;
  @FXML private RadioButton twoMinsBtn;
  @FXML private RadioButton fourMinsBtn;
  @FXML private RadioButton sixMinsBtn;
  @FXML private Button startGameBtn;

  @FXML
  private void initialize() {}

  private void resetDifficulty() {
    easyBtn.selectedProperty().set(false);
    mediumBtn.selectedProperty().set(false);
    hardBtn.selectedProperty().set(false);
  }

  @FXML
  private void onClickEasy() {}

  @FXML
  private void onClickMedium() {}

  @FXML
  private void onClickHard() {}

  @FXML
  private void onClickTwo() {}

  @FXML
  private void onClickFour() {}

  @FXML
  private void onClickSix() {}

  @FXML
  private void onGameStart() {}
}
