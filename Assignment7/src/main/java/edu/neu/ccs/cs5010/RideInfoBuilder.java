package edu.neu.ccs.cs5010;


/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class RideInfoBuilder {

  public RideInfoBuilder() {
    rideInfo = new RideInfo();
  }

  public RideInfoBuilder setResortId(int resortId) {
    if (resortId < 0) {
      throw new IllegalArgumentException("");
    }
    rideInfo.resortId = resortId;
    return this;
  }

  public RideInfoBuilder setDay(int day) {
    if (day < 1 || day > 365 ) {
      throw new IllegalArgumentException("");
    }
    rideInfo.day = day;
    return this;
  }

  public RideInfoBuilder setSkier(int skierId) {
    if (skierId < 0) {
      throw new IllegalArgumentException("");
    }
    rideInfo.skierId = skierId;
    return this;
  }

  public RideInfoBuilder setLiftId(int liftId) {
    if (liftId < 1 || liftId > 40) {
      throw new IllegalArgumentException("");
    }
    rideInfo.liftId = liftId;
    return this;
  }

  public RideInfoBuilder setTime(int time) {
    if (time < 0 || time > 360) {
      throw new IllegalArgumentException("");
    }
    rideInfo.time = time;
    return this;
  }

  public RideInfo build() {
    return rideInfo;
  }

  private RideInfo rideInfo;
}
