package edu.neu.ccs.cs5010.concurrentsystem;

import edu.neu.ccs.cs5010.RideInfo;
import edu.neu.ccs.cs5010.RideInfoBuilder;

/**
 * This is part of PDP Assignment 7.
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