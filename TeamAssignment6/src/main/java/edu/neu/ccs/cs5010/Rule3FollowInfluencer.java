package edu.neu.ccs.cs5010;

import java.util.List;
import java.util.Map;

public class Rule3FollowInfluencer implements Rule {
  @Override
  public UserRecommendationList generateRecommendations(UserRecommendationList recommendationList,
                                              int userId,
                                              Map<Integer, GraphNode> idToNodeMap,
                                              int numRecommendationsPerUser) {

    GraphNode userNode = idToNodeMap.get(userId);
    //TODO: change maxInfluencers according to file.
    List<Integer> influencersList = new TwitterGraph().generateInfluencers(250);
    for (Integer nodeId : influencersList) {
      if (!userNode.getFriends().contains(nodeId)) {
        recommendationList.tryRecommendUser(nodeId);
        if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
          break;
        }
      }
    }
    return recommendationList;
  }
}
