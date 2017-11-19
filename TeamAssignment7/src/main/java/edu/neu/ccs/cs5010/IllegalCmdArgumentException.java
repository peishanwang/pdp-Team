package edu.neu.ccs.cs5010;

/**
 * Class to throw exception if cmd argument is wrong and also print appropriate instructions.
 */
public class IllegalCmdArgumentException extends RuntimeException {
  /**
   * IllegalCmdArgumentException Constructor/
   * @param str self explanatory.
   */
  public IllegalCmdArgumentException(String str) {
    super(str);
    System.out.println("You need to input following two arguments:\n"
        + "1.csv file containing lift records.\n"
        + "2.choose \"Sequential\" or \"Concurrent\" solution to generate result.\n");
  }
}
