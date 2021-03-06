package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentsystem.SkiHelper;


/**
 * This is the consumer class for sequential solution.
 *
 */
public class SequentialRideInfoConsumer implements IRideInfoConsumer {
  private static final int MIN_PER_HOUR = 60;
  private static final String OUTPUT_FILE1 = "skier2.csv";
  private static final String OUTPUT_FILE2 = "lift2.csv";
  private static final String OUTPUT_FILE3 = "hour2.csv";
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
    writer.write(OUTPUT_FILE1, extracter.extractResult1());
    writer.write(OUTPUT_FILE2, extracter.extractResult2());
    writer.write(OUTPUT_FILE3, extracter.extractResult3());
  }
}
