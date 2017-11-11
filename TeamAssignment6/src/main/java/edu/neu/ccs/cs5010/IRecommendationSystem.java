package edu.neu.ccs.cs5010;

/**
 * IRecommendationSystem interface to generate recommendations and output results.
 */
public interface IRecommendationSystem {

  /**
   * method to read node and edge files.
   * this method will also call method to add nodes into a hashmap
   */
  void initializeSystem();

  /**
   * Method to generate recommendations.
   */
  void generateRecommendation();

  /**
   * method to output results on console.
   * @param path path of node file
   * @param encoding file encoding used
   */
  void outputResult(String path, String encoding);

  /**
   * method to print results on console.
   */
  void printResult();
}
