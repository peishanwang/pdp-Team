package edu.neu.ccs.cs5010.concurrentski;

public class LiftHourQueueItem {
  public LiftHourQueueItem(int min, int liftId) {
    this.min = min;
    this.liftId = liftId;
  }

  public Integer getTimeMin() {
    return min;
  }

  public Integer getHour() {
    int hour = (min / 60);
    // starts from 1
    if (hour < 6) {hour += 1;}
    return hour;
  }

  public Integer getLiftId() {
    return liftId;
  }

  private Integer min;
  private Integer liftId;
}