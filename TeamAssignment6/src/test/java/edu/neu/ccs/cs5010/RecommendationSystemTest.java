package edu.neu.ccs.cs5010;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecommendationSystemTest {
  @Test
  public void main() throws Exception {
    String[] args = new String[] {
        "nodes_small.csv", "edges_small.csv", "outputs.csv", "s", "100", "15"
    };
    RecommendationSystem.main(args);
  }

}