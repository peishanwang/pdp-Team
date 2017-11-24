package edu.neu.ccs.cs5010.generateDatFile;


import java.io.IOException;

/**
 * This is the consumer class for sequential solution.
 *
 */
public class SequentialRideInfoConsumer implements IRideInfoConsumer {
  private static final int MIN_PER_HOUR = 60;
  private static final String OUTPUT_FILE2 = "skier.dat";
  private static final String OUTPUT_FILE3 = "lifts.dat";
  private static final String OUTPUT_FILE4 = "hour2.dat";
  private IResort resort;

  /**
   * Constructor of SequentialRideInfoConsumer.
   */
  public SequentialRideInfoConsumer() {
    this.resort = new Resort();
  }

  @Override
  public void accept(RideInfo rideInfo) {
    resort.addSkierVertical(rideInfo.getSkierId(),
            SkiHelper.getVerticalDistanceMetres(rideInfo.getLiftId()));
    resort.addLiftRide(rideInfo.getLiftId());
    resort.addLiftRideWithHourIndex(rideInfo.getLiftId(),
        (rideInfo.getTime() - 1) / MIN_PER_HOUR);
  }

  @Override
  public void stop() {
    IResultWriter writer = new ResultWriter();
    IResultExtracter extracter = new ResultExtracter(resort);
    try {
      DatFileGenerator.writeToRandomAccessFile(extracter.extractResult1(), OUTPUT_FILE2);
      DatFileGenerator.writeToRandomAccessFile(extracter.extractResult2(), OUTPUT_FILE3);
      DatFileGenerator.writeToRandomAccessFile(extracter.extractResult3(), OUTPUT_FILE4);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
