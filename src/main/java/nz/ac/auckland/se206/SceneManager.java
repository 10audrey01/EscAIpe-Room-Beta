package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {
  // Enum to represent different application UI screens
  public enum AppUi {
    START,
    CLASSICAL,
    RAVE,
    ROCK,
    PIANO,
    BODYBUILDER,
    HARP,
    TRUMPET,
    CLASSICALNOTE,
    ROCKNOTE,
    MUSICQUIZ,
    GUITARIST
  }

  // HashMap to store UI roots for each AppUi
  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();

  // HashMap to store controllers for each AppUi
  private static HashMap<AppUi, Object> controllerMap = new HashMap<AppUi, Object>();

  // Method to add a UI root for a specific AppUi
  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  // Method to get the UI root for a specific AppUi
  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi);
  }

  // Method to add a controller for a specific AppUi
  public static void addController(AppUi appUi, Object controller) {
    controllerMap.put(appUi, controller);
  }

  // Method to get the controller for a specific AppUi
  public static Object getController(AppUi appUi) {
    return controllerMap.get(appUi);
  }
}
