package edu.neu.ccs.cs5010;

/**
 * CmdParser class is used to parse arguments from command line.
 */
public class CmdParser {
  private static final int NUM_ARGS = 2;
  private static final int HOST_INDEX = 0;
  private static final int PORT_INDEX = 1;
  private String hostName;
  private int portNumber;

  /**
   * CmdParser Constructor.
   * @param args command line arguments
   * @throws IllegalArgumentException Exception thrown if arguments are less than 2
   */
  public CmdParser(String[] args) throws IllegalArgumentException {
    if (args.length != NUM_ARGS) {
      System.err.println("Usage: java YahtzeeClient <host name> <port number>");
      throw new IllegalArgumentException();
    }

    hostName = args[HOST_INDEX];
    portNumber = Integer.parseInt(args[PORT_INDEX]);
  }

  public String getHostName() {
    return hostName;
  }

  public int getPortNumber() {
    return portNumber;
  }
}
