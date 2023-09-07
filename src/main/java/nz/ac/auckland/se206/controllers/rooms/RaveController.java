package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class RaveController {

  private static Timeline timeline;
  private static final Integer START_TIME_MIN = GameState.time.getTime();
  private static final Integer START_TIME_SEC = 00;

  public static void playTimer() {
    timeline.play();
  }

  public static void stopTimer() {
    timeline.stop();
  }

  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle rockDoor;
  @FXML private Pane posterPane;
  @FXML private Pane djPane;
  @FXML private Pane bodybuilderPane;
  @FXML private Pane bouncerPane;
  @FXML private Pane discoPane;
  @FXML private Pane speakerPane;
  @FXML private ImageView doorImage;
  @FXML private ImageView greenLock;
  @FXML private ImageView redLock;
  @FXML private ImageView blueLock;
  @FXML private ImageView yellowLock;
  @FXML private Label timerMinLabel;
  @FXML private Label timerSecLabel;

  private Integer timeMinutes = START_TIME_MIN;
  private Integer timeSeconds = START_TIME_SEC;

  @FXML
  private void initialize() {
    startTimer();
  }

  @FXML
  private void onClickPoster(MouseEvent event) {
    System.out.println("poster clicked");
  }

  @FXML
  private void onClickDj(MouseEvent event) {
    System.out.println("dj clicked");
  }

  @FXML
  private void onClickBodybuilder(MouseEvent event) {
    System.out.println("bodybuilder clicked");
  }

  @FXML
  private void onClickBouncer(MouseEvent event) {
    System.out.println("bouncer clicked");
  }

  @FXML
  private void onClickDisco(MouseEvent event) {
    System.out.println("disco clicked");
  }

  @FXML
  private void onClickSpeaker(MouseEvent event) {
    System.out.println("speaker clicked");
  }

  @FXML
  private void onClickRed(MouseEvent event) {
    System.out.println("red clicked");
  }

  @FXML
  private void onClickGreen(MouseEvent event) {
    System.out.println("green clicked");
  }

  @FXML
  private void onClickBlue(MouseEvent event) {
    System.out.println("blue clicked");
  }

  @FXML
  private void onClickYellow(MouseEvent event) {
    System.out.println("yellow clicked");
  }

  @FXML
  private void onClickDoor(MouseEvent event) {
    System.out.println("door clicked");
  }

  @FXML
  private void doGoClassical(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }

  @FXML
  private void doGoRock(MouseEvent event) throws IOException {
    Rectangle current = (Rectangle) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROCK));
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
