package edu.neu.ccs.cs5010;

/**
 * RideInfo containing the information of a ride.
 */
public class RideInfo implements IRideInfo{
  int resortId;
  int day; // day of ride from 1 to 365
  int skierId;
  int liftId; // liftId from 1 to 40
  int time; // time in minutes from 0 to 360

  /**
   * Constructor of RideInfo.
   */
  public RideInfo() { }

  @Override
  public int getResortId() {
    return resortId;
  }

  @Override
  public int getDay() {
    return day;
  }

  @Override
  public int getSkierId() {
    return skierId;
  }

  @Override
  public int getLiftId() {
    return liftId;
  }

  @Override
  public int getTime() {
    return time;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    RideInfo rideInfo = (RideInfo) obj;

    return getResortId() == rideInfo.getResortId()
        && getDay() == rideInfo.getDay()
        && getSkierId() == rideInfo.getSkierId()
        && getLiftId() == rideInfo.getLiftId()
        && getTime() == rideInfo.getTime();
  }

  @Override
  public int hashCode() {
    int result = getResortId();
    result = 31 * result + getDay();
    result = 31 * result + getSkierId();
    result = 31 * result + getLiftId();
    result = 31 * result + getTime();
    return result;
  }
}
