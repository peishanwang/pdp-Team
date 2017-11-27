package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.*;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ModelDatabase {

  public ModelDatabase() {
    this.rawLiftRidesModelPool = new DataModelPool<>(
            MAX_MODEL_POOL_SIZE, () -> new RawLiftRidesDataModel(BASE_PATH));
    this.skiersModelPool = new DataModelPool<>(
            MAX_MODEL_POOL_SIZE, () -> new SkierDataModel(BASE_PATH));
    this.liftsModelPool = new DataModelPool<>(
            MAX_MODEL_POOL_SIZE, () -> new LiftDataModel(BASE_PATH));
    this.hoursModelPool = new DataModelPool<>(
            MAX_MODEL_POOL_SIZE, () -> new HourRidesDataModel(BASE_PATH));
    this.dbLockPool = new ReentrantReadWriteLock[MAX_DB_EXCLUSIVE_LOCKS];
  }

  public String performQuery(Query query) {
    Query.QueryType queryType = query.getType();
    int key = query.getKey();
    switch (queryType) {
      case SKIER_SUMMARY:
        return getSkierSummary(key);
      case SKIER_RIDE_DETAILS:
        return getSkierRideDetails(key);
      case BUSY_LIFTS_PER_HOUR:
        return getBusyLiftsPerHour(key);
      case LIFT_SUMMARY: {
        LiftDataModel liftModel = this.liftsModelPool.requestModel();
        return getLiftSummary(key);
      }
      default:
        throw new IllegalArgumentException("Illegal query type" + queryType.name());
    }
  }

  public void close() {
    this.rawLiftRidesModelPool.close();
    this.skiersModelPool.close();
    this.liftsModelPool.close();
    this.hoursModelPool.close();
  }

  private static int getHash(int hashCode) {
    hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
    return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
  }

  private String getSkierSummary(int skierId) {
    // get lock for skierId by its hash from dbLockPool
    int skierLockHash = getHash(skierId) % MAX_DB_EXCLUSIVE_LOCKS;
    ReentrantReadWriteLock readWriteLock = dbLockPool[skierLockHash];
    readWriteLock.writeLock().lock();
    SkierData skierData;
    try {
      // now get the skierDataModel from the pool
      SkierDataModel skierDataModel = this.skiersModelPool.requestModel();
      skierData = skierDataModel.getDataInfo(skierId);
      skierData.incNumViews();
      skierDataModel.updateDataInfo(skierId, skierData);
      // return the skierDataModel back to the pool
      this.skiersModelPool.returnModel(skierDataModel);
    } finally {
      readWriteLock.writeLock().unlock();
    }
    return "queryId: 1"
            + ", skierId: " + skierData.getSkierId()
            + ", numRides: " + skierData.getNumRides()
            + ", numVertical: " + skierData.getTotalVertical()
            + ", numViews: " + skierData.getNumViews();
  }

  private String getSkierRideDetails(int skierId) {
    RawLiftRidesDataModel rawLiftRidesDataModel = this.rawLiftRidesModelPool.requestModel();
    List<RawLiftRidesData> ridesData = rawLiftRidesDataModel.getDataListInfo(skierId);
    this.rawLiftRidesModelPool.returnModel(rawLiftRidesDataModel);
    ridesData.sort(Comparator.comparing(o -> ((Integer) o.getTime())));
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

  private String getBusyLiftsPerHour(int hourId) {
    HourRidesDataModel hourModel = this.hoursModelPool.requestModel();
    HourRideData rideData = hourModel.getDataInfo(hourId - 1);
    this.hoursModelPool.returnModel(hourModel);
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

  private String getLiftSummary(int liftId) {
    LiftDataModel liftModel = this.liftsModelPool.requestModel();
    LiftData liftData = liftModel.getDataInfo(liftId);
    this.liftsModelPool.returnModel(liftModel);
    return "queryId: 4"
            + ", liftId: " + liftData.getLiftId()
            + ", numRides: " + liftData.getNumRides();
  }


  private static final String BASE_PATH = ".";
  private static final int MAX_MODEL_POOL_SIZE = 5;
  private static final int MAX_DB_EXCLUSIVE_LOCKS = 5;
  private final IDataModelPool<RawLiftRidesDataModel> rawLiftRidesModelPool;
  private final IDataModelPool<SkierDataModel> skiersModelPool;
  private final IDataModelPool<LiftDataModel> liftsModelPool;
  private final IDataModelPool<HourRidesDataModel> hoursModelPool;
  private final ReentrantReadWriteLock[] dbLockPool;
}