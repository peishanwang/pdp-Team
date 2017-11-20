package edu.neu.ccs.cs5010.concurrentsystem;

import edu.neu.ccs.cs5010.IRideInfoConsumer;
import edu.neu.ccs.cs5010.RideInfo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class ParallelRideInfoConsumer implements IRideInfoConsumer {
  private int numConsumerThreads = 1;
  BlockingQueue<SkierQueueItem> skierQueue;
  ConsumerExecutor<SkierQueueItem> skierConsumer;

  BlockingQueue<Integer> liftQueue;
  ConsumerExecutor<Integer> liftConsumer;

  BlockingQueue<LiftHourQueueItem> liftHourQueue;
  ConsumerExecutor<LiftHourQueueItem> liftHourConsumer;

  public ParallelRideInfoConsumer() {
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

  @Override
  public void accept(RideInfo rideInfo) {
    try {
      skierQueue.put(new SkierQueueItem(rideInfo.getSkierId(), rideInfo.getLiftId()));
      liftQueue.put(rideInfo.getLiftId());
      liftHourQueue.put(new LiftHourQueueItem(rideInfo.getTime(), rideInfo.getLiftId()));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    // stop for threads to finish
    try {
      skierConsumer.join();
      liftConsumer.join();
      liftHourConsumer.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
