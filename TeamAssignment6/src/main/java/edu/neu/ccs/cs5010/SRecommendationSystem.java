package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * RRecommendationSystem class is used for taking users to process from start.
 *
 */
public class SRecommendationSystem extends RecommendationSystem{

  /**
   * SRecommendationSystem constructor.
   * @param nodeCsv node csv file
   * @param edgeCsv edge csv file
   * @param numToProcess number of users to process
   * @param numToRecommend number of recommendation to make
   */
  public SRecommendationSystem(String nodeCsv,
                               String edgeCsv,
                               int numToProcess,
                               int numToRecommend) {
    super(nodeCsv, edgeCsv, numToProcess, numToRecommend);
  }

  /**
   * method to generate recommendations-overridden from interface RecommendationSystem.
   */
  @Override
  public void generateRecommendation() {
    Map<Integer, GraphNode> map = new SocialNetworkUsersMap().idToNodeMap;
    List<Integer> allUser = new ArrayList<>(map.keySet());
    Collections.sort(allUser);
    for (int i = 0; i < numToProcess; i++) {
      UserRecommendationList recommendationList = new UserRecommendationList(allUser.get(i));
      giveRecommendation(recommendationList, allUser.get(i), map);
      allRecommendations.add(recommendationList);
    }
  }
}
