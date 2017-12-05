package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;


public class ServerInfoHandlerTest {
  private static final String PRINT_FRAME = "PRINT_GAME_STATE: PRINT!";
  private static final String DO_NOTHING = "INFO: nothing to do.";
  private static final String CHOOSE_DICE = "CHOOSE_DICE: 1 2 3 4 5";
  private static final String CHOOSE_SCORE = "CHOOSE_SCORE: 1 2 3 4 5";
  private static final String[] EMPTY = new String[]{"\n"};
  private static final String[] YES = new String[]{"a", "yes"};
  private static final String[] NO = new String[]{"a", "no"};
  private static final String ROUND_OVER = "ROUND_OVER: ";
  private static int index;

  private void helper(String testString, String[] inputStrings) {
    index = 0;
    ServerInfoHandler handler = new ServerInfoHandler(testString) {
      @Override
      protected void setReader() {
        reader = new IoUtil(new ByteArrayInputStream(inputStrings[index].getBytes()));
        index++;
      }
    };

    if (testString.equals(CHOOSE_DICE) && Arrays.equals(inputStrings, EMPTY)) {
      Assert.assertEquals("KEEP_DICE: 1 2 3 4 5 0 0 0 0 0", handler.getResponse());
    } else if (testString.equals(CHOOSE_DICE)) {
      Assert.assertEquals("KEEP_DICE: 1 2 3 4 5 1 1 1 0 0", handler.getResponse());
    } else if (testString.equals(CHOOSE_SCORE)) {
      Assert.assertEquals("SCORE_CHOICE: Aces", handler.getResponse());
    } else if (testString.equals(ROUND_OVER) && Arrays.equals(inputStrings, YES)) {
      Assert.assertEquals(PRINT_FRAME, handler.getResponse());
    } else if (testString.equals(ROUND_OVER) && Arrays.equals(inputStrings, NO)) {
      Assert.assertEquals(DO_NOTHING, handler.getResponse());
    }
  }

  @Test
  public void testAskPrint() {
    helper(ROUND_OVER, YES);
    helper(ROUND_OVER, NO);
  }

  @Test
  public void testDice() {
    helper(CHOOSE_DICE, new String[]{"abc", "6 2", "-1 2", "1 2 3"});
    helper(CHOOSE_DICE, EMPTY);
  }

  @Test
  public void testKeepScore() {
    helper(CHOOSE_SCORE, new String[]{"a", "Aces"});
  }
}
