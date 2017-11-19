package edu.neu.ccs.cs5010;

import java.util.*;

public class Resort implements IResort {

  Map<Integer, ISkier> idToSkier;
  List<ILift> liftList;

  public Resort() {
    idToSkier = new HashMap<>();
    liftList = new ArrayList<>();
  }

  public void addSkierVertical(int skierId, int vertical) {
    if (!idToSkier.containsKey(skierId)) {
      idToSkier.put(skierId, new Skier(skierId));
    }
    idToSkier.get(skierId).addVertical(vertical);
  }

  public List<ISkier> getTopSkier(int numberOfSkiers) {
    List<ISkier> allSkiers = new ArrayList<>();
    for (int id : idToSkier.keySet()) {
      allSkiers.add(idToSkier.get(id));
    }
    Collections.sort(allSkiers);
    return allSkiers.subList(0, numberOfSkiers);
  }

  public void addLiftRide(int liftId) {
    liftList.get(liftId - 1).addRide();
  }

  public void addLiftRideWithTime(int liftId, int timeIndex) {
    liftList.get(liftId - 1).addRideWithTime(timeIndex);
  }
}
