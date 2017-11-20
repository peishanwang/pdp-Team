package edu.neu.ccs.cs5010.concurrentsystem;


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
   */
  public SkierQueueItem(int skierId, int liftId) {
    this.skierId = skierId;
    this.liftId = liftId;
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
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    SkierQueueItem that = (SkierQueueItem) object;
    return Objects.equals(skierId, that.skierId) &&
            Objects.equals(liftId, that.liftId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(skierId, liftId);
  }

  private Integer skierId;
  private Integer liftId;
}
