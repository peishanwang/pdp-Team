package edu.neu.ccs.cs5010;

import java.util.*;

public class RRecommendationSystem extends RecommendationSystem{

  public RRecommendationSystem(String nodeCsv, String edgeCsv, int numToProcess, int numToRecommend) {
    super(nodeCsv, edgeCsv, numToProcess, numToRecommend);
  }

  @Override
  public void generateRecommendation() {
    Map<Integer, GraphNode> map = SocialNetworkUsersMap.idToNodeMap;
    List<Integer> allUser = new ArrayList<>(map.keySet());
    Random random = new Random();
    Collections.sort(allUser, (a, b) -> b - a);
    Set<Integer> selectedUser = new HashSet();
    int count = 0;
    while (count < numToRecommend) {
      int index;
      do {
        index = random.nextInt(allUser.size());
      } while (selectedUser.contains(index));
      selectedUser.add(index);
      UserRecommendationList recommendationList = new UserRecommendationList(allUser.get(index));
      recommendationList = giveRecommendation(recommendationList, allUser.get(index), map);
      allRecommendations.add(recommendationList);
    }
  }
}
