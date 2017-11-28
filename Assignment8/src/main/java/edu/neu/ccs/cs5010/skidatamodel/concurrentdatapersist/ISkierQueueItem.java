package edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist;

/**
 * This is part of PDP Assignment 8.
 *
 * @author Manika and Peishan
 */
public interface ISkierQueueItem {

  Integer getSkierId();

  Integer getLiftId();

  Integer getTs();
}
