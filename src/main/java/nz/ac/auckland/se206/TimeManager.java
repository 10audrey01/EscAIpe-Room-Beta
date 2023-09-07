package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class TimeManager {
  private Timer timer;
  private ArrayList<Label> timers;
  private int time;
  private boolean timerStarted;

  public TimeManager() {
    this.timers = new ArrayList<Label>();
    this.timerStarted = false;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public void addToTimers(Label label) {
    timers.add(label);
  }

  public void startCountdown() {
    // will not start the countdown if it has already started.
    if (this.timerStarted) {
      return;
    }

    timer = new Timer();
    System.out.println("timer started");
    timers.get(0).setText("hello");
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
                    time--;
                    int minutes = time / 60;
                    int seconds = time % 60;
                    Platform.runLater(
                        () -> {
                          for (Label label : timers)
                            label.setText(
                                Integer.toString(minutes) + ":" + Integer.toString(seconds));
                        });
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
}
