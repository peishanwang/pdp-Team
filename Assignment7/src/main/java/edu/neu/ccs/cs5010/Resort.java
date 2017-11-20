package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of Resort containing all skier and lifts' ride information in this resort.
 */
public class Resort implements IResort {

  private static final int NUM_LIFTS = 40;
  private Map<Integer, ISkier> idToSkier;
  private List<ILift> liftList;

  /**
   * Constructor of the Resort.
   */
  public Resort() {
    idToSkier = new HashMap<>();
    liftList = new ArrayList<>();
    for (int i = 0; i < NUM_LIFTS; i++) {
      liftList.add(new Lift(i + 1));
    }
  }

  @Override
  public void addSkierVertical(int skierId, int vertical) {
    if (!idToSkier.containsKey(skierId)) {
      idToSkier.put(skierId, new Skier(skierId));
    }
    idToSkier.get(skierId).addVertical(vertical);
  }

  @Override
  public List<ISkier> getTopSkier(int numberOfSkiers) {
    List<ISkier> allSkiers = new ArrayList<>();
    for (Map.Entry<Integer, ISkier> entry: idToSkier.entrySet()) {
      allSkiers.add(entry.getValue());
    }
    Collections.sort(allSkiers);
    return allSkiers.subList(0, numberOfSkiers);
  }

  @Override
  public void addLiftRide(int liftId) {
    liftList.get(liftId - 1).addRide();
  }

  @Override
  public void addLiftRideWithHourIndex(int liftId, int hourIndex) {
    liftList.get(liftId - 1).addRideWithHourIndex(hourIndex);
  }

  @Override
  public List<ILift> getLiftList() {
    return liftList;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Resort resort = (Resort) obj;

    return idToSkier.keySet().equals(resort.idToSkier.keySet())
        && getLiftList().equals(resort.getLiftList());
  }

  @Override
  public int hashCode() {
    int result = idToSkier.keySet().hashCode();
    result = 31 * result + getLiftList().hashCode();
    return result;
  }
}
