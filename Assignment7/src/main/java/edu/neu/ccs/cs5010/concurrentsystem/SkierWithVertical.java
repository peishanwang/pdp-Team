package edu.neu.ccs.cs5010.concurrentsystem;

public class SkierWithVertical implements ISkierWithVertical{
  public SkierWithVertical(int skierId, int vertical) {
    this.skierId = skierId;
    this.vertical = vertical;
  }

  @Override
  public Integer getSkierId() {
    return skierId;
  }

  @Override
  public Integer getVerticalDistance() {
    return vertical;
  }

  private Integer skierId;
  private Integer vertical;
}
