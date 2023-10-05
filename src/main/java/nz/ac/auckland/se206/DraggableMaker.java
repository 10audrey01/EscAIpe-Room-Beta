package nz.ac.auckland.se206;

import javafx.scene.Node;

/** Utility class for making JavaFX nodes draggable within the GUI. */
public class DraggableMaker {
  private double mouseAnchorX;
  private double mouseAnchorY;

  /**
   * Makes a JavaFX node draggable by the user.
   *
   * @param node The JavaFX node to be made draggable.
   */
  public void makeDraggable(Node node) {

    // Get the current coordinates on click
    node.setOnMousePressed(
        (mouseEvent) -> {
          mouseAnchorX = mouseEvent.getX();
          mouseAnchorY = mouseEvent.getY();
        });

    // As the user drags, update the coordinates to the cursor or where the user drags to
    node.setOnMouseDragged(
        (mouseEvent) -> {
          node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
          node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
  }
}
