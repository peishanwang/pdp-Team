package edu.neu.ccs.cs5010;

/**
 * PlayYahtzee is main class of Project.
 */
public class PlayYahtzee {

  /**
   * main method of PlayYahtzee.
   * @param args command line arguments
   */
  public static void main(String[] args) {
    CmdParser parser = new CmdParser(args);
    new YahtzeeClient(parser.getHostName(), parser.getPortNumber());
  }
}
