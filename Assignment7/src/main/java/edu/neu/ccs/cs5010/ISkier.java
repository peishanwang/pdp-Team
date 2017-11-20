package edu.neu.ccs.cs5010;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public interface ISkier extends Comparable<ISkier> {
  int getSkierId();

  void addVertical(int vertical);

  int getTotalVertical();
}
