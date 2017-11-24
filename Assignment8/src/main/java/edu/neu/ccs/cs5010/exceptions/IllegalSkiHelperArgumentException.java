package edu.neu.ccs.cs5010.exceptions;

/**
 * exception if lift id is invalid i.e something different from 1-40.
 */
public class IllegalSkiHelperArgumentException extends RuntimeException {

  /**
   * IllegalSkiHelperArgumentException constructor.
   * @param str error message
   */
  public IllegalSkiHelperArgumentException(String str) {
    super(str);
  }
}
