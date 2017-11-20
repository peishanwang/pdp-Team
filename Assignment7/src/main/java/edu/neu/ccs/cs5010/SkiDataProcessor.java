package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentsystem.ParallelRideInfoConsumer;



/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class SkiDataProcessor implements ISkiDataProcessor {
  private static final String SEQUENTIAL = "Sequential";
  private static final String CONCURRENT = "Concurrent";
  private String path;
  private boolean processParallely;

  public SkiDataProcessor(String path, boolean processParallely) {
    this.path = path;
    this.processParallely = processParallely;
  }

  public static void main(String[] args) {
    ICmdParser cmdParser = new CmdParser(args);
    boolean processParallely = cmdParser.getFlag().equalsIgnoreCase(CONCURRENT);
    ISkiDataProcessor processor = new SkiDataProcessor(cmdParser.getCsvFile(), processParallely);
    long startTime = System.currentTimeMillis();
    processor.processData();
    long endTime = System.currentTimeMillis();
    System.out.println("Time taken for " + (processParallely ? CONCURRENT : SEQUENTIAL) +
            " : " + String.valueOf(endTime - startTime) + " ms");
  }

  public void processData() {
    InfoParser infoParser = new InfoParser();
    IRideInfoConsumer rideDataConsumer;
    if (this.processParallely) {
      rideDataConsumer = new ParallelRideInfoConsumer();
    } else {
      rideDataConsumer = new SequentialRideInfoConsumer();
    }
    infoParser.parseInfo(this.path, rideDataConsumer);
  }
}