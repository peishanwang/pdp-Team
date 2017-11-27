package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;


import java.util.Objects;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class SkierQueueItem implements ISkierQueueItem {

  /**
   * SkierQueueItem Consumer.
   * @param skierId id of skier
   * @param liftId id of lift
   * @param timeStamp of ride
   */
  public SkierQueueItem(int skierId, int liftId, int timeStamp) {
    this.skierId = skierId;
    this.liftId = liftId;
    this.timestamp = timeStamp;
  }

  /**
   * method to get skier id.
   * @return id of skier
   */
  public Integer getSkierId() {
    return skierId;
  }

  /**
   * method to get lift id.
   * @return id of lift
   */
  public Integer getLiftId() {
    return liftId;
  }

  @Override
  public Integer getTs() {
    return timestamp;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    SkierQueueItem that = (SkierQueueItem) object;
    return Objects.equals(skierId, that.skierId)
            && Objects.equals(liftId, that.liftId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(skierId, liftId);
  }

  private Integer skierId;
  private Integer liftId;
  private Integer timestamp;
}
