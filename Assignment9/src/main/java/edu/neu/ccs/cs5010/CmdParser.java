package edu.neu.ccs.cs5010;

public class CmdParser {
  private static final int NUM_ARGS = 2;
  private static final int HOST_INDEX = 0;
  private static final int PORT_INDEX = 1;
  private String hostName;
  private int portNumber;

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
