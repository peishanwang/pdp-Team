package edu.neu.ccs.cs5010;

import java.util.Map;
import java.util.Random;

public class Rule4FollowRandomUser implements Rule{
  @Override
  public UserRecommendationList generateRecommendations(UserRecommendationList recommendationList,
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
    }
    return recommendationList;
  }
}
