package edu.neu.ccs.cs5010;

import java.util.HashMap;
import java.util.Map;

public class FrameParser {
  private static final Map<String, FrameParser.Frame> stringToEnum = new HashMap();
  static {
    for (Frame frame : Frame.values()) {
      stringToEnum.put(frame.toString(), frame);
    }
  }

  public static Frame getFrame(String name) {
    return stringToEnum.get(name);
  }


  enum Frame {
    START_GAME,
    START_TURN,
    INVALID_DICE_CHOICE,
    INFO,
    ACK,
    CHOOSE_DICE,
    START_ROUND,
    ROUND_OVER,
    JOIN,
    KEEP_DICE,
    SCORE_CHOICE,
    TURN_OVER,
    CHOOSE_SCORE,
    SCORE_CHOICE_VALID,
    SCORE_CHOICE_INVALID,
    GAME_OVER,
    PRINT_GAME_STATE
  }
}
