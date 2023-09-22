package nz.ac.auckland.se206;

import javafx.scene.Node;

// class for handling draggable components in the gui
public class DraggableMaker {
  private double mouseAnchorX;
  private double mouseAnchorY;

  // function for making a node draggable by the user
  public void makeDraggable(Node node) {

    // get the current co-ordinates on click
    node.setOnMousePressed(
        (mouseEvent) -> {
          mouseAnchorX = mouseEvent.getX();
          mouseAnchorY = mouseEvent.getY();
        });

    // as the user drags, update the co-ordinates to the cursor or where the user drags to
    node.setOnMouseDragged(
        (mouseEvent) -> {
          node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
          node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
  }
}
