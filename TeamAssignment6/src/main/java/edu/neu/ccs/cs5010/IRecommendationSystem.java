package edu.neu.ccs.cs5010;


public interface IRecommendationSystem {
  void initializeSystem();

  void generateRecommendation();

  void outputResult(String path, String encoding);

  void printResult();
}
