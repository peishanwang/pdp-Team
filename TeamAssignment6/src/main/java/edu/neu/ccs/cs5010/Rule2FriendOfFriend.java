package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class to generate recommendations by following friends of friends.
 * This class uses Criteria 2.
 */
public class Rule2FriendOfFriend implements Rule {

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

    GraphNode userNode = idToNodeMap.get(userId);
    Set<Integer> friendOfFriends = new HashSet<>();
    for (Integer friendId : userNode.getFriends()) {
      GraphNode friendNode = idToNodeMap.get(friendId);
      if (friendNode == null) {
        throw new IllegalStateException("friendNode not found for id: " + friendId);
      }
      for (Integer friendOfFriend : friendNode.getFriends()) {
        if (userNode.getNodeId() != friendOfFriend
                && !userNode.getFriends().contains(friendOfFriend)) {
          friendOfFriends.add(friendOfFriend);
        }
      }
    }
    // check if already in recommendation list
    List<Integer> friendOfFriendsList = new ArrayList<>(friendOfFriends);
    friendOfFriends.clear();
    Collections.sort(friendOfFriendsList);
    for (Integer friendOfFriendId : friendOfFriendsList) {
      recommendationList.tryRecommendUser(friendOfFriendId);
      if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
        break;
      }
    }
  }
}
