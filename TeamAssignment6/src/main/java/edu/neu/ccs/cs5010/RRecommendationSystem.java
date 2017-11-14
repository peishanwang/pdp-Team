package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


/**
 * RRecommendationSystem class is used for taking users to process randomly.
 *
 */
public class RRecommendationSystem extends RecommendationSystem {

  /**
   * RRecommendationSystem constructor.
   * @param nodeCsv node csv file
   * @param edgeCsv edge csv file
   * @param numToProcess number of users to process
   * @param numToRecommend number of recommendation to make
   */
  public RRecommendationSystem(String nodeCsv,
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
    Map<Integer, GraphNode> map = SocialNetworkUsersMap.idToNodeMap;
    List<Integer> allUser = new ArrayList<>(map.keySet());
    Random random = new Random();
    allUser.sort((user1, user2) -> user2 - user1);
    Set<Integer> selectedUser = new HashSet<>();
    int count = 0;
    while (count < numToRecommend) {
      int index;
      do {
        index = random.nextInt(allUser.size());
        count++;
      } while (selectedUser.contains(index));
      selectedUser.add(index);
      UserRecommendationList recommendationList = new UserRecommendationList(allUser.get(index));
      giveRecommendation(recommendationList, allUser.get(index), map);
      allRecommendations.add(recommendationList);
    }
  }
}
