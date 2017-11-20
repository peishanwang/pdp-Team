package edu.neu.ccs.cs5010.concurrentsystem;

import edu.neu.ccs.cs5010.IRideInfoConsumer;
import edu.neu.ccs.cs5010.RideInfo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelRideInfoConsumer implements IRideInfoConsumer {
  private int numConsumerThreads = 1;
  Queue<SkierQueueItem> skierQueue;
  ConsumerExecutor<SkierQueueItem> skierConsumer;

  Queue<Integer> liftQueue;
  ConsumerExecutor<Integer> liftConsumer;

  Queue<LiftHourQueueItem> liftHourQueue;
  ConsumerExecutor<LiftHourQueueItem> liftHourConsumer;

  public ParallelRideInfoConsumer() {
    skierQueue = new ConcurrentLinkedQueue<>();
    liftQueue = new ConcurrentLinkedQueue<>();
    liftHourQueue = new ConcurrentLinkedQueue<>();

    skierConsumer = new ConsumerExecutor<>(skierQueue, new SkierConsumer(), numConsumerThreads);
    skierConsumer.startConsumers();

    liftConsumer = new ConsumerExecutor<>(liftQueue, new LiftConsumer(), numConsumerThreads);
    liftConsumer.startConsumers();

    liftHourConsumer = new ConsumerExecutor<>(
            liftHourQueue, new LiftHourConsumer(), numConsumerThreads);
    liftHourConsumer.startConsumers();
  }

  @Override
  public void accept(RideInfo rideInfo) {
    skierQueue.add(new SkierQueueItem(rideInfo.getSkierId(), rideInfo.getLiftId()));
    liftQueue.add(rideInfo.getLiftId());
    liftHourQueue.add(new LiftHourQueueItem(rideInfo.getTime(), rideInfo.getLiftId()));
  }

  @Override
  public void stop() {
    // stop for threads to finish
    try {
      long startTime = System.currentTimeMillis();
      skierConsumer.join();
      liftConsumer.join();
      liftHourConsumer.join();
      long endTime = System.currentTimeMillis();
      System.out.println("time taken to stop threads: " + (endTime - startTime));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
