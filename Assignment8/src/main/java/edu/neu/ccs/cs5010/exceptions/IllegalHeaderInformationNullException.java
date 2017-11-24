package edu.neu.ccs.cs5010.exceptions;

/**
 * Class to throw exception if header of csv file null.
 */
public class IllegalHeaderInformationNullException extends RuntimeException {

  /**
   * IllegalHeaderInformationNullException Constructor.
   * @param str information to print
   */
  public IllegalHeaderInformationNullException(String str) {
    super(str);
  }
}
