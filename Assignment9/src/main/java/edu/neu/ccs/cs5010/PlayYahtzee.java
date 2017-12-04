package edu.neu.ccs.cs5010;

import java.io.IOException;

/**
 * PlayYahtzee is main class of Project.
 */
public class PlayYahtzee {
  private static final int NUM_ARGS = 2;
  private static final int HOST_INDEX = 0;
  private static final int PORT_INDEX = 1;
  private static final int EXIT_STATUS = 1;

  /**
   * main method of PlayYahtzee.
   * @param args command line arguments
   * @throws IOException IOException
   */
  public static void main(String[] args) throws IOException {
    if (args.length != NUM_ARGS) {
      System.err.println("Usage: java YahtzeeClient <host name> <port number>");
      System.exit(EXIT_STATUS);
    }

    String hostName = args[HOST_INDEX];
    int portNumber = Integer.parseInt(args[PORT_INDEX]);
    new YahtzeeClient(hostName, portNumber);
  }
}
