package edu.neu.ccs.cs5010;

import java.io.IOException;

/**
 * PlayYahtzee is main class of Project.
 */
public class PlayYahtzee {

  /**
   * main method of PlayYahtzee.
   * @param args command line arguments
   * @throws IOException IOException
   */
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("Usage: java YahtzeeClient <host name> <port number>");
      System.exit(1);
    }

    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);
    new YahtzeeClient(hostName, portNumber);
  }
}
