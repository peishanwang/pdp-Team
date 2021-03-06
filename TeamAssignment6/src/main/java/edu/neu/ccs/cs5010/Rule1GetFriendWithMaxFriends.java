package edu.neu.ccs.cs5010;

import java.util.Date;
import java.util.Map;

/**
 * Class to generate recommendations according to First Criteria.
 * Rule 1: if profile less than 1 month old, find friend which has most friends and
 * follow what he follows
 */
public class Rule1GetFriendWithMaxFriends implements Rule{

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
    Date currentDate = new Date();
    long monthTimeMs = 31L * 24L * 60L * 60L * 1000L;
    GraphNode userNode = idToNodeMap.get(userId);
    if ((currentDate.getTime() - userNode.getProfileCreationDate().getTime()) <= monthTimeMs) {
      int maxfriends = 0;
      GraphNode nodeWithMaxfriends = null;
      for (Integer friendsNodeId : userNode.getFriends()) {
        GraphNode friendsNode = idToNodeMap.get(friendsNodeId);
        if (friendsNode.getFriends().size() > maxfriends) {
          nodeWithMaxfriends = friendsNode;
          maxfriends = nodeWithMaxfriends.getFriends().size();
        }
      }
      if (maxfriends > 0) {
        for (Integer friendsNodeId : nodeWithMaxfriends.getFriends()) {
          if (recommendationList.getRecommendationSize() < numRecommendationsPerUser
                 && !userNode.getFriends().contains(friendsNodeId)) {
            recommendationList.tryRecommendUser(friendsNodeId);
          }
        }
      }
    }
  }


}
