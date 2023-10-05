package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

/**
 * The SceneManager class provides functionality for managing different screens and controllers in a
 * JavaFX application. It uses enums to represent various application UI screens, stores UI roots
 * and controllers in HashMaps, and provides methods to add, retrieve, and manage these components.
 */
public class SceneManager {
  /** Enum to represent different application UI screens. */
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

  /**
   * Adds a UI root for a specific AppUi to the SceneManager.
   *
   * @param appUi The AppUi enum representing the UI screen.
   * @param uiRoot The JavaFX Parent object representing the root of the UI screen.
   */
  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  /**
   * Retrieves the UI root for a specific AppUi from the SceneManager.
   *
   * @param appUi The AppUi enum representing the UI screen.
   * @return The JavaFX Parent object representing the root of the UI screen.
   */
  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi);
  }

  /**
   * Adds a controller for a specific AppUi to the SceneManager.
   *
   * @param appUi The AppUi enum representing the UI screen.
   * @param controller The controller object associated with the UI screen.
   */
  public static void addController(AppUi appUi, Object controller) {
    controllerMap.put(appUi, controller);
  }

  /**
   * Retrieves the controller for a specific AppUi from the SceneManager.
   *
   * @param appUi The AppUi enum representing the UI screen.
   * @return The controller object associated with the UI screen.
   */
  public static Object getController(AppUi appUi) {
    return controllerMap.get(appUi);
  }
}
