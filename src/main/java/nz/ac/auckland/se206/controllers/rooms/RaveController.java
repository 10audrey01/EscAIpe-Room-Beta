package nz.ac.auckland.se206.controllers.rooms;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class RaveController {
  @FXML private Rectangle classicalDoor;
  @FXML private Rectangle rockDoor;
  @FXML private ImageView posterBtn;
  @FXML private ImageView djBtn;
  @FXML private ImageView bodybuilderBtn;
  @FXML private ImageView bouncerBtn;
  @FXML private ImageView discoBtn;
  @FXML private ImageView speakerBtn;

  @FXML
  private void initialize() {}

  @FXML
  private void onPosterEnter() throws IOException {
    Image currentImage =
        new Image(App.class.getResource("/images/raveRoom/posterSel.png").openStream());
    Platform.runLater(
        () -> {
          posterBtn.setImage(currentImage);
        });
  }

  @FXML
  private void onPosterExit() throws IOException {
    Image currentImage =
        new Image(App.class.getResource("/images/raveRoom/poster.png").openStream());
    Platform.runLater(
        () -> {
          posterBtn.setImage(currentImage);
        });
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
}
