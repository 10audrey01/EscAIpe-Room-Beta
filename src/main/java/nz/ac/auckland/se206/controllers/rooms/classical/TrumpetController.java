package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class TrumpetController {

  @FXML private ImageView trumpetButton1;
  @FXML private ImageView trumpetButton2;
  @FXML private ImageView trumpetButton3;
  @FXML private Pane leaveTrumpet;
  private boolean isButton1Up;
  private boolean isButton2Up;
  private boolean isButton3Up;

  @FXML
  private void initialize() throws IOException {
    isButton1Up = true;
    isButton2Up = true;
    isButton3Up = true;
  }

  @FXML
  private void onClickedTrumpetButton1(MouseEvent event) throws IOException {
    if (isButton1Up) {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton1Down.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton1Up = false;
    } else {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton1Up.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton1Up = true;
    }
  }

  @FXML
  private void onClickedTrumpetButton2(MouseEvent event) throws IOException {
    if (isButton2Up) {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton2Down.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton2Up = false;
    } else {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton2Up.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton2Up = true;
    }
  }

  @FXML
  private void onClickedTrumpetButton3(MouseEvent event) throws IOException {
    if (isButton3Up) {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton3Down.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton3Up = false;
    } else {
      ImageView current = (ImageView) event.getSource();
      Image currentImage =
          new Image(
              App.class
                  .getResource("/images/classicalRoom/Trumpet/TrumpetButton3Up.png")
                  .openStream());
      Platform.runLater(
          () -> {
            current.setImage(currentImage);
          });
      isButton3Up = true;
    }
  }

  @FXML
  public void onClickedLeaveTrumpet(MouseEvent event) throws IOException {
    System.out.println("Leave Trumpet Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
