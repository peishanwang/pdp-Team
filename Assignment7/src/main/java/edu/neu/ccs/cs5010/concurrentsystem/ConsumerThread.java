package edu.neu.ccs.cs5010.concurrentsystem;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ConsumerThread<T> extends Thread {
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
