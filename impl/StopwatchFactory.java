package edu.nyu.pqs.stopwatch.impl;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

import java.util.ArrayList;
import java.util.List;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  private static List<IStopwatch> stopwatchList = new ArrayList<IStopwatch>();
  private static Object lock = new Object();
  
	/**
	 * Creates and returns a new IStopwatch object
	 * @param id The identifier of the new object
	 * @return The new IStopwatch object
	 * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
	 */
	public static IStopwatch getStopwatch(String id) {
	  synchronized (lock) {
  	  if (id.equals("") || id == null) {
  	    throw new IllegalArgumentException(id + " this id is empty or null");    
  	  }
  	  
  	  IStopwatch stopwatch = new Stopwatch(id);
  	  
  	  if (stopwatchList.contains(stopwatch)) {
  	    throw new IllegalArgumentException(id + " this id is already taken");
  	  }
      stopwatchList.add(stopwatch);
      return stopwatch;
	  }
	}
	
	/**
	 * Returns a list of all created stopwatches
	 * @return a List of all created IStopwatch objects.  Returns an empty
	 * list if no IStopwatches have been created.
	 */
	public static List<IStopwatch> getStopwatches() {
    return stopwatchList; 
  }
}
