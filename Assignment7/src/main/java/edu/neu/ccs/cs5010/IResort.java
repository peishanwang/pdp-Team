package edu.neu.ccs.cs5010;

import java.util.List;

public interface IResort {
  void addSkierVertical(int skierId, int vertical);

  List<ISkier> getTopSkier(int numberOfSkiers);

  void addLiftRide(int liftId);

  void addLiftRideWithTime(int liftId, int timeIndex);

  List<ILift> getLiftList();

}
