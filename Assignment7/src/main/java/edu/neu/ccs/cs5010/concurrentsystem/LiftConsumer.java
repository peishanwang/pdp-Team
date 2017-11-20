package edu.neu.ccs.cs5010.concurrentsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class LiftConsumer implements Consumer<Integer> {
  private ConcurrentMap<Integer, AtomicInteger> liftNumRidesMap;
  private AtomicBoolean finished;
  private String liftCsvFile = "lifts.csv";
  edu.neu.ccs.cs5010.ResultWriter fileWriter;


  public LiftConsumer() {
    liftNumRidesMap = new ConcurrentSkipListMap<>();
    finished = new AtomicBoolean(false);
    fileWriter = new edu.neu.ccs.cs5010.ResultWriter();
  }

  @Override
  public void accept(Integer liftId) {
    if (liftId == null) {
      // this is signal from one of threads that queue is empty now
      if (finished.compareAndSet(false, true)) {
        // write file
        List<Object[]> liftCountData = new ArrayList<>();
        liftCountData.add(new String[]{"LiftID", "Number of Rides"});
        for (Map.Entry<Integer, AtomicInteger> liftNumRidesEntry :
                liftNumRidesMap.entrySet()) {
          liftCountData.add(new String[]{
                  String.valueOf(
                          liftNumRidesEntry.getKey()),
                  String.valueOf(liftNumRidesEntry.getValue())});
        }
        fileWriter.write(liftCsvFile, liftCountData);
      }
      return;
    }
    liftNumRidesMap.putIfAbsent(liftId, new AtomicInteger(0));
    liftNumRidesMap.get(liftId).incrementAndGet();
  }
}
