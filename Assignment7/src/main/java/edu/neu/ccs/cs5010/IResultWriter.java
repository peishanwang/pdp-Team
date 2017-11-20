package edu.neu.ccs.cs5010;

import java.util.List;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public interface IResultWriter {
  void write(String path, List<Object[]> rows);
}
