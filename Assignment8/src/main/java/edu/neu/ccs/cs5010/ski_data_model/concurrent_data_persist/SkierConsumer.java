package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import edu.neu.ccs.cs5010.ski_data_model.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class SkierConsumer implements Consumer<SkierQueueItem> {
  class SkierRideStats {
    SkierRideStats() {
      numRides = new AtomicInteger(0);
      totalVertical = new AtomicInteger(0);
    }
    AtomicInteger numRides;
    AtomicInteger totalVertical;
  }
  private ConcurrentMap<Integer, SkierRideStats> skierVerticalRideMap;
  private static final int MAX_SKIERS = 40000;
  private AtomicBoolean finished;
  private RawLiftRidesDataModel rawLiftRidesDataModel;
  private AtomicInteger numRidesConsumed;
  /**
   * SkierConsumer constructor.
   */
  public SkierConsumer() {
    skierVerticalRideMap = new ConcurrentHashMap<>();
    finished = new AtomicBoolean(false);
    rawLiftRidesDataModel = new RawLiftRidesDataModel(SkiDataProcessor.SKI_DATA_MODEL_BASE_PATH,
            DataSourceOpenMode.CREATE_MODEL);
    numRidesConsumed = new AtomicInteger(0);
  }

  /**
   * method overridden by Consumer Interface.
   * @param skierQueueItem skier with lift info(we can get vertical acc. to lift id)
   */
  @Override
  public void accept(SkierQueueItem skierQueueItem) {
    if (skierQueueItem == null) {
      // this is signal from one of threads that queue is empty now
      if (finished.compareAndSet(false, true)) {
        // write file
        SkierDataModel skierDataModel = new SkierDataModel(SkiDataProcessor
                .SKI_DATA_MODEL_BASE_PATH, DataSourceOpenMode.CREATE_MODEL);
        for (int i = 1; i <= MAX_SKIERS; i++) {
          SkierRideStats skierRideStats = skierVerticalRideMap.get(i);
          SkierData skierData;
          if (skierRideStats == null) {
            skierData = SkierData.constructSkierData(i, 0, 0);
          } else {
            skierData = SkierData.constructSkierData(i, skierRideStats.numRides.get(),
                    skierRideStats.totalVertical.get());
          }
          skierDataModel.addDataInfo(skierData);
        }
        skierDataModel.close();
        rawLiftRidesDataModel.close();
      }
      return;
    }
    skierVerticalRideMap.putIfAbsent(skierQueueItem.getSkierId(), new SkierRideStats());
    SkierRideStats skierRideStats = skierVerticalRideMap.get(skierQueueItem.getSkierId());
    skierRideStats.numRides.incrementAndGet();
    skierRideStats.totalVertical.addAndGet(SkiHelper.getVerticalDistanceMetres(skierQueueItem
                    .getLiftId()));
    // add to raw data file
    synchronized (rawLiftRidesDataModel) {
      int rideNum = numRidesConsumed.incrementAndGet();
      rawLiftRidesDataModel.addDataInfo(RawLiftRidesData.constructRawLiftRidesData(rideNum,
              skierQueueItem.getSkierId(), skierQueueItem.getLiftId(), skierQueueItem.getTs()));
    }
  }

}
