package edu.neu.ccs.cs5010;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IRecommendationSystem {
  void initializeSystem() throws IOException;
  void generateRecommendation();
  void outputResult(String path, String encoding);

  void printResult();
}
