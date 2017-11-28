package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import java.io.File;
import java.util.logging.Logger;


/**
 * Main class to process the SkiData.
 *
 */
public class SkiDataProcessor implements ISkiDataProcessor {
  public static final String SKI_DATA_FILE = "PDPAssignment.csv";
  public static final String SKI_DATA_MODEL_BASE_PATH = ".";
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
    String defaultPathName = SKI_DATA_MODEL_BASE_PATH + File.separator + SKI_DATA_FILE;
    ISkiDataProcessor processor = new SkiDataProcessor(
            args.length == 0 ? defaultPathName : args[0]);
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