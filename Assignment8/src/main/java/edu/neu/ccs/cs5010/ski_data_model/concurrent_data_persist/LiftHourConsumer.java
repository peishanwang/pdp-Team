package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import edu.neu.ccs.cs5010.ski_data_model.DataSourceOpenMode;
import edu.neu.ccs.cs5010.ski_data_model.HourRideData;
import edu.neu.ccs.cs5010.ski_data_model.HourRidesDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class LiftHourConsumer implements Consumer<LiftHourQueueItem> {
  private ConcurrentMap<Integer, ConcurrentMap<Integer, AtomicInteger>> hourNumLiftRidesMap;
  private AtomicBoolean finished;
  private final int numBusiestLifts = 10;

  /**
   * LiftHourConsumer constructor.
   */
  public LiftHourConsumer() {
    hourNumLiftRidesMap =
            new ConcurrentSkipListMap<>();
    finished = new AtomicBoolean(false);
  }

  /**
   * method overridden by Consumer interface.
   * @param liftHourQueueItem item of lift along with hours
   */
  @Override
  public void accept(LiftHourQueueItem liftHourQueueItem) {
    if (liftHourQueueItem == null) {
      // this is signal from one of threads that queue is empty now
      if (finished.compareAndSet(false, true)) {
        // write file
        HourRidesDataModel hourRidesDataModel = new HourRidesDataModel(SkiDataProcessor
                .SKI_DATA_MODEL_BASE_PATH, DataSourceOpenMode.CREATE_MODEL);
        for (Map.Entry<Integer, ConcurrentMap<Integer, AtomicInteger>> hourLiftNumRidesEntry :
                hourNumLiftRidesMap.entrySet()) {
          PriorityQueue<LiftWithRides> busiestLifts =
                  new PriorityQueue<>(40,
                      (obj1, obj2) -> obj2.getNumRides() - obj1.getNumRides());
          getBusiestLifts(hourLiftNumRidesEntry.getValue(), busiestLifts);
          HourRideData data = getBusiestLiftsAlongWithRides(busiestLifts, hourLiftNumRidesEntry);
          hourRidesDataModel.addDataInfo(data);
        }
        hourRidesDataModel.close();
      }
      return;
    }
    int hour = liftHourQueueItem.getHour();
    hourNumLiftRidesMap.putIfAbsent(hour, new ConcurrentHashMap<>());
    hourNumLiftRidesMap.get(hour).putIfAbsent(
            liftHourQueueItem.getLiftId(), new AtomicInteger(0));
    hourNumLiftRidesMap.get(hour).get(liftHourQueueItem.getLiftId()).incrementAndGet();
  }

  /**
   * method to get busiest lifts.
   * @param hourLiftNumRides for a given hour
   * @param busiestLifts priority queue to store info to
   */
  private void getBusiestLifts(ConcurrentMap<Integer, AtomicInteger> hourLiftNumRides,
                               PriorityQueue<LiftWithRides> busiestLifts) {
    for (Map.Entry<Integer, AtomicInteger> liftNumRidesEntry : hourLiftNumRides.entrySet()) {
      busiestLifts.add(new LiftWithRides(
              liftNumRidesEntry.getKey(), liftNumRidesEntry.getValue().get()));
    }
  }

  /**
   * method to get busiest lifts along with hour.
   * @param busiestLifts priority queue of top lifts
   * @param hourLiftNumRidesEntry one entry of map.
   */
  private HourRideData getBusiestLiftsAlongWithRides(PriorityQueue<LiftWithRides> busiestLifts,
                            Map.Entry<Integer, ConcurrentMap<Integer, AtomicInteger>>
                                                     hourLiftNumRidesEntry) {
    int hour = hourLiftNumRidesEntry.getKey();
    int liftCount = 0;
    int[] rides = new int[numBusiestLifts];
    while (liftCount < numBusiestLifts) {
      LiftWithRides liftWithRides = busiestLifts.poll();
      // pri queue is min queue so add into rides from back
      rides[numBusiestLifts - liftCount - 1] = liftWithRides.getLiftId();
      liftCount++;
    }
    return HourRideData.constructHourData(hour, rides);
  }
}
