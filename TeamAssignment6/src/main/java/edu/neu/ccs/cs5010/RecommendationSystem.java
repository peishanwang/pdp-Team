package edu.neu.ccs.cs5010;

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

  public static void main(String[] args) {
    ICmdParser cmdParser = new CmdParser(args);
    IRecommendationSystem recommendationSystem = chooseSystem(cmdParser);
    recommendationSystem.initializeSystem();
    recommendationSystem.generateRecommendation();
    recommendationSystem.outputResult(cmdParser.getFileOutput(), ENCODING);
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

  public void initializeSystem() {
    
  }

  public void outputResult(String path, String encoding) {
    ICsvGenerator csvGenerator = new CsvGenerator(Arrays.asList(NODE_ID, RECOMMENDED_NODES));
    for (int i = 0; i < allRecommendations.size(); i++) {
      csvGenerator.addLine(listToMap(allRecommendations.get(i)));
    }
    csvGenerator.generateCsv(path, encoding);
  }

  private HashMap<String, String> listToMap(UserRecommendationList recommendationForOne) {

  }

  //update recommendationList
  void giveRecommendation(UserRecommendationList recommendationList,
                          int Id,
                          Map<Integer, GraphNode> map) {
    Rule currRule;
    currRule = new Rule1();
    recommendationList = currRule.generateRecommendation(recommendationList, Id, map, numToRecommend);
    currRule = new Rule2();
    recommendationList = currRule.generateRecommendation(recommendationList, Id, map, numToRecommend);
    currRule = new Rule3();
    recommendationList = currRule.generateRecommendation(recommendationList, Id, map, numToRecommend);
    currRule = new Rule4();
    recommendationList = currRule.generateRecommendation(recommendationList, Id, map, numToRecommend);
  }
}
