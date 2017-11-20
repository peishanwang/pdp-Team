package edu.neu.ccs.cs5010;

/**
 * Interface of ResultExtracter.
 *
 * @see edu.neu.ccs.cs5010.ResultExtracter
 */
import java.util.List;

public interface IResultExtracter {
  /**
   * Extract the result for problem1.
   * @return List of String array containing problem result.
   */
  List<Object[]> extractResult1();

  /**
   * Extract the result for problem2.
   * @return List of String array containing problem result.
   */
  List<Object[]> extractResult2();

  /**
   * Extract the result for problem3.
   * @return List of String array containing problem result.
   */
  List<Object[]> extractResult3();
}
