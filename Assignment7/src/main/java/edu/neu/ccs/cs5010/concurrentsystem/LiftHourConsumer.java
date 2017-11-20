package edu.neu.ccs.cs5010.concurrentsystem;

import java.util.ArrayList;
import java.util.Comparator;
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
  private int numBusiestLifts = 10;
  private String hourCsvFile = "hours.csv";
  edu.neu.ccs.cs5010.ResultWriter fileWriter;

  public LiftHourConsumer(){
    hourNumLiftRidesMap =
            new ConcurrentSkipListMap<>();
    finished = new AtomicBoolean(false);
    fileWriter = new edu.neu.ccs.cs5010.ResultWriter();
  }

  @Override
  public void accept(LiftHourQueueItem liftHourQueueItem) {
    if (liftHourQueueItem == null) {
      // this is signal from one of threads that queue is empty now
      if (finished.compareAndSet(false, true)) {
        // write file
        List<Object[]> liftHourInformation = new ArrayList<>();
        liftHourInformation.add(new String[]{"Hour", "LiftID", "Number of Rides"});
        for (Map.Entry<Integer, ConcurrentMap<Integer, AtomicInteger>> hourLiftNumRidesEntry :
                hourNumLiftRidesMap.entrySet()) {
          PriorityQueue<LiftWithRides> busiestLifts =
                  new PriorityQueue<>(numBusiestLifts,
                          Comparator.comparingInt(obj -> obj.getNumRides()));
          for (Map.Entry<Integer, AtomicInteger> liftNumRidesEntry :
                  hourLiftNumRidesEntry.getValue().entrySet()) {
            if (busiestLifts.size() >= numBusiestLifts) {
              if (busiestLifts.peek().getNumRides() > liftNumRidesEntry.getValue().get()) {
                continue;
              } else {
                busiestLifts.poll();
              }
            }
            busiestLifts.add(new LiftWithRides(
                    liftNumRidesEntry.getKey(), liftNumRidesEntry.getValue().get()));
          }
          while (!busiestLifts.isEmpty()) {
            LiftWithRides liftWithRides = busiestLifts.poll();
            liftHourInformation.add(new String[]
                    {String.valueOf(hourLiftNumRidesEntry.getKey()),
                            String.valueOf(liftWithRides.getLiftId()),
                            String.valueOf(liftWithRides.getNumRides())
                    });
          }
        }
        fileWriter.write(hourCsvFile, liftHourInformation);
      }
      return;
    }
    int hour = liftHourQueueItem.getHour();
    hourNumLiftRidesMap.putIfAbsent(hour, new ConcurrentHashMap<>());
    hourNumLiftRidesMap.get(hour).putIfAbsent(
            liftHourQueueItem.getLiftId(), new AtomicInteger(0));
    hourNumLiftRidesMap.get(hour).get(liftHourQueueItem.getLiftId()).incrementAndGet();
  }

}
