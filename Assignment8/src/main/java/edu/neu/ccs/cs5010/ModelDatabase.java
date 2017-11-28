package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.*;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelDatabase {
  private static final Logger LOGGER
          = Logger.getLogger(ModelDatabase.class.getName());

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
    for (int i = 0; i < MAX_DB_EXCLUSIVE_LOCKS; i++) {
      this.dbLockPool[i] = new ReentrantReadWriteLock();
    }
    this.queriesPerformed = new AtomicInteger(0);
  }

  public String performQuery(Query query) {
    Query.QueryType queryType = query.getType();
    int key = query.getKey();
    int queryNum = queriesPerformed.incrementAndGet();
    LOGGER.log(Level.FINE, "Performing query: " + queryNum + " of type "
            + queryType.name() + ", " + "key: " + key);
    switch (queryType) {
      case SKIER_SUMMARY:
        return getSkierSummary(key);
      case SKIER_RIDE_DETAILS:
        return getSkierRideDetails(key);
      case BUSY_LIFTS_PER_HOUR:
        return getBusyLiftsPerHour(key);
      case LIFT_SUMMARY: {
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
    SkierData skierData;
    int skierLockId = getHash(skierId) % MAX_DB_EXCLUSIVE_LOCKS;
    Lock skierIdLock = dbLockPool[skierLockId].writeLock();
    // first get the lock for skierId to work on
    skierIdLock.lock();
    // then get the skierDataModel from the pool
    SkierDataModel skierDataModel = this.skiersModelPool.requestModel();
    try {
      skierData = skierDataModel.getDataInfo(skierId);
      skierData.incNumViews();
      skierDataModel.updateDataInfo(skierId, skierData);
    } finally {
      // return the skierDataModel back to the pool
      this.skiersModelPool.returnModel(skierDataModel);
      // release the lock
      skierIdLock.unlock();
    }
    return "queryId: 1"
            + ", skierId: " + skierData.getSkierId()
            + ", numRides: " + skierData.getNumRides()
            + ", numVertical: " + skierData.getTotalVertical()
            + ", numViews: " + skierData.getNumViews();
  }

  private String getSkierRideDetails(int skierId) {
    // get model from pool
    RawLiftRidesDataModel rawLiftRidesDataModel = this.rawLiftRidesModelPool.requestModel();
    List<RawLiftRidesData> ridesData = rawLiftRidesDataModel.getDataListInfo(skierId);
    // return model back to pool
    this.rawLiftRidesModelPool.returnModel(rawLiftRidesDataModel);
    ridesData.sort(Comparator.comparing(obj -> ((Integer) obj.getTime())));
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
  private AtomicInteger queriesPerformed;
}
