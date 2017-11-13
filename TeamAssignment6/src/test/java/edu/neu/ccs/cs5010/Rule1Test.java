package edu.neu.ccs.cs5010;

import org.junit.Test;

public class Rule1Test {

  @Test
  public void checkRule1Coverage(){
    IRecommendationSystem system = new SRecommendationSystem(
            "nodes_rule1.csv",
            "edges_small.csv",
            100,
            5);
    system.initializeSystem();
    system.generateRecommendation();
    Rule rule = new Rule1GetFriendWithMaxFriends();
    UserRecommendationList userRecommendationList = new UserRecommendationList(2);
    rule.generateRecommendations(
            userRecommendationList,
            1,
            SocialNetworkUsersMap.idToNodeMap,
            5
    );
  }
}
