package edu.neu.ccs.cs5010;

import java.io.IOException;

public interface IRecommendationSystem {
  void initializeSystem() throws IOException;
  void generateRecommendation();
  void outputResult(String path, String encoding);

  void printResult();
}
