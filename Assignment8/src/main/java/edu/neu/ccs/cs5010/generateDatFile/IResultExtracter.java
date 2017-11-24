package edu.neu.ccs.cs5010.generateDatFile;

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
  List<int[]> extractResult1();

  /**
   * Extract the result for problem2.
   * @return List of String array containing problem result.
   */
  List<int[]> extractResult2();

  /**
   * Extract the result for problem3.
   * @return List of String array containing problem result.
   */
  List<int[]> extractResult3();
}
