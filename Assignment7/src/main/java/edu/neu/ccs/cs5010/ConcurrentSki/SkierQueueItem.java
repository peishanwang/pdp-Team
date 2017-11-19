package edu.neu.ccs.cs5010.ConcurrentSki;

public class SkierQueueItem {
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
