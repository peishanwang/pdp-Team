package edu.neu.ccs.cs5010;

/**
 * ICmdParser used to parse command line args.
 */
public interface ICmdParser {
  /**
   * method to get all nodes.
   * @return string having all nodes of csvfile
   */
  String getFileNodes();

  /**
   * method to get all edges.
   * @return string having all edges of csvFile
   */
  String getFileEdges();

  /**
   * method that has output file properties.
   * @return output file
   */
  String getFileOutput();

  /**
   * method to get processing flag.
   * @return char - e/r/s
   */
  char getProcessingFlag();

  /**
   * method to get number of users to be processed.
   * @return number of users to be processed
   */
  int getNumberOfUsersToProcess();

  /**
   * method to get number of recommendations to be generated.
   * @return number of recommendations
   */
  int getNumberOfRecommendations();
}
