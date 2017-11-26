package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

/**
 * Interface of RideInfo.
 *
 * @see IRideInfo
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