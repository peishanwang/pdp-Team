package edu.neu.ccs.cs5010;

public class CmdParser implements ICmdParser{
  private static final int HASHCODE_INITIAL = 17;
  private static final int HASHCODE_COEFFICIENT = 31;
  private static final int MIN_ARGS = 3;
  private static final int MAX_ARGS = 6;
  private static final int NODE_FILE_INDEX = 0;
  private static final int EDGE_FILE_INDEX = 1;
  private static final int OUTPUT_FILE_INDEX = 2;
  private static final int FLAG_INDEX = 3;
  private static final int PROCESS_NUM_INDEX = 4;
  private static final int RECOMMENDATION_NUM_INDEX = 5;
  private static final int DEFAULT_RECOMMENDATION_NUM = 15;
  private static final char FLAG_START = 's';
  private static final char FLAG_END = 'e';
  private static final char FLAG_RANDOM = 'r';
  private static final String SMALL_CSV = "nodes_small.csv";
  private static final String LARGE_CSV = "nodes_10000.csv";
  private static final int SMALL_USER_AMOUNT = 100;
  private static final int LARGE_USER_AMOUNT = 9500;
  private String fileNodes;
  private String fileEdges;
  private String fileOutput;
  private char processingFlag;
  private int numberOfUsersToProcess;
  private int numberOfRecommendations;
  private int totalNumberOfUsers;

  /**
   * CmdParser constructor.
   * @param args arguments passed through command line
   */
  public CmdParser(String[] args) {
    if (args.length < MIN_ARGS) {
      throw new IllegalCmdArgumentException("You didn't give enough arguments.");
    } else if (args.length > MAX_ARGS) {
      throw new IllegalCmdArgumentException("You input too many arguments.");
    }
    fileNodes = args[NODE_FILE_INDEX];
    fileEdges = args[EDGE_FILE_INDEX];
    fileOutput = args[OUTPUT_FILE_INDEX];
    processingFlag = FLAG_START;
    numberOfUsersToProcess = SMALL_USER_AMOUNT;
    numberOfRecommendations = DEFAULT_RECOMMENDATION_NUM;
    if (fileNodes.contains(SMALL_CSV)) {
      totalNumberOfUsers = SMALL_USER_AMOUNT;
    } else if (fileNodes.contains(LARGE_CSV)) {
      totalNumberOfUsers = LARGE_USER_AMOUNT;
    }
    if (args.length > RECOMMENDATION_NUM_INDEX) {
      numberOfRecommendations = Integer.valueOf(args[RECOMMENDATION_NUM_INDEX]);
    }
    if (args.length > PROCESS_NUM_INDEX) {
      numberOfUsersToProcess = Integer.valueOf(args[PROCESS_NUM_INDEX]);
      if (numberOfUsersToProcess > totalNumberOfUsers) {
        throw new IllegalCmdArgumentException("numberOfUsersToProcess must not"
            + "be larger than total number of users.");
      }
    }
    if (args.length > FLAG_INDEX) {
      char flag = args[FLAG_INDEX].charAt(0);
      if (args[FLAG_INDEX].length() != 1 || (flag != FLAG_START
          && flag != FLAG_END && flag != FLAG_RANDOM)) {
        throw new IllegalCmdArgumentException("Your input processingFlag"
            + "is illegal.");
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

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(this.getClass().isInstance(object))) {
      return false;
    }
    CmdParser other = (CmdParser) object;
    return this.fileNodes.equals(other.fileNodes)
        && (this.fileEdges.equals(other.fileEdges))
        && (this.fileOutput.equals(other.fileOutput))
        && (this.processingFlag == other.processingFlag)
        && (this.numberOfUsersToProcess == other.numberOfUsersToProcess)
        && (this.numberOfRecommendations == other.numberOfRecommendations)
        && (this.totalNumberOfUsers == other.totalNumberOfUsers);
  }

  @Override
  public int hashCode() {
    int result = HASHCODE_INITIAL;
    result = HASHCODE_COEFFICIENT * result + fileNodes.hashCode();
    result = HASHCODE_COEFFICIENT * result + fileEdges.hashCode();
    result = HASHCODE_COEFFICIENT * result + fileOutput.hashCode();
    result = HASHCODE_COEFFICIENT * result + processingFlag;
    result = HASHCODE_COEFFICIENT * result + numberOfUsersToProcess;
    result = HASHCODE_COEFFICIENT * result + numberOfRecommendations;
    result = HASHCODE_COEFFICIENT * result + totalNumberOfUsers;
    return result;
  }
}
