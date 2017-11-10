package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Rule3FollowInfluencer implements Rule {
  @Override
  public void generateRecommendations(UserRecommendationList recommendationList,
                                              int userId,
                                              Map<Integer, GraphNode> idToNodeMap,
                                              int numRecommendationsPerUser) {

    GraphNode userNode = idToNodeMap.get(userId);
    //TODO: change maxInfluencers according to file.
    List<Integer> influencersList;
    if(idToNodeMap.size()==100) {
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


  public List<Integer> generateInfluencers(Map<Integer, GraphNode> idToNodeMap, int maxInfluencers) {
    Comparator<GraphNode> followerComparator = new Comparator<GraphNode>() {
      @Override
      public int compare(GraphNode o1, GraphNode o2) {
        return o1.getNumFollowers() - o2.getNumFollowers();
      }
    };
    // min heap of max influencers
    PriorityQueue<GraphNode> influencersPQ = new PriorityQueue<>(maxInfluencers, followerComparator);
    // go over all the nodes in graph and add to queue
    for (GraphNode node : idToNodeMap.values()) {
      if (influencersPQ.size() < maxInfluencers) {
        influencersPQ.add(node);
      } else {
        if (node.getNumFollowers() > influencersPQ.peek().getNumFollowers()) {
          influencersPQ.poll();
          influencersPQ.add(node);
        }
      }
    }

    List<Integer> influencersList = new ArrayList<>(maxInfluencers);
    while (!influencersPQ.isEmpty()) {
      influencersList.add(influencersPQ.poll().getNodeId());
    }
    Collections.sort(influencersList);
    return influencersList;
  }
}
