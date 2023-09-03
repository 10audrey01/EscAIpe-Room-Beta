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

public class ClassicalController {

  @FXML private Rectangle raveDoor;
  @FXML private Rectangle rockDoor;
  @FXML private ImageView cello;
  @FXML private ImageView celloBow;
  @FXML private ImageView grandPiano;
  @FXML private ImageView clarinet;
  @FXML private ImageView harp;
  @FXML private ImageView tambourine;
  @FXML private ImageView trumpet;

  @FXML
  private void initialize() {}

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
  private void doClickedCello(MouseEvent event) throws IOException {}

  @FXML
  private void doEnteredCello(MouseEvent event) throws IOException {
    System.out.println("CelloHovered");
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/CelloHovered.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doExitedCello(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/Cello.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doClickedBow(MouseEvent event) throws IOException {}

  @FXML
  private void doEnteredBow(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/CelloBowHovered.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doExitedBow(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/CelloBow.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doClickedClarinet(MouseEvent event) throws IOException {}

  @FXML
  private void doEnteredClarinet(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/ClarinetHovered.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doExitedClarinet(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/Clarinet.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doClickedGrandPiano(MouseEvent event) throws IOException {}

  @FXML
  private void doEnteredGrandPiano(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(
            App.class.getResource("/images/classicalRoom/GrandPianoHovered.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doExitedGrandPiano(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/GrandPiano.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doClickedTrumpet(MouseEvent event) throws IOException {}

  @FXML
  private void doEnteredTrumpet(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/TrumpetHovered.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doExitedTrumpet(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/Trumpet.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doClickedHarp(MouseEvent event) throws IOException {}

  @FXML
  private void doEnteredHarp(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/HarpHovered.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doExitedHarp(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/Harp.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doClickedTambourine(MouseEvent event) throws IOException {}

  @FXML
  private void doEnteredTambourine(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(
            App.class.getResource("/images/classicalRoom/TambourineHovered.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }

  @FXML
  private void doExitedTambourine(MouseEvent event) throws IOException {
    ImageView current = (ImageView) event.getSource();
    Image currentImage =
        new Image(App.class.getResource("/images/classicalRoom/Tambourine.png").openStream());
    Platform.runLater(
        () -> {
          current.setImage(currentImage);
        });
  }
}
