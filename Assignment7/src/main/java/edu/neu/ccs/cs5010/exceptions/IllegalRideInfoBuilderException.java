package edu.neu.ccs.cs5010.exceptions;

/**
 * exception if something is wrong with given information in row of csv file.
 */
public class IllegalRideInfoBuilderException extends RuntimeException {

  /**
   * IllegalRideInfoBuilderException constructor.
   * @param str error message
   */
  public IllegalRideInfoBuilderException(String str) {
    super(str);
  }
}
