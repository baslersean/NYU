package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * Provides stopwatch objects for timing tasks. The stopwatch objects support 
 * the typical operations of a physical stopwatch: start, stop, restart, and the 
 * recording of laps (times intervals). Also, a stopwatch can be asked for a 
 * list of all the lap times that have been recorded using that stopwatch (if 
 * the stopwatch has just been started and stopped once, then the list is of 
 * size one and contains the elapsed time).
 * @author baslersean
 *
 */
public class Stopwatch implements IStopwatch {
  private String id;
  private boolean isRunning = false;
  private List<Long> lapTimes = new ArrayList<Long>();
  private Long mostRecentLapTime;
  
  /**
   * Constructor for stopwatch that sets the id
   * @param id of the stopwatch
   */
  public Stopwatch(String id) {
    this.id = id;
  }
  
  /**
   * Public getter for id
   * @return the id of this stopwatch
   */
  @Override
  public String getId() {
    return this.id;
  }
  
  /**
   * Starts the stopwatch if it isn't running.  Creates the first lap.  Throws
   * an IllegalStateException if already running.
   */
  @Override
  public void start() {
    synchronized(this) {
      if (this.isRunning) {
        throw new IllegalStateException("This stopwatch is ALREADY running");
      }
      this.isRunning = true;
      this.mostRecentLapTime = System.currentTimeMillis();
    }
  }
  
  /**
   * Marks a lap and saves the lap time.  Throws an IllegalStateException if
   * not running.
   */
  @Override
  public void lap() {
    synchronized(this) {
      if (!this.isRunning) {
        throw new IllegalStateException("This stopwatch is NOT running");
      }
      lapTimes.add(System.currentTimeMillis() - mostRecentLapTime);
      mostRecentLapTime = System.currentTimeMillis();
    }
  }
  
  /**
   * Stops the stopwatch if it is running.  Adds a final lap.  Throws an
   * IllegalStateException if not running.
   */
  @Override
  public void stop() {
    synchronized(this) {
      if (!this.isRunning) {
        throw new IllegalStateException("This stopwatch is NOT running");
      }
      this.isRunning = false;
      lapTimes.add(System.currentTimeMillis() - mostRecentLapTime);
      mostRecentLapTime = System.currentTimeMillis();
    }
  }

  /**
   * Stops the stopwatch if it's running and resets it.
   */
  @Override
  public void reset() {
    if (this.isRunning) {
      this.isRunning = false;
    }
    synchronized (this.lapTimes) {
      lapTimes.clear();
    }
    
  }
  
  /**
   * Returns the lap times.
   */
  @Override
  public List<Long> getLapTimes() {
    return lapTimes;
  }
  
  /**
   * No need to override equals() or hashCode since the object versions function
   * for this implementation
   */
  
  /**
   * @return String representing the stopwatch
   */
  @Override
  public String toString() {
    String str = "Stopwatch id: " + this.id +
        "\nNumber of laps: " + this.lapTimes.size() +
        "\nTimes for each lap: " + this.lapTimes;
    return str;
  }
}
