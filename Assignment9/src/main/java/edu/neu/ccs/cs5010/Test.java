package edu.neu.ccs.cs5010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {

  private BufferedReader stdIn;

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("Usage: java YahtzeeClient <host name> <port number>");
      System.exit(1);
    }

    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);
    new Test(hostName, portNumber);
  }

  public Test(String hostName, int portNumber) {
    this.stdIn = new BufferedReader(new InputStreamReader(System.in));
    this.runServer(hostName, portNumber);
  }

  private void runServer(String hostName, int portNumber) {
    try (
        Socket kkSocket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
    ) {
      while (true) {
        String fromServer;
        while ((fromServer = in.readLine()) != null) {
          System.out.println("Server: " + fromServer);
          if (fromServer.contains("GAME_OVER")) {
            kkSocket.close();
            System.out.println("Game is over, bye!");
            return;
          }
          String response = stdIn.readLine();
          out.println(response);
        }
        Thread.sleep(100L);
      }
    } catch (UnknownHostException var68) {
      System.err.println("Don't know about host " + hostName);
      System.exit(1);
    } catch (IOException var69) {
      System.err.println("Couldn't get I/O for the connection to " + hostName);
      System.exit(1);
    } catch (InterruptedException var70) {
      var70.printStackTrace();
    }

  }
}
