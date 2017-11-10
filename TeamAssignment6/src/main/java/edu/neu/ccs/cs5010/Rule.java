package edu.neu.ccs.cs5010;

import java.util.Map;

/**
 * Interface Rule.
 */
public interface Rule {
  /**
   * takes a recommended list and generate more recommendations and then append new recommendations.
   * @param recommendationList list of recommended users
   * @param userId id of user
   * @param idToNodeMap map that stores node-id as key and graph node as value
   * @param numRecommendationsPerUser required number of recommendations per user
   */
  void generateRecommendations(UserRecommendationList recommendationList,
                              int userId,
                              Map<Integer,GraphNode> idToNodeMap,
                              int numRecommendationsPerUser);
}
