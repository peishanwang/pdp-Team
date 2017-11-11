package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecommendationSystemTest {
  private IRecommendationSystem system1;
  private IRecommendationSystem system2;
  private IRecommendationSystem system3;

  @Before
  public void before() {
    system1 = new SRecommendationSystem("nodes_small.csv", "edges_small.csv",
        100, 15);
    system2 = new SRecommendationSystem("nodes_small.csv", "edges_small.csv",
        100, 15);
    system3 = new SRecommendationSystem("nodes_small.csv", "edges_small.csv",
        50, 15);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(system1.hashCode(), system2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        system2.hashCode() == system3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, system1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, system1.equals(system2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, system2.equals(system3));
  }

  @Test
  public void testEqualsSame() {
    IRecommendationSystem system4 = system1;
    Assert.assertEquals(true, system1.equals(system4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IRecommendationSystem system5 = new SRecommendationSystem("nodes_small.csv",
        "edges_small.csv", 50, 15);
    Assert.assertEquals(false, system1.equals(system5));
  }

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

  @Test(expected = IllegalArgumentException.class)
  public void testNodeException() {
    IRecommendationSystem systemBad = new SRecommendationSystem(
        "nodes_bad.csv",
        "edges_small.csv",
        100,
        15);
    systemBad.initializeSystem();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEdgeException() {
    IRecommendationSystem systemBad = new SRecommendationSystem(
        "nodes_small.csv",
        "edges_bad.csv",
        100,
        15);
    systemBad.initializeSystem();
  }

}