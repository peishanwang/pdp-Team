package edu.neu.ccs.cs5010;


/**
 * ServerInfoHandler is the class that handles all the game operations.
 * i.e handles the responses of server.
 */
public class ServerInfoHandler {
  private static final String DO_NOTHING = "INFO: nothing to do.";
  private static final String SCORE_FRAME = "SCORE_CHOICE: ";
  private static final String DICE_FRAME = "KEEP_DICE:";
  private static final String PRINT_FRAME = "PRINT_GAME_STATE: PRINT!";
  private static final int MIN_INDEX = 1;
  private static final int MAX_INDEX = 5;
  private static final int FRAME_INDEX = 0;
  private static final int PAYLOAD_INDEX = 1;
  private static final String ONE = " 1";
  private static final String ZERO = " 0";
  private static final String YES = "yes";
  private static final String NONO = "no";
  private static final String SPACE_SEPARATOR = " ";
  private static final String COLON_SEPARATOR = ":";
  private static final String GAME_OVER = "GAME_OVER";
  private String fromServer;
  protected IoUtil reader;

  public ServerInfoHandler(String fromServer) {
    this.fromServer = fromServer;
  }

  protected void setReader() {
    reader = new IoUtil(System.in);
  }

  /**
   * method to get response from server.
   * @return appropriate response
   */
  public String getResponse() {
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
    setReader();
    for (userIn = reader.getLine(); userIn.length() <= 1; userIn = reader.getLine()) {
      System.out.println("Please enter something  ");
      setReader();
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
    setReader();
    for (userIn = reader.getLine().toLowerCase();
         !(userIn.equals(YES) || userIn.equals(NONO));
         userIn = reader.getLine().toLowerCase()) {
      System.out.println("Please enter yes or no");
      setReader();
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
      setReader();
      String userIn = reader.getLine();
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
}
