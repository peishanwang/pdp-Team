package edu.neu.ccs.cs5010.exceptions;

public class IllegalHeaderInformationNullException extends RuntimeException {

  /**
   * IllegalHeaderInformationNullException Constructor.
   * @param str information to print
   */
  public IllegalHeaderInformationNullException(String str) {
    super(str);
  }
}
