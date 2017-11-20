package edu.neu.ccs.cs5010;

/**
 * Interface of Skier.
 *
 * @see Skier
 */
public interface ISkier extends Comparable<ISkier> {
  /**
   * Returns skier's id.
   * @return skier's id.
   */
  int getSkierId();

  /**
   * Add vertical for the skier.
   * @param vertical value of vertical.
   */
  void addVertical(int vertical);

  /**
   * Returns total number of vertical.
   * @return total number of vertical.
   */
  int getTotalVertical();
}
