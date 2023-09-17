package nz.ac.auckland.se206.controllers.rooms.classical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class HarpController {
  @FXML private Line string1;
  @FXML private Line string2;
  @FXML private Line string3;
  @FXML private Line string4;
  @FXML private Line string5;
  @FXML private Line string6;
  @FXML private Line string7;
  @FXML private Line string8;
  @FXML private Line string9;
  @FXML private Line string10;
  @FXML private Line string11;
  @FXML private Line string12;
  @FXML private Line string13;
  @FXML private Line string14;
  @FXML private Line string15;
  @FXML private Line string16;
  @FXML private Line string17;
  @FXML private Line string18;
  @FXML private Line string19;
  @FXML private Line string20;
  @FXML private Line string21;
  @FXML private Line string22;
  @FXML private Line string23;
  @FXML private Line string24;
  @FXML private Line string25;
  @FXML private Line string26;
  @FXML private Line string27;
  @FXML private Line string28;
  @FXML private Line string29;
  @FXML private Line string30;
  @FXML private Line string31;
  @FXML private Line string32;
  @FXML private Line string33;
  @FXML private Line string34;
  @FXML private Pane leaveHarp;

  private ArrayList<Line> strings;
  private ArrayList<Line> currentNotesPlayed;

  @FXML
  private void initialize() throws IOException {
    strings =
        new ArrayList<Line>(
            List.of(
                string1, string2, string3, string4, string5, string6, string7, string8, string9,
                string10, string11, string12, string13, string14, string15, string16, string17,
                string18, string19, string20, string21, string22, string23, string24, string25,
                string26, string27, string28, string29, string30, string31, string32, string33,
                string34));
    currentNotesPlayed = new ArrayList<Line>();
    for (Line string : strings) {
      string.setOnMouseEntered(
          e -> {
            currentNotesPlayed.add(string);
            System.out.println(string.getId() + " entered");
          });
    }
  }

  @FXML
  public void onClickedLeaveHarp(MouseEvent event) throws IOException {
    System.out.println("Leave Harp Clicked");
    Pane current = (Pane) event.getSource();
    Scene currentScene = current.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CLASSICAL));
  }
}
