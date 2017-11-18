package edu.neu.ccs.cs5010;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

class SkiHelper {
  static int getVerticalDistanceMetres(int liftId) {
    if (liftId >= 1 && liftId <= 10) {
      return 200;
    } else if (liftId >= 11 && liftId <= 20) {
      return 300;
    } else if (liftId >= 20 && liftId <= 30) {
      return 400;
    } else if (liftId >= 30 && liftId <= 40) {
      return 500;
    } else {
      throw new IllegalArgumentException("invalid liftId: " + liftId);
    }
  }
}

class SkierQueueItem {
  public SkierQueueItem(int skierId, int liftId) {
    this.skierId = skierId;
    this.liftId = liftId;
  }

  public Integer getSkierId() {
    return skierId;
  }

  public Integer getLiftId() {
    return liftId;
  }

  private Integer skierId;
  private Integer liftId;
}

class LiftHourQueueItem {
  public LiftHourQueueItem(int min, int liftId) {
    this.min = min;
    this.liftId = liftId;
  }

  public Integer getTimeMin() {
    return min;
  }

  public Integer getHour() {
    int hour = (min / 60);
    // starts from 1
    if (hour < 6) {hour += 1;}
    return hour;
  }

  public Integer getLiftId() {
    return liftId;
  }

  private Integer min;
  private Integer liftId;
}

class ConsumerThread<T> extends Thread {
  private final BlockingQueue<T> consumerQueue;
  private final Consumer<T> consumerFx;
  private volatile boolean stop;

  public ConsumerThread(final BlockingQueue<T> queue, final Consumer<T> consumerFx) {
    this.consumerQueue = queue;
    this.consumerFx = consumerFx;
    this.stop = false;
  }

  public void beginStop() {
    this.stop = true;
  }

  @Override
  public void run() {
    while (!this.stop || !this.consumerQueue.isEmpty()) {
      try {
        T item = this.consumerQueue.poll(100, TimeUnit.MILLISECONDS);
        if (item == null) {
          continue;
        }
        consumerFx.accept(item);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    // signal end by calling with null object
    consumerFx.accept(null);
  }
}

class ConsumerExecutor<T> {

  public ConsumerExecutor(final BlockingQueue<T> queue,
                          final Consumer<T> consumerFx,
                          int numConsumerThreads) {
    consumerThreads = new ArrayList<>();
    for (int i = 0; i < numConsumerThreads; i++) {
      consumerThreads.add(new ConsumerThread<>(queue, consumerFx));
    }
  }

  public void startConsumers() {
    for (ConsumerThread<T> consumerThread : consumerThreads) {
      consumerThread.start();
    }
  }

  public void join() throws InterruptedException {
    for (ConsumerThread<T> consumerThread : consumerThreads) {
      consumerThread.beginStop();
      consumerThread.join();
    }
  }

  private List<ConsumerThread<T>> consumerThreads;
}

class SkierWithVertical {
  public SkierWithVertical(int skierId, int vertical) {
    this.skierId = skierId;
    this.vertical = vertical;
  }

  public Integer getSkierId() {
    return skierId;
  }

  public Integer getVerticalDistance() {
    return vertical;
  }

  private Integer skierId;
  private Integer vertical;
}

class LiftWithRides {
  public LiftWithRides(int liftId, int numRides) {
    this.liftId = liftId;
    this.numRides = numRides;
  }

  public Integer getLiftId() {
    return liftId;
  }

  public Integer getNumRides() {
    return numRides;
  }

  private Integer liftId;
  private Integer numRides;
}

public class SkiDataProcessorFile {
  private int numConsumerThreads = 1;
  BlockingQueue<SkierQueueItem> skierQueue;
  ConsumerExecutor<SkierQueueItem> skierConsumer;

  BlockingQueue<Integer> liftQueue;
  ConsumerExecutor<Integer> liftConsumer;

  BlockingQueue<LiftHourQueueItem> liftHourQueue;
  ConsumerExecutor<LiftHourQueueItem> liftHourConsumer;

  public SkiDataProcessorFile() {
    skierQueue = new LinkedBlockingDeque<>();
    liftQueue = new LinkedBlockingDeque<>();
    liftHourQueue = new LinkedBlockingDeque<>();

    Consumer<SkierQueueItem> skierConsumerFx = new Consumer<SkierQueueItem>() {
      private ConcurrentMap<Integer, AtomicInteger> skierVerticalRideMap = new ConcurrentHashMap<>();
      private AtomicBoolean finished = new AtomicBoolean(false);
      private final int topNSkiers = 100;
      @Override
      public void accept(SkierQueueItem skierQueueItem) {
        if (skierQueueItem == null) {
          // this is signal from one of threads that queue is empty now
          if (finished.compareAndSet(false, true)) {
            // write file
            try {
              BufferedWriter writer = new BufferedWriter(new FileWriter("skier.csv"));
              writer.write("SkierId,Vertical\n");
              PriorityQueue<SkierWithVertical> topNVerticalSkiers = new PriorityQueue<>(topNSkiers, Comparator.comparingInt(o -> o.getVerticalDistance()));
              for (Map.Entry<Integer, AtomicInteger> entrySet : skierVerticalRideMap.entrySet()) {
                if (topNVerticalSkiers.size() >= topNSkiers) {
                  if (topNVerticalSkiers.peek().getVerticalDistance() > entrySet.getValue().get()) {
                    continue;
                  } else {
                    topNVerticalSkiers.poll();
                  }
                }
                topNVerticalSkiers.add(new SkierWithVertical(entrySet.getKey(), entrySet.getValue().get()));
              }
              List<SkierWithVertical> topSkiers = new ArrayList<>(topNVerticalSkiers.size());
              while (!topNVerticalSkiers.isEmpty()) {
                topSkiers.add(topNVerticalSkiers.poll());
              }

              for (int i = topSkiers.size() - 1; i >= 0; i--) {
                writer.write(topSkiers.get(i).getSkierId() + "," + topSkiers.get(i).getVerticalDistance());
                if (i != 0) {
                  writer.write("\n");
                }
              }
              writer.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
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
      @Override
      public void accept(Integer liftId) {
        if (liftId == null) {
          // this is signal from one of threads that queue is empty now
          if (finished.compareAndSet(false, true)) {
            // write file
            try {
              BufferedWriter writer = new BufferedWriter(new FileWriter("lifts.csv"));
              writer.write("LiftID,Number of Rides\n");
              int numLiftEntries = 0;
              for (Map.Entry<Integer, AtomicInteger> liftNumRidesEntry : liftNumRidesMap.entrySet()) {
                writer.write(liftNumRidesEntry.getKey() + "," + liftNumRidesEntry.getValue());
                numLiftEntries++;
                if (numLiftEntries < liftNumRidesMap.size()) {
                  writer.write("\n");
                }
              }
              writer.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          return;
        }
        liftNumRidesMap.putIfAbsent(liftId, new AtomicInteger(0));
        liftNumRidesMap.get(liftId).incrementAndGet();
      }
    };

    Consumer<LiftHourQueueItem> liftHourConsumerFx = new Consumer<LiftHourQueueItem>() {
      private ConcurrentMap<Integer, ConcurrentMap<Integer, AtomicInteger>> hourNumLiftRidesMap = new ConcurrentSkipListMap<>();
      private AtomicBoolean finished = new AtomicBoolean(false);
      private int numBusiestLifts = 10;

      @Override
      public void accept(LiftHourQueueItem liftHourQueueItem) {
        if (liftHourQueueItem == null) {
          // this is signal from one of threads that queue is empty now
          if (finished.compareAndSet(false, true)) {
            // write file
            try {
              BufferedWriter writer = new BufferedWriter(new FileWriter("hours.csv"));
              writer.write("Hour,LiftID,Number of Rides\n");
              int numHourEntries = 0;
              for (Map.Entry<Integer,  ConcurrentMap<Integer, AtomicInteger>> hourLiftNumRidesEntry : hourNumLiftRidesMap.entrySet()) {
                numHourEntries++;
                PriorityQueue<LiftWithRides> busiestLifts = new PriorityQueue<>(numBusiestLifts, Comparator.comparingInt(o -> o.getNumRides()));
                for (Map.Entry<Integer, AtomicInteger> liftNumRidesEntry : hourLiftNumRidesEntry.getValue().entrySet()) {
                  if (busiestLifts.size() >= numBusiestLifts) {
                    if (busiestLifts.peek().getNumRides() > liftNumRidesEntry.getValue().get()) {
                      continue;
                    } else {
                      busiestLifts.poll();
                    }
                  }
                  busiestLifts.add(new LiftWithRides(liftNumRidesEntry.getKey(), liftNumRidesEntry.getValue().get()));
                }
                int numLiftsRemaining = busiestLifts.size();
                while (!busiestLifts.isEmpty()) {
                  LiftWithRides liftWithRides = busiestLifts.poll();
                  numLiftsRemaining--;
                  writer.write(hourLiftNumRidesEntry.getKey() + "," + liftWithRides.getLiftId() + "," + liftWithRides.getNumRides());
                  if (!(numHourEntries == hourNumLiftRidesMap.size() && numLiftsRemaining == 0)) {
                    writer.write("\n");
                  }
                }
              }
              writer.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          return;
        }
        int hour = liftHourQueueItem.getHour();
        hourNumLiftRidesMap.putIfAbsent(hour, new ConcurrentHashMap<>());
        hourNumLiftRidesMap.get(hour).putIfAbsent(liftHourQueueItem.getLiftId(), new AtomicInteger(0));
        hourNumLiftRidesMap.get(hour).get(liftHourQueueItem.getLiftId()).incrementAndGet();
      }
    };

    skierConsumer = new ConsumerExecutor<>(skierQueue, skierConsumerFx, numConsumerThreads);
    skierConsumer.startConsumers();

    liftConsumer = new ConsumerExecutor<>(liftQueue, liftConsumerFx, numConsumerThreads);
    liftConsumer.startConsumers();

    liftHourConsumer = new ConsumerExecutor<>(liftHourQueue, liftHourConsumerFx,numConsumerThreads);
    liftHourConsumer.startConsumers();
  }

  public void parseFile(String filePath) throws IOException, InterruptedException {
    FileReader in = new FileReader(filePath);
    BufferedReader br = new BufferedReader(in);
    String line = br.readLine();
    if (line == null) {
      throw new IllegalArgumentException("No header in file");
    }
    String headers[] = line.split(",");
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
    while ((line = br.readLine()) != null) {
      String cols[] = line.split(",");
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

  public static void main(String[] args) throws IOException, InterruptedException {
    String filePath = "PDPAssignment.csv";
    SkiDataProcessorFile skiDataProcessor = new SkiDataProcessorFile();
    skiDataProcessor.parseFile(filePath);
  }
}
