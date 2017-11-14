package edu.neu.ccs.cs5010;

import org.junit.Test;

public class Rule4Test {

  @Test
  public void checkRule4Coverage(){
    IRecommendationSystem system = new SRecommendationSystem(
        "nodes_rule1.csv",
        "edges_small.csv",
        100,
        20);
    system.initializeSystem();
    system.generateRecommendation();
    Rule rule = new Rule4FollowRandomUser();
    UserRecommendationList userRecommendationList = new UserRecommendationList(96);
    rule.generateRecommendations(
        userRecommendationList,
        96,
        SocialNetworkUsersMap.idToNodeMap,
        7
    );
  }
}
