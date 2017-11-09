package edu.neu.ccs.cs5010;

import java.util.HashSet;
import java.util.Set;

class UserRecommendationList {
  UserRecommendationList(int userId) {
    this.userId = userId;
    this.recommendations = new HashSet<>();
  }
  // put in recommendation list if not already there
  public void tryRecommendUser(int nodeId) {
    if (this.userId != nodeId) {
      this.recommendations.add(nodeId);
    }
  }
  public int getUserId() {
    return userId;
  }

  public int getRecommendationSize() {
    return recommendations.size();
  }

  public Set<Integer> getRecommendations() {
    return recommendations;
  }
  private int userId;
  private Set<Integer> recommendations;
}

