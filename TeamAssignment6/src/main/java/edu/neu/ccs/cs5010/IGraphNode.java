package edu.neu.ccs.cs5010;

import java.util.Date;
import java.util.Set;

public interface IGraphNode {
  void addFollower(GraphNode node);

  int getNodeId();

  Set<Integer> getFriends();

  int getNumFollowers();

  Date getProfileCreationDate();

  void addOneRecommended();

  int getRecommendedTimes();
}
