package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentsystem.ParallelRideInfoConsumer;

import java.util.logging.Logger;


/**
 * Main class to process the SkiData.
 *
 */
public class SkiDataProcessor implements ISkiDataProcessor {
  private static final String SEQUENTIAL = "Sequential";
  private static final String CONCURRENT = "Concurrent";
  private String path;
  private boolean processParallely;
  private static final Logger LOGGER
      = Logger.getLogger(SkiDataProcessor.class.getName());

  /**
   * Constructor of SkiDataProcessor.
   * @param path input .csv file path
   * @param processParallely true if using concurrent solution.
   *                         false if using sequential solution.
   */
  public SkiDataProcessor(String path, boolean processParallely) {
    this.path = path;
    this.processParallely = processParallely;

  }

  /**
   * Main method of the processor.
   * @param args input arguments
   */
  public static void main(String[] args) {
    ICmdParser cmdParser = new CmdParser(args);
    boolean processParallely = cmdParser.getFlag().equalsIgnoreCase(CONCURRENT);
    ISkiDataProcessor processor = new SkiDataProcessor(cmdParser.getCsvFile(), processParallely);
    long startTime = System.currentTimeMillis();
    processor.processData();
    long endTime = System.currentTimeMillis();
    long timeTaken = endTime - startTime;
    LOGGER.info("Time taken for " + (processParallely ? CONCURRENT : SEQUENTIAL)
    +":" + timeTaken + "ms");
  }

  @Override
  public void processData() {
    IInfoParser infoParser = new InfoParser();
    IRideInfoConsumer rideDataConsumer;
    if (this.processParallely) {
      rideDataConsumer = new ParallelRideInfoConsumer();
    } else {
      rideDataConsumer = new SequentialRideInfoConsumer();
    }
    infoParser.parseInfo(this.path, rideDataConsumer);
  }
}