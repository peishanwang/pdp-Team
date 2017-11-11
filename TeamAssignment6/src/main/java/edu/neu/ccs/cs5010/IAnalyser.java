package edu.neu.ccs.cs5010;

import java.util.List;

/**
 * IAnalyser interface used to get top recommended users.
 */
public interface IAnalyser {

  /**
   * method to get list of top 10 users.
   * @return list of top 10 recommended users
   */
  List<Integer> getTopTenResult();
}
