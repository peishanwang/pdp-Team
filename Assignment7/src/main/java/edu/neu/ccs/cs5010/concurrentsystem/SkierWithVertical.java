package edu.neu.ccs.cs5010.concurrentsystem;

public class SkierWithVertical {
  public SkierWithVertical(int skierId, int vertical) {
    this.skierId = skierId;
    this.vertical = vertical;
  }

  public Integer getSkierId() {
    return skierId;
  }

  public Integer getVerticalDistance() {
    return vertical;
  }

  private Integer skierId;
  private Integer vertical;
}
