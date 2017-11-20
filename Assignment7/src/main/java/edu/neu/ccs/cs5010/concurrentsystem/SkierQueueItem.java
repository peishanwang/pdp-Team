package edu.neu.ccs.cs5010.concurrentsystem;


/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class SkierQueueItem implements ISkierQueueItem {

  public SkierQueueItem(int skierId, int liftId) {
    this.skierId = skierId;
    this.liftId = liftId;
  }

  public Integer getSkierId() {
    return skierId;
  }

  public Integer getLiftId() {
    return liftId;
  }

  private Integer skierId;
  private Integer liftId;
}
