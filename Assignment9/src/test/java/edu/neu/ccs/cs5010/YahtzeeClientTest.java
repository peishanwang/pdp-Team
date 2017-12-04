package edu.neu.ccs.cs5010;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.*;

public class YahtzeeClientTest {
  private static final String PRINT_FRAME = "PRINT_GAME_STATE: PRINT!";
  private static final String DO_NOTHING = "INFO: nothing to do.";
  private static final String CHOOSE_DICE = "CHOOSE_DICE: 1 2 3 4 5";
  private static final String CHOOSE_SCORE = "CHOOSE_SCORE: 1 2 3 4 5";
  private static final String[] EMPTY = new String[]{""};
  private static final String[] YES = new String[]{"a", "yes"};
  private static final String[] NO = new String[]{"a", "no"};
  private static final String ROUND_OVER = "ROUND_OVER: ";
  private ServerSocket mockServerSocket;
  private Socket mockTestClientSocket;
  private PipedOutputStream oStream;
  private InputStream iStream;
  private static int index;

  @Before
  public void setup() {
    // Set it first
    mockServerSocket = mock(ServerSocket.class);
    mockTestClientSocket = mock(Socket.class);

    try {
      // Then mock it
      when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);

      oStream = new PipedOutputStream();
      when(mockTestClientSocket.getOutputStream()).thenReturn(oStream);

      when(mockTestClientSocket.isClosed()).thenReturn(false);

    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testGameOver() {
    try {
      String testString = "GAME_OVER";
      iStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
      when(mockTestClientSocket.getInputStream()).thenReturn(iStream);
      new YahtzeeClient(mockTestClientSocket);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  private void helper(String testString, String[] inputStrings) {
    try {
      iStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
      when(mockTestClientSocket.getInputStream()).thenReturn(iStream);
      index = -1;
      new YahtzeeClient(mockTestClientSocket) {
        @Override
        protected boolean checkAndCloseSocket(String check) {
          if (testString.equals(CHOOSE_DICE) && Arrays.equals(inputStrings, EMPTY)) {
            Assert.assertEquals("KEEP_DICE: 1 2 3 4 5 0 0 0 0 0", check);
          } else if (testString.equals(CHOOSE_DICE)) {
            Assert.assertEquals("KEEP_DICE: 1 2 3 4 5 1 1 1 0 0", check);
          } else if (testString.equals(CHOOSE_SCORE)) {
            Assert.assertEquals("SCORE_CHOICE: Aces", check);
          } else if (testString.equals(ROUND_OVER) && Arrays.equals(inputStrings, YES)) {
            Assert.assertEquals(PRINT_FRAME, check);
          } else if (testString.equals(ROUND_OVER) && Arrays.equals(inputStrings, NO)) {
            Assert.assertEquals(DO_NOTHING, check);
          }

          try {
            socket.close();
            System.out.println("socket closed");
          } catch (IOException e) {
            e.printStackTrace();
          }
          return true;
        }
        @Override
        protected String getLine() {
          index++;
          return inputStrings[index];
        }
      };
    } catch (IOException e) {
      fail(e.getMessage());
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
    helper(CHOOSE_DICE, new String[]{""});
  }

  @Test
  public void testKeepScore() {
    helper(CHOOSE_SCORE, new String[]{"a", "Aces"});
  }
}
