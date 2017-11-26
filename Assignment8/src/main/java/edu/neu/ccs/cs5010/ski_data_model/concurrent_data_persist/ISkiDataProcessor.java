package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import edu.neu.ccs.cs5010.generateDatFile.SkiDataProcessor;

/**
 * This is interface of SkiDataProcessor.
 *
 * @see SkiDataProcessor
 */
public interface ISkiDataProcessor {
  /**
   * Process data from .csv file.
   */
  void processData();
}
