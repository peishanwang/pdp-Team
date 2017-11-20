package edu.neu.ccs.cs5010.concurrentsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class ConsumerExecutor<T> {

  public ConsumerExecutor(final Queue<T> queue,
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