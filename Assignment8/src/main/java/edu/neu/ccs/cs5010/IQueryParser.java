package edu.neu.ccs.cs5010;

import java.util.List;

/**
 * Interface of QueryParser.
 *
 * @see QueryParser
 */
public interface IQueryParser {
  List<Query> parseInfo(String path, int numberOfQueries);

}
