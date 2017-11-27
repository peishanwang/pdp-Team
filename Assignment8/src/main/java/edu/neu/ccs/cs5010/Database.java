package edu.neu.ccs.cs5010;


import edu.neu.ccs.cs5010.ski_data_model.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
  private static final String PATH = ".";
  private RawLiftRidesDataModel liftrides;
  private SkierDataModel skiers;
  private LiftDataModel lifts;
  private HourRidesDataModel hours;
  private final ReentrantReadWriteLock readWriteLock =
      new ReentrantReadWriteLock();

  public Database() {
    liftrides = new RawLiftRidesDataModel(PATH);
    skiers = new SkierDataModel(PATH);
    lifts = new LiftDataModel(PATH);
    hours = new HourRidesDataModel(PATH);
  }

  public String getResult(int queryId, int parameter) {
    switch (queryId) {
      case 1:
        return getQuery1Result(parameter);
      case 2:
        return getQuery2Result(parameter);
      case 3 :
        return getQuery3Result(parameter);
      case 4 :
        return getQuery4Result(parameter);
      default:
        throw new IllegalArgumentException("Illegal query id" + queryId);
    }
  }

  private String getQuery1Result(int skierId) {
    readWriteLock.writeLock().lock();
    SkierData skierData;
    try {
      skierData = skiers.getDataInfo(skierId);
      skierData.incNumViews();
      skiers.updateDataInfo(skierId, skierData);
    } finally {
      readWriteLock.writeLock().unlock();
    }
    return "queryId: 1"
        + ", skierId: " + skierData.getSkierId()
        + ", numRides: " + skierData.getNumRides()
        + ", numVertical: " + skierData.getTotalVertical()
        + ", numViews: " + skierData.getNumViews();
  }

  private String getQuery2Result(int skierId) {
    List<RawLiftRidesData> ridesData = liftrides.getDataListInfo(skierId);
    Collections.sort(ridesData, Comparator.comparing(o -> ((Integer) o.getTime())));
    StringBuilder strb = new StringBuilder();
    strb.append("queryId: 2");
    strb.append(", skierId: ").append(skierId).append(", liftIds: ");
    for (RawLiftRidesData rideData : ridesData) {
      strb.append(rideData.getLiftId()).append(", ");
    }
    strb.delete(strb.length() - 2, strb.length());
    return strb.toString();
  }

  private String getQuery3Result(int hourId) {
    HourRideData rideData = hours.getDataInfo(hourId - 1);
    if (rideData.getBusyLifts().length != 10) {
      throw new IllegalStateException("invalid lifts");
    }
    StringBuilder strb = new StringBuilder();
    strb.append("queryId: 3");
    strb.append(", hourId: ").append(hourId).append(", liftIds: ");
    for (int liftId : rideData.getBusyLifts()) {
      strb.append(liftId).append(", ");
    }
    strb.delete(strb.length() - 2, strb.length());
    return strb.toString();
  }

  private String getQuery4Result(int liftId) {
    LiftData liftData = lifts.getDataInfo(liftId);
    return "queryId: 4"
        + ", liftId: " + liftData.getLiftId()
        + ", numRides: " + liftData.getNumRides();
  }
}
