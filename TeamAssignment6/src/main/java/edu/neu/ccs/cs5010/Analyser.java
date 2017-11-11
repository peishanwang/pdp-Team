package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Analyser class is used to print top 10 recommended users.
 */
public class Analyser implements IAnalyser {

  public List<Integer> getTopTenResult() {
    List<Integer> res = new ArrayList<>();
    Set<Integer> allUsers = SocialNetworkUsersMap.idToNodeMap.keySet();
    PriorityQueue<GraphNode> priorityQueue = new PriorityQueue<GraphNode>(
        new Comparator<GraphNode>() {
          @Override
          public int compare(GraphNode user1, GraphNode user2) {
            if (user1.getRecommendedTimes() == user2.getRecommendedTimes()) {
              return user1.getNodeId() - user2.getNodeId();
            } else {
              return user2.getRecommendedTimes() - user1.getRecommendedTimes();
            }
          }
        }
    );
    for (int user : allUsers) {
      priorityQueue.add(SocialNetworkUsersMap.idToNodeMap.get(user));
    }
    for (int i = 0; i < 10; i++) {
      res.add(priorityQueue.poll().getNodeId());
    }
    return res;
  }
}
