package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import edu.neu.ccs.cs5010.ski_data_model.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
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
      rideIds = new ConcurrentLinkedQueue<>();
      totalVertical = new AtomicInteger(0);
    }

    Queue<Integer> rideIds;
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
        // raw data index file
        SkierToRideIndex skierToRideIndex = new SkierToRideIndex(SkiDataProcessor
                .SKI_DATA_MODEL_BASE_PATH, DataSourceOpenMode.CREATE_MODEL);
        for (int i = 1; i <= MAX_SKIERS; i++) {
          SkierRideStats skierRideStats = skierVerticalRideMap.get(i);
          SkierData skierData;
          SkierIndexData skierIndexData = SkierIndexData.constructRawLiftRidesData(i, new int[]{});
          if (skierRideStats == null) {
            skierData = SkierData.constructSkierData(i, 0, 0);
          } else {
            skierData = SkierData.constructSkierData(i, skierRideStats.rideIds.size(),
                    skierRideStats.totalVertical.get());
            for (Integer rideId : skierRideStats.rideIds) {
              skierIndexData.addRide(rideId);
            }
          }
          skierDataModel.addDataInfo(skierData);
          skierToRideIndex.addDataInfo(skierIndexData);
        }
        skierDataModel.close();
        skierToRideIndex.close();
        rawLiftRidesDataModel.close();
      }
      return;
    }
    // add to raw data file
    int rideNum = numRidesConsumed.incrementAndGet();
    rawLiftRidesDataModel.addDataInfo(RawLiftRidesData.constructRawLiftRidesData(rideNum,
            skierQueueItem.getSkierId(), skierQueueItem.getLiftId(), skierQueueItem.getTs()));

    skierVerticalRideMap.putIfAbsent(skierQueueItem.getSkierId(), new SkierRideStats());
    SkierRideStats skierRideStats = skierVerticalRideMap.get(skierQueueItem.getSkierId());
    skierRideStats.rideIds.add(rideNum);
    skierRideStats.totalVertical.addAndGet(SkiHelper.getVerticalDistanceMetres(skierQueueItem
            .getLiftId()));
  }

}
