package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CmdParserTest {
  private ICmdParser cmdParser1;
  private ICmdParser cmdParser2;
  private ICmdParser cmdParser3;

  @Before
  public void before() {
    cmdParser1 = new CmdParser(new String[] {
        "nodes_small.csv", "edges_small.csv", "outputs.csv", "s", "100", "15"
    });
    cmdParser2 = new CmdParser(new String[] {
        "nodes_small.csv", "edges_small.csv", "outputs.csv", "s", "100", "15"
    });
    cmdParser3 = new CmdParser(new String[] {
        "nodes_10000.csv", "edges_10000.csv", "outputs.csv", "s", "50", "15"
    });
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testException1() {
    cmdParser1 = new CmdParser(new String[] {
        "nodes_small.csv", "edges_small.csv"
    });
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testException2() {
    cmdParser1 = new CmdParser(new String[] {
        "nodes_small.csv", "edges_small.csv", "outputs.csv", "s",
        "100", "15", "hahaha"
    });
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testException3() {
    cmdParser1 = new CmdParser(new String[] {
        "nodes_small.csv", "edges_small.csv", "outputs.csv", "s", "1000", "15"
    });
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testException4() {
    cmdParser1 = new CmdParser(new String[] {
        "nodes_small.csv", "edges_small.csv", "outputs.csv", "l", "100", "15"
    });
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testException5() {
    cmdParser1 = new CmdParser(new String[] {
        "nodes_small.csv", "edges_small.csv", "outputs.csv", "ssss", "100", "15"
    });
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(cmdParser1.hashCode(), cmdParser2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        cmdParser2.hashCode() == cmdParser3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, cmdParser1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, cmdParser1.equals(cmdParser2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, cmdParser2.equals(cmdParser3));
  }

  @Test
  public void testEqualsSame() {
    ICmdParser cmdParser4 = cmdParser1;
    Assert.assertEquals(true, cmdParser1.equals(cmdParser4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IAnalyser analyser = new Analyser();
    Assert.assertEquals(false, cmdParser1.equals(analyser));
  }

}