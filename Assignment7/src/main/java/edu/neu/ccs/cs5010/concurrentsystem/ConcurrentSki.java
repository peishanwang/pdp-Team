package edu.neu.ccs.cs5010.concurrentsystem;


import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

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
import static edu.neu.ccs.cs5010.IoUtil.getReader;

public class ConcurrentSki {
  private int numConsumerThreads = 1;
  BlockingQueue<SkierQueueItem> skierQueue;
  ConsumerExecutor<SkierQueueItem> skierConsumer;

  BlockingQueue<Integer> liftQueue;
  ConsumerExecutor<Integer> liftConsumer;

  BlockingQueue<LiftHourQueueItem> liftHourQueue;
  ConsumerExecutor<LiftHourQueueItem> liftHourConsumer;

  public ConcurrentSki() {
    skierQueue = new LinkedBlockingDeque<>();
    liftQueue = new LinkedBlockingDeque<>();
    liftHourQueue = new LinkedBlockingDeque<>();

    skierConsumer = new ConsumerExecutor<>(skierQueue, new SkierConsumer(), numConsumerThreads);
    skierConsumer.startConsumers();

    liftConsumer = new ConsumerExecutor<>(liftQueue, new LiftConsumer(), numConsumerThreads);
    liftConsumer.startConsumers();

    liftHourConsumer = new ConsumerExecutor<>(
            liftHourQueue, new LiftHourConsumer(), numConsumerThreads);
    liftHourConsumer.startConsumers();
  }

  public void parseFile(String filePath) throws InterruptedException {
    CsvParserSettings settings = new CsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    CsvParser parser = new CsvParser(settings);
    parser.beginParsing(getReader(filePath));
    String[] headers = parser.parseNext();
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
    String[] row;
    long startTime = System.currentTimeMillis();
    while ((row = parser.parseNext()) != null)  {
      Integer skierId = Integer.parseInt(row[skierIdx]);
      Integer liftId = Integer.parseInt(row[liftIdx]);
      Integer time = Integer.parseInt(row[timeIdx]);

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
