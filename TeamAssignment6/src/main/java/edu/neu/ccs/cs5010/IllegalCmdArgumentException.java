package edu.neu.ccs.cs5010;

public class IllegalCmdArgumentException extends RuntimeException {
  public IllegalCmdArgumentException(String str) {
    super(str);
    System.out.println("Compulsory arguments:\n" +
        "1.csv file containing node information.\n" +
        "2.csv file containing edge information.\n" +
        "3.filename of the output csv file.\n" +
        "Optional arguments(only can be skipped starting from the end):\n" +
        "1.processingFlag(choose from 's'(from start)/ " +
        "'e'(from end)/ 'r'(randomly choose))\n" +
        "2.numberOfUsersToProcess\n" +
        "3.numberOfRecommendations");
  }
}
