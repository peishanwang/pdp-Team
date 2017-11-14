package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GraphNodeTest {
  GraphNode graphNodeWrongDate;
  GraphNode graphNodeWrongAge;
  GraphNode graphNodeWrongAge2;
  GraphNode graphNodeWrongCity;
  GraphNode graphNodeWrongGender;
  private GraphNode graphNode1;
  private GraphNode graphNode2;
  private GraphNode graphNode3;

  @Before
  public void before() {
    graphNode1 = new GraphNode(1,
        "11/11/2017",
        "M",
        12,
        "Seattle");
    graphNode2 = new GraphNode(1,
        "11/11/2017",
        "M",
        33,
        "Seattle");
    graphNode3 = new GraphNode(2,
        "11/11/2017",
        "M",
        12,
        "Seattle");
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(graphNode1.hashCode(), graphNode2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        graphNode2.hashCode() == graphNode3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, graphNode1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, graphNode1.equals(graphNode2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, graphNode2.equals(graphNode3));
  }

  @Test
  public void testEqualsSame() {
    GraphNode graphNode4 = graphNode1;
    Assert.assertEquals(true, graphNode1.equals(graphNode4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IAnalyser analyser = new Analyser();
    Assert.assertEquals(false, graphNode1.equals(analyser));
  }


  @Test(expected = IllegalArgumentException.class)
  public void checkWrongDate(){
    graphNodeWrongDate = new GraphNode(1,
            "wrong date",
            "M",
            12,
            "Seattle");
  }


  @Test(expected = IllegalArgumentException.class)
  public void checkWrongAge(){
    graphNodeWrongAge = new GraphNode(1,
            "11/11/2017",
            "M",
            124,
            "Seattle");
    graphNodeWrongAge2 = new GraphNode(1,
            "11/11/2017",
            "M",
            -6,
            "Seattle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkWrongAge2(){
    graphNodeWrongAge2 = new GraphNode(1,
            "11/11/2017",
            "M",
            -6,
            "Seattle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkWrongGender(){
    graphNodeWrongGender = new GraphNode(1,
            "11/11/2017",
            "k",
            14,
            "Seattle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkWrongCity(){
    graphNodeWrongCity = new GraphNode(1,
            "11/11/2017",
            "M",
            14,
            "");
  }

  @Test
  public void testGetCity() {
    Assert.assertEquals("Seattle", graphNode1.getCity());
  }

  @Test
  public void testGetGender() {
    Assert.assertEquals(Gender.MALE, graphNode1.getGender());
  }

  @Test
  public void testGetAge() {
    Assert.assertEquals(12, graphNode1.getAge());
  }
}
