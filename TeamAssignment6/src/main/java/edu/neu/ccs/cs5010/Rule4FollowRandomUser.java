package edu.neu.ccs.cs5010;

import java.util.Map;
import java.util.Random;

/**
 * Class to generate recommendations randomly using 4th criteria.
 */
public class Rule4FollowRandomUser implements Rule{

  /**
   * Method to generate recommendations. overridden by Rule Interface.
   * @param recommendationList list of recommended users
   * @param userId id of user
   * @param idToNodeMap map that stores node-id as key and graph node as value
   * @param numRecommendationsPerUser required number of recommendations per user
   */
  @Override
  public void generateRecommendations(UserRecommendationList recommendationList,
                                              int userId,
                                              Map<Integer, GraphNode> idToNodeMap,
                                              int numRecommendationsPerUser) {
    Random random = new Random();
    GraphNode userNode = idToNodeMap.get(userId);
    while (recommendationList.getRecommendationSize() < numRecommendationsPerUser) {
      // assuming nodeId are sequentially given

      int randomNodeId = random.nextInt(idToNodeMap.size()) + 1;
      if (!userNode.getFriends().contains(randomNodeId)) {
        recommendationList.tryRecommendUser(randomNodeId);
      }

      if (recommendationList.getRecommendationSize()
              + idToNodeMap.get(userId).getFriends().size()
              + 1
              >= idToNodeMap.keySet().size()) {
        System.out.println("Required number of recommendations cannot be generated");
        break;
      }

    }
  }
}
