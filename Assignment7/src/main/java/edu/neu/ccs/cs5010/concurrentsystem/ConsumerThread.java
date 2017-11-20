package edu.neu.ccs.cs5010.concurrentsystem;

import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class ConsumerThread<T> extends Thread {
  private final Queue<T> consumerQueue;
  private final Consumer<T> consumerFx;
  private volatile boolean stop;

  /**
   * ConsumerThread Constructor.
   * @param queue queue of data to infer
   * @param consumerFx consumer according to operation required
   */
  public ConsumerThread(final Queue<T> queue, final Consumer<T> consumerFx) {
    this.consumerQueue = queue;
    this.consumerFx = consumerFx;
    this.stop = false;
  }

  /**
   *method to use if we want to stop thread execution before consumer queue is empty.
   */
  public void beginStop() {
    this.stop = true;
  }

  /**
   * method overridden by Thread class.
   */
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
