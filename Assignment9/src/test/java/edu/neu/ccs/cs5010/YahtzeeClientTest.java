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

  private static final String GAME_OVER = "GAME_OVER";
  private Socket mockTestClientSocket;
  private InputStream iStream;


  @Before
  public void setup() {
    // Set it first
    ServerSocket mockServerSocket = mock(ServerSocket.class);
    mockTestClientSocket = mock(Socket.class);

    try {
      // Then mock it
      when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);

      PipedOutputStream oStream = new PipedOutputStream();
      when(mockTestClientSocket.getOutputStream()).thenReturn(oStream);

      when(mockTestClientSocket.isClosed()).thenReturn(false);

    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testGameOver() {
    try {
      String testString = GAME_OVER;
      iStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
      when(mockTestClientSocket.getInputStream()).thenReturn(iStream);
      new YahtzeeClient(mockTestClientSocket);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
