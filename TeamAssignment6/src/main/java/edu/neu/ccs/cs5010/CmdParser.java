package edu.neu.ccs.cs5010;

public class CmdParser implements ICmdParser{
  private String fileNodes;
  private String fileEdges;
  private String fileOutput;
  private char processingFlag;
  private int numberOfUsersToProcess;
  private int numberOfRecommendations;
  private int totalNumberOfUsers;
  public CmdParser(String[] args) {
    if (args.length < 3 || args.length > 6) {
      throw new IllegalArgumentException("");
    }
    fileNodes = args[0];
    fileEdges = args[1];
    fileOutput = args[2];
    processingFlag = 's';
    numberOfUsersToProcess = 100;
    numberOfRecommendations = 15;
    if (fileNodes.contains("nodes_small.csv")) {
      totalNumberOfUsers = 100;
    } else if (fileNodes.contains("nodes_10000.csv")) {
      totalNumberOfUsers = 9500;
    }
    if (args.length == 6) {
      numberOfRecommendations = Integer.valueOf(args[5]);
    }
    if (args.length >= 5) {
      numberOfUsersToProcess = Integer.valueOf(args[4]);
      if (numberOfUsersToProcess > totalNumberOfUsers) {
        throw new IllegalArgumentException("");
      }
    }
    if (args.length >= 4) {
      char flag = args[3].charAt(0);
      if (args[3].length() != 1 || (flag != 's' && flag != 'e' && flag != 'r')) {
        throw new IllegalArgumentException("");
      }
      processingFlag = flag;
    }
  }

  public String getFileNodes() {
    return fileNodes;
  }

  public String getFileEdges() {
    return fileEdges;
  }

  public String getFileOutput() {
    return fileOutput;
  }

  public char getProcessingFlag() {
    return processingFlag;
  }

  public int getNumberOfUsersToProcess() {
    return numberOfUsersToProcess;
  }

  public int getNumberOfRecommendations() {
    return numberOfRecommendations;
  }
}
