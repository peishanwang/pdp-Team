package edu.neu.ccs.cs5010.generateDatFile;

import java.util.logging.Logger;


/**
 * Main class to process the SkiData.
 *
 */
public class SkiDataProcessor implements ISkiDataProcessor {
  private String path;
  private static final Logger LOGGER
      = Logger.getLogger(SkiDataProcessor.class.getName());

  /**
   * Constructor of SkiDataProcessor.
   * @param path input .csv file path
   */
  public SkiDataProcessor(String path) {
    this.path = path;
  }

  /**
   * Main method of the processor.
   * @param args input arguments
   */
  public static void main(String[] args) {
    ISkiDataProcessor processor = new SkiDataProcessor("PDPAssignment.csv");
    long startTime = System.currentTimeMillis();
    processor.processData();
    long endTime = System.currentTimeMillis();
    long timeTaken = endTime - startTime;
    String str = String.format("Time taken for %1$s : %2$d ms",
        "Sequential",
        timeTaken);
    LOGGER.info(str);
  }

  @Override
  public void processData() {
    IInfoParser infoParser = new InfoParser();
    IRideInfoConsumer rideDataConsumer;
    rideDataConsumer = new SequentialRideInfoConsumer();
    infoParser.parseInfo(this.path, rideDataConsumer);
  }
}