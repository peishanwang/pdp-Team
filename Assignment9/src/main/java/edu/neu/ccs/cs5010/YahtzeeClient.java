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
  private static final String DO_NOTHING = "INFO: nothing to do.";
  private static final String SCORE_FRAME = "SCORE_CHOICE: ";
  private static final String DICE_FRAME = "KEEP_DICE:";
  private static final String PRINT_FRAME = "PRINT_GAME_STATE: PRINT!";
  private static final String GAME_OVER = "GAME_OVER";
  private static final String ENCODING = "UTF-8";
  private static final String ONE = " 1";
  private static final String ZERO = " 0";
  private static final String YES = "yes";
  private static final String NO = "no";
  private static final String SPACE_SEPARATOR = " ";
  private static final String COLON_SEPARATOR = ":";
  private static final String SEVER = "Sever: ";
  private static final long INTERVAL = 100L;
  private static final int MIN_INDEX = 1;
  private static final int MAX_INDEX = 5;
  private static final int FRAME_INDEX = 0;
  private static final int PAYLOAD_INDEX = 1;

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
    try (
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
            socket.getOutputStream(), ENCODING), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(
          socket.getInputStream(), ENCODING))
    ) {
      while (true) {
        String fromServer;
        while ((fromServer = input.readLine()) != null) {
          System.out.println(SEVER + fromServer);
          String response = this.getResponse(fromServer);
          if (checkAndCloseSocket(response)) {
            return;
          }
          out.println(response);
        }
        Thread.sleep(INTERVAL);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

  }

  /**
   * method to get response from server.
   * @param fromServer string from server
   * @return appropriate response
   */
  public String getResponse(String fromServer) {
    String[] out = fromServer.split(COLON_SEPARATOR);
    FrameParser.Frame frame = FrameParser.getFrame(out[FRAME_INDEX]);
    switch (frame) {
      case CHOOSE_SCORE:
      case SCORE_CHOICE_INVALID:
        return getKeepScore(fromServer);
      case CHOOSE_DICE:
        return getKeepDiceFrame(out[PAYLOAD_INDEX]);
      case ROUND_OVER:
        return askPrint();
      case GAME_OVER:
        return GAME_OVER;
      default:
        return DO_NOTHING;
    }
  }

  /**
   * method to take score from player.
   * @param fromServer string from server
   * @return score frame.
   */
  private String getKeepScore(String fromServer) {
    System.out.println("Enter the Score you want to take:  " + fromServer);
    String userIn;
    for (userIn = getLine(); userIn.length() <= 1; userIn = getLine()) {
      System.out.println("Please enter something  ");
    }
    return SCORE_FRAME + userIn;
  }

  /**
   * method to ask for printing.
   * @return frame telling what to print.
   */
  private String askPrint() {
    System.out.println("Do you want to print the current state?(yes or no)");
    String userIn;
    for (userIn = getLine().toLowerCase();
        !(userIn.equals(YES) || userIn.equals(NO));
        userIn = getLine().toLowerCase()) {
      System.out.println("Please enter yes or no");
    }
    if (userIn.toLowerCase().equals(YES)) {
      return PRINT_FRAME;
    } else {
      return DO_NOTHING;
    }
  }

  /**
   * method to get dice frame.
   * @param diceValue value of dice
   * @return buffer after keeping dice.
   */
  private String getKeepDiceFrame(String diceValue) {
    int[] out = new int[]{0, 0, 0, 0, 0};
    StringBuilder strb = new StringBuilder(DICE_FRAME + diceValue);
    String[] values;
    OUTER:
    while (true) {
      System.out.println("Enter numbers for the ones you want to keep, 1-5. ");
      String userIn = getLine();
      values = userIn.trim().split(SPACE_SEPARATOR);
      if (userIn.trim().length() > 0) {
        for (int i = 0; i < values.length; i++) {
          try {
            int val = Integer.parseInt(values[i]);
            if (val >= MIN_INDEX && val <= MAX_INDEX) {
              out[val - 1] = 1;
            } else {
              System.out.println("INFO : Enter a number between 1 and 5.");
              continue OUTER;
            }
          } catch (NumberFormatException ex) {
            System.out.println("Wrong entry. String not allowed. Enter a number between 1 and 5");
            continue OUTER;
          }
        }
        break;
      } else {
        break;
      }
    }
    for (int anOut : out) {
      if (anOut == 1) {
        strb.append(ONE);
      } else {
        strb.append(ZERO);
      }
    }
    return strb.toString();
  }

  protected String getLine() {
    try {
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in, ENCODING));
      return stdIn.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return DO_NOTHING;
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
