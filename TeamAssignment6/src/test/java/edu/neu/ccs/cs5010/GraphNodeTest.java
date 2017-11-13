package edu.neu.ccs.cs5010;

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
}
