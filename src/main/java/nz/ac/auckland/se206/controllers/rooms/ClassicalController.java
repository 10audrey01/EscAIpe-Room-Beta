package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class ClassicalController {

  private static Timeline timeline;
  private static final Integer START_TIME_MIN = GameState.time.getTime();
  private static final Integer START_TIME_SEC = 00;

  public static void playTimer() {
    timeline.play();
  }

  public static void stopTimer() {
    timeline.stop();
  }

  @FXML private Rectangle raveDoor;
  @FXML private Rectangle rockDoor;
  @FXML private Pane celloPane;
  @FXML private Pane celloBowPane;
  @FXML private Pane grandPianoPane;
  @FXML private Pane clarinetPane;
  @FXML private Pane harpPane;
  @FXML private Pane tambourinePane;
  @FXML private Pane trumpetPane;
  @FXML private Label timerMinLabel;
  @FXML private Label timerSecLabel;

  private Integer timeMinutes = START_TIME_MIN;
  private Integer timeSeconds = START_TIME_SEC;

  @FXML
  private void initialize() {
    startTimer();
  }

  @FXML
  private void doGoRave(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.RAVE));
  }

  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
  }

  @FXML
  private void doClickedCello(MouseEvent event) throws IOException {
    System.out.println("Cello Clicked");
  }

  @FXML
  private void doClickedCelloBow(MouseEvent event) throws IOException {
    System.out.println("Cello Bow Clicked");
  }

  @FXML
  private void doClickedClarinet(MouseEvent event) throws IOException {
    System.out.println("Clarinet Clicked");
  }

  @FXML
  private void doClickedGrandPiano(MouseEvent event) throws IOException {
    System.out.println("Grand Piano Clicked");
  }

  @FXML
  private void doClickedTrumpet(MouseEvent event) throws IOException {
    System.out.println("Trumpet Clicked");
  }

  @FXML
  private void doClickedHarp(MouseEvent event) throws IOException {
    System.out.println("Harp Clicked");
  }

  @FXML
  private void doClickedTambourine(MouseEvent event) throws IOException {
    System.out.println("Tambourine Clicked");
  }

  public void startTimer() {
    timerMinLabel.setText(timeMinutes.toString());
    timerSecLabel.setText(": 00");
    timeline = new Timeline(); // create a timeline for the timer
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline
        .getKeyFrames()
        .add(
            new KeyFrame(
                Duration.seconds(1), // handler is called every second
                new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent event) {
                    timeSeconds--;
                    if (timeSeconds < 0
                        && timeMinutes > 0) { // decrement minutes if seconds reach 0
                      timeMinutes--;
                      timeSeconds = 59;
                    }
                    timerMinLabel.setText(timeMinutes.toString());
                    if (timeSeconds < 10) {
                      timerSecLabel.setText(": 0" + timeSeconds.toString()); // aesthetic purposes
                    } else {
                      timerSecLabel.setText(": " + timeSeconds.toString());
                    }
                    if (timeMinutes <= 0 && timeSeconds <= 0) {
                      timeline.stop();
                      try {
                        App.setRoot("end"); // go to end page if time runs out
                      } catch (IOException e) {
                        e.printStackTrace();
                      }
                    }
                  }
                }));
  }
}
