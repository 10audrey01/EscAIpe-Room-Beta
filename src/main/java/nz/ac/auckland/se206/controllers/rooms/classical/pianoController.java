package nz.ac.auckland.se206.controllers.rooms.classical;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class pianoController {

  @FXML private ImageView note1;
  @FXML private ImageView note2;
  @FXML private ImageView note3;
  @FXML private ImageView note4;
  @FXML private ImageView note5;
  @FXML private ImageView note6;
  @FXML private ImageView note7;
  @FXML private ImageView note8;
  @FXML private ImageView note9;
  @FXML private ImageView note10;
  @FXML private ImageView note11;
  @FXML private ImageView note12;
  @FXML private ImageView note13;
  @FXML private ImageView note14;
  @FXML private Pane aKey;
  @FXML private Pane bKey;
  @FXML private Pane cKey;
  @FXML private Pane dKey;
  @FXML private Pane eKey;
  @FXML private Pane fKey;
  @FXML private Pane gKey;

  public ArrayList<ImageView> notesList =
      new ArrayList<ImageView>(
          List.of(
              note1, note2, note3, note4, note5, note6, note7, note8, note9, note10, note11, note12,
              note13, note14));

  // winning squence
  public static String notesToPlay = "";
}
