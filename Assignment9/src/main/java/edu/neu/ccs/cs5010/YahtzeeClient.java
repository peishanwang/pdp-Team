package edu.neu.ccs.cs5010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * YahtzeeClient class.
 */
public class YahtzeeClient {

  protected Socket socket;
  private static final String GAME_OVER = "GAME_OVER";
  private static final String SEVER = "Sever: ";
  private static final long INTERVAL = 100L;


  /**
   * YahtzeeClient Constructor that takes socket for instantiation.
   * @param socket instance of Socket class
   */
  public YahtzeeClient(Socket socket) {
    this.socket = socket;
    runServer();
  }

  /**
   * YahtzeeClient Constructor to make a client with hostname and portnumber.
   * @param hostName name of host (localhost in our case)
   * @param portNumber port number 1200
   */
  public YahtzeeClient(String hostName, int portNumber) {
    try {
      socket = new Socket(hostName, portNumber);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to " + hostName);
    }
    runServer();
  }

  /**
   * method to run the server.
   */
  private void runServer() {
    try {
      IoUtil input = new IoUtil(socket.getInputStream());
      IoUtil out = new IoUtil(socket.getOutputStream());
      while (true) {
        String fromServer;
        while ((fromServer = input.getLine()) != null) {
          System.out.println(SEVER + fromServer);
          ServerInfoHandler handler = new ServerInfoHandler(fromServer);
          String response = handler.getResponse();
          if (checkAndCloseSocket(response)) {
            return;
          }
          out.writeLine(response);
        }
        Thread.sleep(INTERVAL);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * method to check whether to close socket or not.
   * @param check this string will tell whether game is over or not
   * @return true/false
   */
  protected boolean checkAndCloseSocket(String check) {
    if (check.contains(GAME_OVER)) {
      try {
        socket.close();
        System.out.println("Game is over, bye!");
        return true;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}
