package edu.neu.ccs.cs5010.generateDatFile;


import edu.neu.ccs.cs5010.exceptions.IllegalRideInfoBuilderException;

/**
 * This is a class to build RideInfo.
 */
public class RideInfoBuilder implements IRideInfoBuilder{

  private RideInfo rideInfo;

  public RideInfoBuilder() {
    rideInfo = new RideInfo();
  }

  @Override
  public RideInfoBuilder setResortId(int resortId) {
    if (resortId < 0) {
      throw new IllegalRideInfoBuilderException("Resort id invalid");
    }
    rideInfo.resortId = resortId;
    return this;
  }

  @Override
  public RideInfoBuilder setDay(int day) {
    if (day < 1 || day > 365 ) {
      throw new IllegalRideInfoBuilderException("Day invalid");
    }
    rideInfo.day = day;
    return this;
  }

  @Override
  public RideInfoBuilder setSkier(int skierId) {
    if (skierId < 0) {
      throw new IllegalRideInfoBuilderException("SkierId invalid");
    }
    rideInfo.skierId = skierId;
    return this;
  }

  @Override
  public RideInfoBuilder setLiftId(int liftId) {
    if (liftId < 1 || liftId > 40) {
      throw new IllegalRideInfoBuilderException("LiftId Invalid");
    }
    rideInfo.liftId = liftId;
    return this;
  }

  @Override
  public RideInfoBuilder setTime(int time) {
    if (time < 0 || time > 360) {
      throw new IllegalRideInfoBuilderException("Time Invalid");
    }
    rideInfo.time = time;
    return this;
  }

  @Override
  public RideInfo build() {
    return rideInfo;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    RideInfoBuilder that = (RideInfoBuilder) obj;

    return rideInfo.equals(that.rideInfo);
  }

  @Override
  public int hashCode() {
    return rideInfo.hashCode();
  }
}
