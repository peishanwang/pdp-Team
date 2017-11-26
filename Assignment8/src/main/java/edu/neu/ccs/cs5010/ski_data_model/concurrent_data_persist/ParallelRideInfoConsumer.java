package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class ParallelRideInfoConsumer implements IRideInfoConsumer {
  private static final Logger LOGGER
      = Logger.getLogger(ParallelRideInfoConsumer.class.getName());
  private int numConsumerThreads = 1;

  Queue<SkierQueueItem> skierQueue;
  ConsumerExecutor<SkierQueueItem> skierConsumer;

  Queue<Integer> liftQueue;
  ConsumerExecutor<Integer> liftConsumer;

  Queue<LiftHourQueueItem> liftHourQueue;
  ConsumerExecutor<LiftHourQueueItem> liftHourConsumer;

  /**
   * Parellel Ride info consumer Constructor.
   */
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

  /**
   * method overridden by RideInfoConsumer.
   * @param rideInfo detail of ride.
   */
  @Override
  public void accept(RideInfo rideInfo) {
    skierQueue.add(new SkierQueueItem(rideInfo.getSkierId(),
            rideInfo.getLiftId(), rideInfo.getTime()));
    liftQueue.add(rideInfo.getLiftId());
    liftHourQueue.add(new LiftHourQueueItem(rideInfo.getTime(), rideInfo.getLiftId()));
  }

  /**
   * method to start processes and stop them.
   */
  @Override
  public void stop() {
    // stop for threads to finish
    try {
      final long startTime = System.currentTimeMillis();
      skierConsumer.join();
      liftConsumer.join();
      liftHourConsumer.join();
      long endTime = System.currentTimeMillis();
      String str = String.format("time taken to stop threads: %1$d ms", endTime - startTime);
      LOGGER.log(Level.FINE, str);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.info(e.getMessage());
    }
  }
}
