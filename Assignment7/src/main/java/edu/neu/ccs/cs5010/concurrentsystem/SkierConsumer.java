package edu.neu.ccs.cs5010.concurrentsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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
  private static final String skierCsvFile = "skier.csv";
  private static final int topNskiers = 100;
  private ConcurrentMap<Integer, AtomicInteger> skierVerticalRideMap;
  private AtomicBoolean finished;
  edu.neu.ccs.cs5010.ResultWriter fileWriter;

  public SkierConsumer() {
    skierVerticalRideMap = new ConcurrentHashMap<>();
    finished = new AtomicBoolean(false);
    fileWriter = new edu.neu.ccs.cs5010.ResultWriter();
  }

  @Override
  public void accept(SkierQueueItem skierQueueItem) {
    if (skierQueueItem == null) {
      // this is signal from one of threads that queue is empty now
      if (finished.compareAndSet(false, true)) {
        // write file
        List<Object[]> skierVerticalInfo = new ArrayList<>();
        skierVerticalInfo.add(new String[]{"SkierID", "Vertical"});
        PriorityQueue<SkierWithVertical> topNVerticalSkiers =
                new PriorityQueue<>(
                        topNskiers, Comparator.comparingInt(obj -> obj.getVerticalDistance()));
        for (Map.Entry<Integer, AtomicInteger> entrySet : skierVerticalRideMap.entrySet()) {
          if (topNVerticalSkiers.size() >= topNskiers) {
            if (topNVerticalSkiers.peek().getVerticalDistance() > entrySet.getValue().get()) {
              continue;
            } else {
              topNVerticalSkiers.poll();
            }
          }
          topNVerticalSkiers.add(
                  new SkierWithVertical(entrySet.getKey(), entrySet.getValue().get()));
        }
        List<SkierWithVertical> topSkiers = new ArrayList<>(topNVerticalSkiers.size());
        while (!topNVerticalSkiers.isEmpty()) {
          topSkiers.add(topNVerticalSkiers.poll());
        }

        for (int i = topSkiers.size() - 1; i >= 0; i--) {
          skierVerticalInfo.add(new String[]
          {String.valueOf(topSkiers.get(i).getSkierId()),
                          String.valueOf(topSkiers.get(i).getVerticalDistance())});
        }
        fileWriter.write(skierCsvFile, skierVerticalInfo);
      }
      return;
    }
    skierVerticalRideMap.putIfAbsent(skierQueueItem.getSkierId(), new AtomicInteger(0));
    skierVerticalRideMap.get(skierQueueItem.getSkierId())
            .addAndGet(SkiHelper.getVerticalDistanceMetres(skierQueueItem.getLiftId()));
  }
}
