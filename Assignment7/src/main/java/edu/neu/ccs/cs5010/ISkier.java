package edu.neu.ccs.cs5010;

public interface ISkier extends Comparable<ISkier> {
  int getSkierId();
  void addVertical(int vertical);
  int getTotalVertical();
}
