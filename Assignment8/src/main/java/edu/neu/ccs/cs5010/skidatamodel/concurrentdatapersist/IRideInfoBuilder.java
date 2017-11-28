package edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist;

/**
 * This is part of PDP Assignment 8.
 *
 * @author Manika and Peishan
 */

public interface IRideInfoBuilder {

  RideInfoBuilder setResortId(int resortId);

  RideInfoBuilder setDay(int day);

  RideInfoBuilder setSkier(int skierId);

  RideInfoBuilder setLiftId(int liftId);

  RideInfoBuilder setTime(int time);

  RideInfo build();
}
