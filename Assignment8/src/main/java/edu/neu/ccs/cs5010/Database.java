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

  public String getResult(Query query) {
    Query.QueryType queryType = query.getType();
    int key = query.getKey();
    switch (queryType) {
      case SKIER_SUMMARY:
        return getQuery1Result(key);
      case SKIER_RIDE_DETAILS:
        return getQuery2Result(key);
      case BUSY_LIFTS_PER_HOUR:
        return getQuery3Result(key);
      case LIFT_SUMMARY:
        return getQuery4Result(key);
      default:
        throw new IllegalArgumentException("Illegal query type" + queryType.name());
    }
  }

  public void close() {
    liftrides.close();
    lifts.close();
    skiers.close();
    hours.close();
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
    strb.append(", skierId: ").append(skierId).append(", [time, liftId]: ");
    for (RawLiftRidesData rideData : ridesData) {
      strb.append("[").append(rideData.getTime()).append(", ");
      strb.append(rideData.getLiftId()).append("]").append(", ");
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
