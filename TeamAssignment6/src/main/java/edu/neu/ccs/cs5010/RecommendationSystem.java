package edu.neu.ccs.cs5010;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RecommendationSystem implements IRecommendationSystem {
  private static final String NODE_ID = "Node ID";
  private static final String SEPARATOR = ",";
  private static final String RECOMMENDED_NODES = "Recommended nodes";
  private static final String ENCODING = "UTF-8";
  private static final int HASHCODE_INITIAL = 17;
  private static final int HASHCODE_COEFFICIENT = 31;
  private static final int PRINTED_NUMBER = 10;
  List<UserRecommendationList> allRecommendations;
  private String nodeCsv;
  private String edgeCsv;
  int numToProcess;
  int numToRecommend;

  public RecommendationSystem(String nodeCsv, String edgeCsv, int numToProcess, int numToRecommend) {
    allRecommendations = new ArrayList<>();
    this.nodeCsv = nodeCsv;
    this.edgeCsv = edgeCsv;
    this.numToProcess = numToProcess;
    this.numToRecommend = numToRecommend;
  }

  public abstract void generateRecommendation();


  @Override
  public void printResult() {
    for (UserRecommendationList userRecommendationList : allRecommendations) {
      System.out.print("User: " + userRecommendationList.getUserId() + " -> [");
      for (Integer nodeId : userRecommendationList.getRecommendations()) {
        System.out.print(nodeId + ", ");

      }
      System.out.println("]");
    }
    List<Integer> topTen = new Analyser().getTopTenResult();
    System.out.println("Top ten most frequently recommended node IDs : ");
    for (int i = 0; i < PRINTED_NUMBER; i++) {
      System.out.println(topTen.get(i));
    }
  }

  public static void main(String[] args) throws IOException {
    ICmdParser cmdParser = new CmdParser(args);
    IRecommendationSystem recommendationSystem = chooseSystem(cmdParser);
    recommendationSystem.initializeSystem();
    recommendationSystem.generateRecommendation();
    recommendationSystem.outputResult(cmdParser.getFileOutput(), ENCODING);
    recommendationSystem.printResult();
  }

  private static IRecommendationSystem chooseSystem(ICmdParser cmdParser) {
    IRecommendationSystem recommendationSystem;
    if (cmdParser.getProcessingFlag() == 's') {
      recommendationSystem = new SRecommendationSystem(cmdParser.getFileNodes(),
              cmdParser.getFileEdges(),
              cmdParser.getNumberOfUsersToProcess(),
              cmdParser.getNumberOfRecommendations());
    } else if (cmdParser.getProcessingFlag() == 'e') {
      recommendationSystem = new ERecommendationSystem(cmdParser.getFileNodes(),
              cmdParser.getFileEdges(),
              cmdParser.getNumberOfUsersToProcess(),
              cmdParser.getNumberOfRecommendations());
    } else {
      recommendationSystem = new RRecommendationSystem(cmdParser.getFileNodes(),
              cmdParser.getFileEdges(),
              cmdParser.getNumberOfUsersToProcess(),
              cmdParser.getNumberOfRecommendations());
    }
    return recommendationSystem;
  }

  public void initializeSystem() {
    SocialNetworkUsersMap socialNetworkUsersMap = new SocialNetworkUsersMap();
    // read node data
    IIoUtil ioUtil = new IoUtil(nodeCsv, ENCODING);
    List<String> nodesInfo = ioUtil.getInput();
    if (nodesInfo == null || nodesInfo.size() == 0 || nodesInfo.get(0) == null) {
      throw new IllegalArgumentException("Node File doesn't contain header");
    }
    for (int i = 1; i < nodesInfo.size(); i++) {
      String cols[] = nodesInfo.get(i).split(SEPARATOR);
      socialNetworkUsersMap.addNode(Integer.parseInt(cols[0]), cols[1], cols[2], Integer.parseInt(cols[3]), cols[4]);
    }

    // read edge data
    ioUtil = new IoUtil(edgeCsv, ENCODING);
    List<String> edgeInfo = ioUtil.getInput();
    if (edgeInfo == null || edgeInfo.size() == 0 || edgeInfo.get(0) == null) {
      throw new IllegalArgumentException("Edge File doesn't contain header");
    }
    for (int i = 1; i < edgeInfo.size(); i++) {
      String cols[] = edgeInfo.get(i).split(SEPARATOR);
      socialNetworkUsersMap.addEdge(Integer.parseInt(cols[0]), Integer.parseInt(cols[1]));
    }
  }

  public void outputResult(String path, String encoding) {
    ICsvGenerator csvGenerator = new CsvGenerator(Arrays.asList(NODE_ID, RECOMMENDED_NODES));
    for (int i = 0; i < allRecommendations.size(); i++) {
      csvGenerator.addLine(listToMap(allRecommendations.get(i)));
    }
    csvGenerator.generateCsv(path, encoding);
  }

  private HashMap<String, String> listToMap(UserRecommendationList recommendationForOne) {
    HashMap<String, String> map = new HashMap<>();
    map.put(NODE_ID, recommendationForOne.getUserId() + "");
    StringBuilder strb = new StringBuilder();
    strb.append("[");
    for (int recommendedFriend : recommendationForOne.getRecommendations()) {
      strb.append(recommendedFriend + " ");
    }
    strb.deleteCharAt(strb.length() - 1);
    strb.append("]");
    map.put(RECOMMENDED_NODES, strb.toString());
    return map;
  }

  //update recommendationList
  void giveRecommendation(UserRecommendationList recommendationList,
                          int id,
                          Map<Integer, GraphNode> map) {
    Rule currRule;
    currRule = new Rule1GetFriendWithMaxFriends();
    currRule.generateRecommendations(recommendationList, id, map, numToRecommend);
    if (recommendationList.getRecommendationSize() >= numToRecommend) {
      // recommendation finished for this user
      return;
    }
    currRule = new Rule2FriendOfFriend();
    currRule.generateRecommendations(recommendationList, id, map, numToRecommend);
    if (recommendationList.getRecommendationSize() >= numToRecommend) {
      // recommendation finished for this user
      return;
    }
    currRule = new Rule3FollowInfluencer();
    currRule.generateRecommendations(recommendationList, id, map, numToRecommend);
    if (recommendationList.getRecommendationSize() >= numToRecommend) {
      // recommendation finished for this user
      return;
    }
    currRule = new Rule4FollowRandomUser();
    currRule.generateRecommendations(recommendationList, id, map, numToRecommend);
    return;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(this.getClass().isInstance(object))) {
      return false;
    }
    RecommendationSystem other = (RecommendationSystem) object;
    return this.allRecommendations.equals(other.allRecommendations)
        && (this.nodeCsv.equals(other.nodeCsv))
        && (this.edgeCsv.equals(other.edgeCsv))
        && (this.numToProcess == other.numToProcess)
        && (this.numToRecommend == other.numToRecommend);
  }

  @Override
  public int hashCode() {
    int result = HASHCODE_INITIAL;
    result = HASHCODE_COEFFICIENT * result + allRecommendations.hashCode();
    result = HASHCODE_COEFFICIENT * result + nodeCsv.hashCode();
    result = HASHCODE_COEFFICIENT * result + edgeCsv.hashCode();
    result = HASHCODE_COEFFICIENT * result + numToProcess;
    result = HASHCODE_COEFFICIENT * result + numToRecommend;
    return result;
  }


}
