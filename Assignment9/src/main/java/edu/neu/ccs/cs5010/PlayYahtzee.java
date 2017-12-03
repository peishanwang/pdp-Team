package edu.neu.ccs.cs5010;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PlayYahtzee {

  private BufferedReader stdIn;
  private static final String DO_NOTHING = "INFO: wow";
  private static final String SCORE_FRAME = "SCORE_CHOICE: ";
  private static final String DICE_FRAME = "KEEP_DICE: ";
  private static final String PRINT_FRAME = "PRINT_GAME_STATE: PRINT!";

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("Usage: java YahtzeeClient <host name> <port number>");
      System.exit(1);
    }

    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);
    new PlayYahtzee(hostName, portNumber);
  }

  public PlayYahtzee(String hostName, int portNumber) {
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
          String response = this.getResponse(fromServer);
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

  private String getResponse(String fromServer) {
    String[] out = fromServer.split(":");
    FrameParser.Frame frame = FrameParser.getFrame(out[0]);
    switch(frame) {
      case CHOOSE_SCORE:
        return getKeepScore(fromServer);
      case SCORE_CHOICE_INVALID:
        return getKeepScore(fromServer);
      case CHOOSE_DICE:
        return getKeepDiceFrame(out[1]);
      case ROUND_OVER:
        return askPrint();
      default:
        return DO_NOTHING;
    }
  }

  private String getKeepScore(String fromServer) {
    try {
      System.out.println("Enter the Score you want to take:  " + fromServer);
      String userIn;
      for(userIn = this.stdIn.readLine(); userIn.length() <= 1; userIn = this.stdIn.readLine()) {
        System.out.println("Please enter something  ");
      }

      return SCORE_FRAME + userIn;
    } catch (IOException var3) {
      var3.printStackTrace();
      return DO_NOTHING;
    }
  }

  private String askPrint() {
    try {
      System.out.println("Do you want to print the current state?(yes or no)");
      String userIn;
      for(userIn = this.stdIn.readLine(); userIn.length() <= 1; userIn = this.stdIn.readLine()) {
        System.out.println("Please enter yes or no");
      }
      if (userIn.toLowerCase().equals("yes")) {
        return PRINT_FRAME;
      } else {
        return DO_NOTHING;
      }
    } catch (IOException var3) {
      var3.printStackTrace();
      return DO_NOTHING;
    }
  }

  private String getKeepDiceFrame(String diceValue) {
    try {
      int[] out = new int[]{0, 0, 0, 0, 0};
      StringBuffer buffer = new StringBuffer(DICE_FRAME + diceValue);
      OUTER:
      while (true) {
        System.out.println("Enter numbers for the ones you want to keep, 1-5. ");
        String userIn = this.stdIn.readLine();
        String[] vals = userIn.trim().split(" ");
        if (userIn.trim().length() > 0) {
          for(int i = 0; i < vals.length; ++i) {
            int val = Integer.valueOf(vals[i]);
            if (val >= 1 && val <= 5) {
              out[val - 1] = 1;
            } else {
              System.out.println("INFO : Enter a number between 1 and 5.");
              continue OUTER;
            }
          }
        }
        for(int i = 0; i < out.length; ++i) {
          if (out[i] == 1) {
            buffer.append(" 1");
          } else {
            buffer.append(" 0");
          }
        }

        return buffer.toString();
      }
    } catch (IOException var8) {
      var8.printStackTrace();
      return DO_NOTHING;
    }
  }


}
