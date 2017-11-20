package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This is a class containing the methods to extract question answer from Resort.
 *
 */
public class ResultExtracter implements IResultExtracter {
  private static final int SKIER_NUM = 100;
  private static final int LIFT_NUM = 10;
  private static final String SKIER_ID = "SkierID";
  private static final String VERTICAL = "Vertical";
  private static final String LIFT_ID = "LiftID";
  private static final String HOUR = "hour";
  private static final String NUM_RIDES = "Number of Rides";

  private IResort resort;

  /**
   * Constructor of ResultExtracter.
   * @param resort resort
   */
  public ResultExtracter(IResort resort) {
    this.resort = resort;
  }

  @Override
  public List<Object[]> extractResult1() {
    List<ISkier> topHundredSkier = resort.getTopSkier(SKIER_NUM);
    List<Object[]> result = new ArrayList<>();
    result.add(new String[]{SKIER_ID, VERTICAL});
    for (int i = 0; i < SKIER_NUM; i++) {
      ISkier currSkier = topHundredSkier.get(i);
      result.add(new String[]{Integer.toString(currSkier.getSkierId()),
          Integer.toString(currSkier.getTotalVertical())});
//      System.out.println(Integer.toString(currSkier.getSkierId()) + "," +
//          Integer.toString(currSkier.getTotalVertical()));
    }
    return result;
  }

  @Override
  public List<Object[]> extractResult2() {
    List<ILift> allLifts = resort.getLiftList();
    List<Object[]> result = new ArrayList<>();
    result.add(new String[]{LIFT_ID, NUM_RIDES});
    for (ILift currLift : allLifts) {
      result.add(new String[]{Integer.toString(currLift.getLiftId()),
          Integer.toString(currLift.getTotalRides())});
    }
    return result;
  }

  private int hourIndex;

  @Override
  public List<Object[]> extractResult3() {
    List<ILift> allLifts = resort.getLiftList();
    List<Object[]> result = new ArrayList<>();

    for (hourIndex = 0; hourIndex < 6; hourIndex++) {
      result.add(new String[]{HOUR + (hourIndex + 1)});
      result.add(new String[]{LIFT_ID, NUM_RIDES});
      Collections.sort(allLifts, (lift1, lift2) -> {
        if (lift1.getHourRides(hourIndex) == lift2.getHourRides(hourIndex)) {
          return lift1.getLiftId() - lift2.getLiftId();
        } else {
          return lift2.getHourRides(hourIndex) - lift1.getHourRides(hourIndex);
        }
      });
      for (int i = 0; i < LIFT_NUM; i++) {
        ILift currLift = allLifts.get(i);
        result.add(new String[]{Integer.toString(currLift.getLiftId()),
            Integer.toString(currLift.getHourRides(hourIndex))});
      }
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    ResultExtracter that = (ResultExtracter) obj;

    return resort.equals(that.resort);
  }

  @Override
  public int hashCode() {
    int result = resort.hashCode();
    result = 31 * result + hourIndex;
    return result;
  }
}
