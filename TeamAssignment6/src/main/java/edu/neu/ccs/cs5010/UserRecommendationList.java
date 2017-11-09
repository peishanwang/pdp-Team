package edu.neu.ccs.cs5010;

import java.util.HashSet;
import java.util.Set;

/**
 * fields - userid of user we are generating recommendation for.
 * set of all the users recommended
 *
 */
class UserRecommendationList {
  /**
   * Constructor of class.
   * @param userId id of user
   */
  UserRecommendationList(int userId) {
    this.userId = userId;
    this.recommendations = new HashSet<>();
  }

  /**
   * Method will add user into recommendation if not already present.
   * @param nodeId id of user recommended
   */
  public void tryRecommendUser(int nodeId) {
    if (this.userId != nodeId) {
      this.recommendations.add(nodeId);
    }
  }

  /**
   * method to get user id.
   * @return user id
   */
  public int getUserId() {
    return userId;
  }

  /**
   * size of recommended users list.
   * @return size of recommended users list
   */
  public int getRecommendationSize() {
    return recommendations.size();
  }

  /**
   * method to get recommendations.
   * @return recommendations generated
   */
  public Set<Integer> getRecommendations() {
    return recommendations;
  }

  private int userId;
  private Set<Integer> recommendations;
}

