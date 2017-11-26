package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class ConsumerExecutor<T> {

  /**
   * ConsumerExecutor Constructor.
   * @param queue queue of data to infer.
   * @param consumerFx Consumer object
   * @param numConsumerThreads number of threads
   */
  public ConsumerExecutor(final Queue<T> queue,
                          final Consumer<T> consumerFx,
                          int numConsumerThreads) {
    consumerThreads = new ArrayList<>();
    for (int i = 0; i < numConsumerThreads; i++) {
      consumerThreads.add(new ConsumerThread<>(queue, consumerFx));
    }
  }

  /**
   * startConsumers method to start all threads.
   */
  public void startConsumers() {
    for (ConsumerThread<T> consumerThread : consumerThreads) {
      consumerThread.start();
    }
  }

  /**
   * method to execute all threads.
   * join basically means waiting for a thread to finish.
   * @throws InterruptedException Interrupted Exception
   */
  public void join() throws InterruptedException {
    for (ConsumerThread<T> consumerThread : consumerThreads) {
      consumerThread.beginStop();
      consumerThread.join();
    }
  }

  private List<ConsumerThread<T>> consumerThreads;

}