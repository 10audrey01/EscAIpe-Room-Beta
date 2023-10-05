package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * The TimeManager class provides functionality for managing timers and countdowns in a JavaFX
 * application. It allows setting and starting timers, updating timer labels in the user interface,
 * and stopping timers when necessary.
 */
public class TimeManager {
  private Timer timer;
  private ArrayList<Label> timers;
  private int time;
  private boolean timerStarted;
  private Thread timerThread;

  /**
   * Constructs a TimeManager object with an empty list of timers and sets the timerStarted flag to
   * false.
   */
  public TimeManager() {
    this.timers = new ArrayList<Label>();
    this.timerStarted = false;
  }

  /**
   * Sets the current time for the countdown timer.
   *
   * @param time The time in seconds to set for the countdown timer.
   */
  public void setTime(int time) {
    this.time = time;
  }

  /**
   * Adds a Label to the list of timers for updating their display values.
   *
   * @param label The Label to be added to the list of timers.
   */
  public void addToTimers(Label label) {
    timers.add(label);
  }

  /**
   * Starts the countdown timer. If the timer has already started, this method does nothing.
   * Otherwise, it initiates the timer and begins decrementing the time.
   */
  public void startCountdown() {
    // will not start the countdown if it has already started.
    if (this.timerStarted) {
      return;
    }

    timer = new Timer();
    System.out.println("timer started");
    updateTime();
    this.timerStarted = true;

    // task for the update of the timer, decrements timer every second and updates GUI, aswell as
    // validating for any events that may have to be run.
    Task<Void> timerTask =
        new Task<Void>() {

          @Override
          // A task for the continuous decrementing of the timer, and updates all timer
          // value indicators for the GUI.
          protected Void call() throws Exception {
            timer.scheduleAtFixedRate(
                new TimerTask() {
                  @Override
                  public void run() {
                    if (time > 0) {
                      time--;
                      updateTime();
                    } else {
                      stopCountdown();
                      try {
                        App.setRoot("end");
                      } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                      }
                    }
                  }
                },
                1000,
                1000);
            return null;
          }
        };

    Thread timerThread = new Thread(timerTask, "TimerThread");
    timerThread.start();
  }

  /** Updates all the timer labels in the UI to display the current time in minutes and seconds. */
  private void updateTime() {
    // get the time in minutes / seconds
    int minutes = time / 60;
    int seconds = time % 60;
    // update all the timer labels in the UI to have the correct time
    Platform.runLater(
        () -> {
          for (Label label : timers) {
            label.setText(String.format("%01d:%02d", minutes, seconds));
          }
        });
  }

  /** Stops the countdown timer and the associated timer thread, if they are active. */
  public void stopCountdown() {
    if (timer != null) {
      timer.cancel();
    }

    if (timerThread != null) {
      timerThread.interrupt();
    }

    this.timerStarted = false;
    System.out.println("timer stopped");
  }
}
