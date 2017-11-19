package edu.neu.ccs.cs5010.concurrentski;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ConcurrentSki {
  private int numConsumerThreads = 1;
  BlockingQueue<SkierQueueItem> skierQueue;
  ConsumerExecutor<SkierQueueItem> skierConsumer;

  BlockingQueue<Integer> liftQueue;
  ConsumerExecutor<Integer> liftConsumer;

  BlockingQueue<LiftHourQueueItem> liftHourQueue;
  ConsumerExecutor<LiftHourQueueItem> liftHourConsumer;
  edu.neu.ccs.cs5010.ResultWriter fileWriter = new edu.neu.ccs.cs5010.ResultWriter();

  public ConcurrentSki() {
    skierQueue = new LinkedBlockingDeque<>();
    liftQueue = new LinkedBlockingDeque<>();
    liftHourQueue = new LinkedBlockingDeque<>();

    Consumer<SkierQueueItem> skierConsumerFx = new Consumer<SkierQueueItem>() {
      private ConcurrentMap<Integer, AtomicInteger> skierVerticalRideMap =
              new ConcurrentHashMap<>();
      private AtomicBoolean finished = new AtomicBoolean(false);
      private final int topNskiers = 100;
      private final String skierCsvFile = "skier.csv";

      @Override
      public void accept(SkierQueueItem skierQueueItem) {
        if (skierQueueItem == null) {
          // this is signal from one of threads that queue is empty now
          if (finished.compareAndSet(false, true)) {
            // write file
            List<Object[]> skierVerticalInfo = new ArrayList<>();
            skierVerticalInfo.add(new String[]{"SkierId", "Vertical"});
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
    };

    Consumer<Integer> liftConsumerFx = new Consumer<Integer>() {
      private ConcurrentMap<Integer, AtomicInteger> liftNumRidesMap = new ConcurrentSkipListMap<>();
      private AtomicBoolean finished = new AtomicBoolean(false);
      private String liftCsvFile = "lifts.csv";

      @Override
      public void accept(Integer liftId) {
        if (liftId == null) {
          // this is signal from one of threads that queue is empty now
          if (finished.compareAndSet(false, true)) {
            // write file
            List<Object[]> liftCountData = new ArrayList<>();
            liftCountData.add(new String[]{"LiftID", "Number of Rides"});
            int numLiftEntries = 0;
            for (Map.Entry<Integer, AtomicInteger> liftNumRidesEntry :
                    liftNumRidesMap.entrySet()) {
              liftCountData.add(new String[]{
                      String.valueOf(
                              liftNumRidesEntry.getKey()),
                      String.valueOf(liftNumRidesEntry.getValue())});
              numLiftEntries++;
            }
            fileWriter.write(liftCsvFile, liftCountData);
          }
          return;
        }
        liftNumRidesMap.putIfAbsent(liftId, new AtomicInteger(0));
        liftNumRidesMap.get(liftId).incrementAndGet();
      }
    };

    Consumer<LiftHourQueueItem> liftHourConsumerFx = new Consumer<LiftHourQueueItem>() {
      private ConcurrentMap<Integer, ConcurrentMap<Integer, AtomicInteger>> hourNumLiftRidesMap =
              new ConcurrentSkipListMap<>();
      private AtomicBoolean finished = new AtomicBoolean(false);
      private int numBusiestLifts = 10;
      private String hourCsvFile = "hours.csv";

      @Override
      public void accept(LiftHourQueueItem liftHourQueueItem) {
        if (liftHourQueueItem == null) {
          // this is signal from one of threads that queue is empty now
          if (finished.compareAndSet(false, true)) {
            // write file
            List<Object[]> liftHourInformation = new ArrayList<>();
            liftHourInformation.add(new String[]{"Hour", "LiftID", "Number of Rides"});
            int numHourEntries = 0;
            for (Map.Entry<Integer, ConcurrentMap<Integer, AtomicInteger>> hourLiftNumRidesEntry :
                    hourNumLiftRidesMap.entrySet()) {
              numHourEntries++;
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
              int numLiftsRemaining = busiestLifts.size();
              while (!busiestLifts.isEmpty()) {
                LiftWithRides liftWithRides = busiestLifts.poll();
                numLiftsRemaining--;
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
    };

    skierConsumer = new ConsumerExecutor<>(skierQueue, skierConsumerFx, numConsumerThreads);
    skierConsumer.startConsumers();

    liftConsumer = new ConsumerExecutor<>(liftQueue, liftConsumerFx, numConsumerThreads);
    liftConsumer.startConsumers();

    liftHourConsumer = new ConsumerExecutor<>(
            liftHourQueue, liftHourConsumerFx, numConsumerThreads);
    liftHourConsumer.startConsumers();
  }


  public void parseFile(String filePath) throws IOException, InterruptedException {
    FileReader fileReader = new FileReader(filePath);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    String line = bufferedReader.readLine();
    if (line == null) {
      throw new IllegalArgumentException("No header in file");
    }
    String[] headers = line.split(",");
    Map<String, Integer> fieldIndexMap = new HashMap<>();
    for (int idx = 0; idx < headers.length; idx++) {
      fieldIndexMap.put(headers[idx], idx);
    }
    Integer skierIdx = fieldIndexMap.get("skier");
    Integer liftIdx = fieldIndexMap.get("lift");
    Integer timeIdx = fieldIndexMap.get("time");
    if (skierIdx == null || liftIdx == null || timeIdx == null) {
      throw new IllegalArgumentException("missing col skier or lift or time");
    }

    long startTime = System.currentTimeMillis();
    while ((line = bufferedReader.readLine()) != null) {
      String[] cols = line.split(",");
      Integer skierId = Integer.parseInt(cols[skierIdx]);
      Integer liftId = Integer.parseInt(cols[liftIdx]);
      Integer time = Integer.parseInt(cols[timeIdx]);

      skierQueue.put(new SkierQueueItem(skierId, liftId));
      liftQueue.put(liftId);
      liftHourQueue.put(new LiftHourQueueItem(time, liftId));
    }
    // stop for threads to finish
    skierConsumer.join();
    liftConsumer.join();
    liftHourConsumer.join();
    long endTime = System.currentTimeMillis();
    System.out.println("Time taken = " + (endTime - startTime) + " ms");
  }

}
