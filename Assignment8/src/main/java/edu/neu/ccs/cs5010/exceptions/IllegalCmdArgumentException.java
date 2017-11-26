package edu.neu.ccs.cs5010.exceptions;


import java.util.logging.Logger;

/**
 * Class to throw exception if cmd argument is wrong and also print appropriate instructions.
 */
public class IllegalCmdArgumentException extends RuntimeException {
  private static final Logger LOGGER
      = Logger.getLogger(IllegalArgumentException.class.getName());
  /**
   * IllegalCmdArgumentException Constructor/
   * @param str self explanatory.
   */
  public IllegalCmdArgumentException(String str) {
    super(str);
    LOGGER.info("You need to input following two arguments:\n"
        + "1.csv file containing lift records.\n"
        + "2.choose \"Sequential\" or \"Concurrent\" solution to generate result.\n");
  }
}