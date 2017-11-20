package edu.neu.ccs.cs5010;

/**
 * Interface of RideInfo.
 *
 * @see RideInfo
 */
public interface IRideInfo {

  /**
   * Returns resort's id.
   * @return resort's id.
   */
  int getResortId();

  /**
   * Returns day.
   * @return day.
   */
  int getDay();

  /**
   * Returns skier's id.
   * @return skier's id.
   */
  int getSkierId();

  /**
   * Returns lift's id.
   * @return lift's id.
   */
  int getLiftId();

  /**
   * Returns time.
   * @return time.
   */
  int getTime();
}
