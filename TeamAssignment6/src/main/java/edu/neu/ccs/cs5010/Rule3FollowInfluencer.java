package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Class to generate recommendations by following influencers of social network.
 * This class uses Criteria 3 to generate recommendations
 */
public class Rule3FollowInfluencer implements Rule {

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
    List<Integer> influencersList;
    if (idToNodeMap.size() == 100) {
      influencersList = new Rule3FollowInfluencer().generateInfluencers(idToNodeMap, 25);
    } else {
      influencersList = new Rule3FollowInfluencer().generateInfluencers(idToNodeMap, 250);
    }
    for (Integer nodeId : influencersList) {
      if (!userNode.getFriends().contains(nodeId)) {
        recommendationList.tryRecommendUser(nodeId);
        if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
          break;
        }
      }
    }
  }


  /**
   * method to generate influencers of this social network.
   * @param idToNodeMap map that stores information of users
   * @param maxInfluencers number of max influencers(25 or 250)
   * @return list of influencers
   */
  public List<Integer> generateInfluencers(Map<Integer, GraphNode> idToNodeMap,
                                           int maxInfluencers) {
    Comparator<GraphNode> followerComparator = new Comparator<GraphNode>() {
      @Override
      public int compare(GraphNode obj1, GraphNode obj2) {
        return obj1.getNumFollowers() - obj2.getNumFollowers();
      }
    };
    // min heap of max influencers
    PriorityQueue<GraphNode> influencersPriorityQueue = new PriorityQueue<>(maxInfluencers,
            followerComparator);
    // go over all the nodes in graph and add to queue
    for (GraphNode node : idToNodeMap.values()) {
      if (influencersPriorityQueue.size() < maxInfluencers) {
        influencersPriorityQueue.add(node);
      } else {
        if (node.getNumFollowers() > influencersPriorityQueue.peek().getNumFollowers()) {
          influencersPriorityQueue.poll();
          influencersPriorityQueue.add(node);
        }
      }
    }

    List<Integer> influencersList = new ArrayList<>(maxInfluencers);
    while (!influencersPriorityQueue.isEmpty()) {
      influencersList.add(influencersPriorityQueue.poll().getNodeId());
    }
    Collections.sort(influencersList);
    return influencersList;
  }
}
