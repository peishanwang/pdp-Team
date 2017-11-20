package edu.neu.ccs.cs5010;

import java.util.*;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class Resort implements IResort {

  Map<Integer, ISkier> idToSkier;
  List<ILift> liftList;

  public Resort() {
    idToSkier = new HashMap<>();
    liftList = new ArrayList<>();
    for (int i = 0; i < 40; i++) {
      liftList.add(new Lift(i + 1));
    }
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

  public List<ILift> getLiftList() {
    return liftList;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Resort resort = (Resort) obj;

    return idToSkier.equals(resort.idToSkier)
        && getLiftList().equals(resort.getLiftList());
  }

  @Override
  public int hashCode() {
    int result = idToSkier.hashCode();
    result = 31 * result + getLiftList().hashCode();
    return result;
  }
}
