package edu.neu.ccs.cs5010;

import java.util.List;

/**
 * Interface of QueryParser.
 *
 * @see QueryParser
 */
public interface IQueryParser {
  /**
   * Parse query information from the csv file.
   * @param path path of the csv file.
   * @return List of queries.
   */
  List<Query> parseInfo(String path);

}
