package edu.neu.ccs.cs5010;

/**
 * This is interface for CmdParser.
 *
 * @see CmdParser
 */
public interface ICmdParser {
  /**
   * Returns .csv file's path.
   * @return .csv file's path.
   */
  String getCsvFile();

  /**
   * Returns flag of the process.
   * @return flag of the process.
   */
  String getFlag();
}
