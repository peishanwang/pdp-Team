package edu.neu.ccs.cs5010;

import java.util.Collections;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class Rule2FriendOfFriend implements Rule {
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
      for (Integer fOfF : friendNode.getFriends()) {
        if (userNode.getNodeId() != fOfF && !userNode.getFriends().contains(fOfF)) {
          friendOfFriends.add(fOfF);
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
