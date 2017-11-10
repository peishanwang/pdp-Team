package edu.neu.ccs.cs5010;

import java.io.*;
import java.util.*;

public abstract class RecommendationSystem implements IRecommendationSystem{
  private static final String NODE_ID = "Node ID";
  private static final String RECOMMENDED_NODES = "Recommended nodes";
  private static final String ENCODING = "UTF-8";
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
    for(UserRecommendationList userRecommendationList:allRecommendations){
      System.out.print("User: " + userRecommendationList.getUserId() + " -> [");
      for (Integer nodeId : userRecommendationList.getRecommendations()) {
        System.out.print(nodeId + ", ");

      }
      System.out.println("]");

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
    } else if (cmdParser.getProcessingFlag() == 'e'){
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

  public void initializeSystem() throws IOException {
    SocialNetworkUsersMap socialNetworkUsersMap = new SocialNetworkUsersMap();

    // read node data
    BufferedReader reader = new BufferedReader(new FileReader(new File(nodeCsv)));
    String line = reader.readLine();
    if (line == null) {
      throw new IllegalArgumentException("Node File doesn't contain header");
    }
    while ((line = reader.readLine()) != null) {
      String cols[] = line.split(",");
      socialNetworkUsersMap.addNode(Integer.parseInt(cols[0]), cols[1], cols[2], Integer.parseInt(cols[3]), cols[4]);
    }
    reader.close();


    // read edge data
    reader = new BufferedReader(new FileReader(new File(edgeCsv)));
    line = reader.readLine();
    if (line == null) {
      throw new IllegalArgumentException("Edge File doesn't contain header");
    }
    while ((line = reader.readLine()) != null) {
      String cols[] = line.split(",");
      socialNetworkUsersMap.addEdge(Integer.parseInt(cols[0]), Integer.parseInt(cols[1]));
    }
    reader.close();
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
  UserRecommendationList giveRecommendation(UserRecommendationList recommendationList,
                          int Id,
                          Map<Integer, GraphNode> map) {
    Rule currRule;
    currRule = new Rule1GetFriendWithMaxFriends();
    recommendationList = currRule.generateRecommendations(recommendationList, Id, map, numToRecommend);
    if (recommendationList.getRecommendationSize() >= numToRecommend) {
      // recommendation finished for this user
      return recommendationList;
    }
    currRule = new Rule2FriendOfFriend();
    recommendationList = currRule.generateRecommendations(recommendationList, Id, map, numToRecommend);
    if (recommendationList.getRecommendationSize() >= numToRecommend) {
      // recommendation finished for this user
      return recommendationList;
    }
    currRule = new Rule3FollowInfluencer();
    recommendationList = currRule.generateRecommendations(recommendationList, Id, map, numToRecommend);
    if (recommendationList.getRecommendationSize() >= numToRecommend) {
      // recommendation finished for this user
      return recommendationList;
    }
    currRule = new Rule4FollowRandomUser();
    currRule.generateRecommendations(recommendationList, Id, map, numToRecommend);
    return recommendationList;
  }


}
