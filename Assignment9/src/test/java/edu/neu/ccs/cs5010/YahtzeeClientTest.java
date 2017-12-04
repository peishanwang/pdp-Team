package edu.neu.ccs.cs5010;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.*;

public class YahtzeeClientTest {
  private ServerSocket mockServerSocket;
  private Socket mockTestClientSocket;
  private PipedOutputStream oStream;
  private InputStream iStream;

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
      new PlayYahtzee(mockTestClientSocket);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDice() {
    try {
      String testString = "CHOOSE_DICE: 1 2 3 4 5";
      iStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
      when(mockTestClientSocket.getInputStream()).thenReturn(iStream);


      String inputString = "1 2 3";
      new PlayYahtzee(mockTestClientSocket) {
        @Override
        protected boolean checkAndCloseSocket(String check) {
          Assert.assertEquals("KEEP_DICE: 1 2 3 4 5 1 1 1 0 0", check);
          try {
            socket.close();
            System.out.println("Game is over, bye!");
          } catch (IOException e) {
            e.printStackTrace();
          }
          return true;
        }
        @Override
        protected void setStdIn() {
          stdIn = new BufferedReader(new InputStreamReader(
              new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8)
          )));
        }
      };
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
