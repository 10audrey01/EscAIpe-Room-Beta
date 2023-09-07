package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class RockController {

  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle raveDoor;
  @FXML private Pane gameMasterPane;
  @FXML private Pane drumsPane;
  @FXML private Pane cyanGuitarPane;
  @FXML private Pane blueGuitarPane;
  @FXML private Pane purpleGuitarPane;
  @FXML private Pane yellowGuitarPane;
  @FXML private Pane amplifierPane;
  @FXML private Label timerLabel;

  @FXML
  private void initialize() {}

  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  @FXML
  private void onClickGameMaster(MouseEvent event) {
    System.out.println("game master clicked");
  }

  @FXML
  private void onClickDrums(MouseEvent event) {
    System.out.println("drums clicked");
  }

  @FXML
  private void onClickCyanGuitar(MouseEvent event) {
    System.out.println("cyan guitar clicked");
  }

  @FXML
  private void onClickBlueGuitar(MouseEvent event) {
    System.out.println("blue guitar clicked");
  }

  @FXML
  private void onClickPurpleGuitar(MouseEvent event) {
    System.out.println("purple guitar clicked");
  }

  @FXML
  private void onClickYellowGuitar(MouseEvent event) {
    System.out.println("yellow guitar clicked");
  }

  @FXML
  private void onClickAmplifier(MouseEvent event) {
    System.out.println("amplifier clicked");
  }
}
