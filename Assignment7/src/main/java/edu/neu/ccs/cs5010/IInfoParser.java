package edu.neu.ccs.cs5010;

/**
 * Interface of InfoParser.
 *
 * @see InfoParser
 */
public interface IInfoParser {
  /**
   * Parse information from .csv file using specific consumer.
   * @param path .csv file's path.
   * @param rideInfoConsumer to use the information from .csv file.
   */
  void parseInfo(String path, IRideInfoConsumer rideInfoConsumer);

}
