package edu.neu.ccs.cs5010;

import edu.neu.cs5010.yahtzee.client.GameClient;
import edu.neu.cs5010.yahtzee.messages.ServerMessageParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class YahtzeeClient {
  BufferedReader stdIn;

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("Usage: java KnockKnockClient <host name> <port number>");
      System.exit(1);
    }

    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);
    new GameClient(hostName, portNumber);
  }

  public YahtzeeClient(String hostName, int portNumber) {
    this.stdIn = new BufferedReader(new InputStreamReader(System.in));
    this.runServer(hostName, portNumber);
  }

  private void runServer(String hostName, int portNumber) {
    try {
      Socket kkSocket = new Socket(hostName, portNumber);
      Throwable var4 = null;

      try {
        PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        Throwable var6 = null;

        try {
          BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
          Throwable var8 = null;

          try {
            while(true) {
              String fromServer;
              while((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye.")) {
                  kkSocket.close();
                  System.out.println("fromSErver is null, so closing the socket... ");
                  return;
                }

                String response = this.getResponse(fromServer);
                out.println(response);
              }

              Thread.sleep(100L);
            }
          } catch (Throwable var62) {
            var8 = var62;
            throw var62;
          } finally {
            if (in != null) {
              if (var8 != null) {
                try {
                  in.close();
                } catch (Throwable var61) {
                  var8.addSuppressed(var61);
                }
              } else {
                in.close();
              }
            }

          }
        } catch (Throwable var64) {
          var6 = var64;
          throw var64;
        } finally {
          if (out != null) {
            if (var6 != null) {
              try {
                out.close();
              } catch (Throwable var60) {
                var6.addSuppressed(var60);
              }
            } else {
              out.close();
            }
          }

        }
      } catch (Throwable var66) {
        var4 = var66;
        throw var66;
      } finally {
        if (kkSocket != null) {
          if (var4 != null) {
            try {
              kkSocket.close();
            } catch (Throwable var59) {
              var4.addSuppressed(var59);
            }
          } else {
            kkSocket.close();
          }
        }

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

  private String getResponse(String fromServer) {
    String[] out = fromServer.split(":");
    ServerMessageParser.Frame frame = ServerMessageParser.Frame.fromString(out[0].trim());
    switch(frame) {
      case CHOOSE_SCORE:
        return this.getKeepScore(fromServer);
      case SCORE_CHOICE_INVALID:
        return this.getKeepScore(fromServer);
      case CHOOSE_DICE:
        String userIn = this.getKeepDiceFrame("", fromServer);
        return userIn;
      case INFO:
        return ServerMessageParser.getSingleton().createMessage(ServerMessageParser.Frame.ACK, "ok.");
      case START_TURN:
        return ServerMessageParser.getSingleton().createMessage(ServerMessageParser.Frame.PRINT_GAME_STATE, "pls");
      default:
        return ServerMessageParser.getSingleton().createMessage(ServerMessageParser.Frame.ACK, "ok.");
    }
  }

  private String getKeepScore(String fromServer) {
    try {
      System.out.println("Enter  the Score you want to take:  " + fromServer);

      String userIn;
      for(userIn = this.stdIn.readLine(); userIn.length() <= 1; userIn = this.stdIn.readLine()) {
        System.out.println("Please enter something  ");
      }

      return "SCORE_CHOICE: " + userIn;
    } catch (IOException var3) {
      var3.printStackTrace();
      return "2: 1 1 1 1 1";
    }
  }

  private String getKeepDiceFrame(String fromServer, String userIn) {
    try {
      System.out.println("Enter numbers for the ones you want to keep, 1-5. ");
      userIn = this.stdIn.readLine();
      String[] vals = userIn.trim().split(" ");
      int[] out = new int[]{0, 0, 0, 0, 0};
      StringBuffer buffer = new StringBuffer("KEEP_DICE: 4 4 2 2 4");
      int i;
      if (userIn.trim().length() > 0) {
        for(i = 0; i < vals.length; ++i) {
          int val = Integer.valueOf(vals[i]).intValue();
          if (val >= 1 && val <= 5) {
            out[val - 1] = 1;
          }
        }
      }

      for(i = 0; i < out.length; ++i) {
        if (out[i] == 1) {
          buffer.append(" 1");
        } else {
          buffer.append(" 0");
        }
      }

      return buffer.toString();
    } catch (IOException var8) {
      var8.printStackTrace();
      return "2: 1 1 1 1 1";
    }
  }

}
