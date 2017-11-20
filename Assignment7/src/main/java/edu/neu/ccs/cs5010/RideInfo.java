package edu.neu.ccs.cs5010;

public class RideInfo {

  public RideInfo() { }

  public int getResortId() {
    return resortId;
  }

  public int getDay() {
    return day;
  }

  public int getSkierId() {
    return skierId;
  }

  public int getLiftId() {
    return liftId;
  }

  public int getTime() {
    return time;
  }

  int resortId;
  int day; // day of ride from 1 to 365
  int skierId;
  int liftId; // liftId from 1 to 40
  int time; // time in minutes from 0 to 360
}
