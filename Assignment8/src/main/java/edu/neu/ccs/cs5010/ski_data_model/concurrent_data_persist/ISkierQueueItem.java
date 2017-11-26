package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public interface ISkierQueueItem {
  Integer getSkierId();
  Integer getLiftId();
  Integer getTs();
}
