package nz.ac.auckland.se206;

import java.io.IOException;
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
  private Thread timerThread;

  public TimeManager() {
    this.timers = new ArrayList<Label>();
    this.timerStarted = false;
  }

  // sets the current time
  public void setTime(int time) {
    this.time = time;
  }

  // add a label to list of timers
  public void addToTimers(Label label) {
    timers.add(label);
  }

  // function that starts the countdown of the timer
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

  // function that updates all the timer labels for displaying time
  private void updateTime() {
    int minutes = time / 60;
    int seconds = time % 60;
    Platform.runLater(
        () -> {
          for (Label label : timers) {
            label.setText(String.format("%01d:%02d", minutes, seconds));
          }
        });
  }

  // function that stops the timer and the thread.
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
