package edu.neu.ccs.cs5010.concurrentsystem;

import java.util.Queue;
import java.util.function.Consumer;

public class ConsumerThread<T> extends Thread {
  private final Queue<T> consumerQueue;
  private final Consumer<T> consumerFx;
  private volatile boolean stop;

  public ConsumerThread(final Queue<T> queue, final Consumer<T> consumerFx) {
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
      T item = this.consumerQueue.poll();
      if (item == null) {
        continue;
      }
      consumerFx.accept(item);
    }
    // signal end by calling with null object
    consumerFx.accept(null);
  }
}
