package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ERecommendationSystem extends RecommendationSystem {

  public ERecommendationSystem(String nodeCsv, String edgeCsv, int numToProcess, int numToRecommend) {
    super(nodeCsv, edgeCsv, numToProcess, numToRecommend);
  }

  @Override
  public void generateRecommendation() {
    Map<Integer, GraphNode> map = SocialNetworkUsersMap.idToNodeMap;
    List<Integer> allUser = new ArrayList<>(map.keySet());
    Collections.sort(allUser, (user1, user2) -> user2 - user1);
    for (int i = 0; i < numToProcess; i++) {
      UserRecommendationList recommendationList = new UserRecommendationList(allUser.get(i));
      giveRecommendation(recommendationList, allUser.get(i), map);
      allRecommendations.add(recommendationList);
    }
  }
}
