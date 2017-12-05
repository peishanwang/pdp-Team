package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Test;

public class CmdParserTest {
  String[] argsWithHostName = {"localhost"};
  String[] argsWithPortNumber = {"1200"};
  String[] correctArguments = {"localhost", "1200"};


  @Test(expected = IllegalArgumentException.class)
  public void checkForJustHostName(){
    CmdParser cmdParserWithHostName = new CmdParser(argsWithHostName);
  }


  @Test(expected = IllegalArgumentException.class)
  public void checkForJustPortNumber(){
    CmdParser cmdParserWithPortNumber = new CmdParser(argsWithPortNumber);
  }

  @Test
  public void checkCorrectArgument(){
    CmdParser correctParser = new CmdParser(correctArguments);

    Assert.assertEquals(correctParser.getHostName(), "localhost");
    Assert.assertEquals(correctParser.getPortNumber(), 1200);
  }


}
