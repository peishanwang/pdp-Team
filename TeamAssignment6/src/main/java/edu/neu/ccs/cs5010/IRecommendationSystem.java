package edu.neu.ccs.cs5010;

import java.util.List;

public interface IRecommendationSystem {
  void initializeSystem();
  void generateRecommendation();
  void outputResult(String path, String encoding);
}
