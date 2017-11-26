package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import java.util.logging.Logger;


/**
 * Main class to process the SkiData.
 *
 */
public class SkiDataProcessor implements ISkiDataProcessor {
  public static final String SKI_DATA_MODEL_BASE_PATH = "D:\\pdp_team_assignments\\Assignment8";
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
    ISkiDataProcessor processor = new SkiDataProcessor
            (SKI_DATA_MODEL_BASE_PATH + "\\PDPAssignment.csv");
    long startTime = System.currentTimeMillis();
    processor.processData();
    long endTime = System.currentTimeMillis();
    long timeTaken = endTime - startTime;
    String str = String.format("Time taken for %1$s : %2$d ms",
        "Parallel",
        timeTaken);
    LOGGER.info(str);
  }

  @Override
  public void processData() {
    IInfoParser infoParser = new InfoParser();
    IRideInfoConsumer rideDataConsumer;
    rideDataConsumer = new ParallelRideInfoConsumer();
    infoParser.parseInfo(this.path, rideDataConsumer);
  }
}