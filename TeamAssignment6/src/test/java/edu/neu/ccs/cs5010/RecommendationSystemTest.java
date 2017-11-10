package edu.neu.ccs.cs5010;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecommendationSystemTest {
  @Test
  public void main() throws Exception {
    String[] args = new String[] {
        "nodes_small.csv", "edges_small.csv", "outputs.csv", "s", "100", "15"
    };

    String[] args2 = new String[] {
            "nodes_small.csv", "edges_small.csv", "outputs.csv", "e", "100", "15"
    };


    String[] args3 = new String[] {
            "nodes_10000.csv", "edges_10000.csv", "outputs.csv", "r", "100", "15"
    };
    RecommendationSystem.main(args);
    RecommendationSystem.main(args2);
    RecommendationSystem.main(args3);
  }

}