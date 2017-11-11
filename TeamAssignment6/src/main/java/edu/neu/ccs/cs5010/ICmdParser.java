package edu.neu.ccs.cs5010;

public interface ICmdParser {
  String getFileNodes();

  String getFileEdges();

  String getFileOutput();

  char getProcessingFlag();

  int getNumberOfUsersToProcess();

  int getNumberOfRecommendations();
}
