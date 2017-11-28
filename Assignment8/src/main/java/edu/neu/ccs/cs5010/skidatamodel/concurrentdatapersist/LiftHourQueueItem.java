package edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist;


import java.util.Objects;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class LiftHourQueueItem implements ILiftHourQueueItem{
  public LiftHourQueueItem(int min, int liftId) {
    this.min = min;
    this.liftId = liftId;
  }

  /**
   * method to get hour.
   * @return hour
   */
  @Override
  public Integer getHour() {
    int hour = (min - 1) / 60;
    return hour + 1;
  }

  /**
   * method to get liftid.
   * @return id of lift
   */
  @Override
  public Integer getLiftId() {
    return liftId;
  }

  private Integer min;
  private Integer liftId;

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    LiftHourQueueItem that = (LiftHourQueueItem) object;
    return Objects.equals(min, that.min)
            && Objects.equals(liftId, that.liftId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(min, liftId);
  }
}