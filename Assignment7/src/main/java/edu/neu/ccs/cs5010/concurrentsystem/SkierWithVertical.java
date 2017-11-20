package edu.neu.ccs.cs5010.concurrentsystem;


import java.util.Objects;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class SkierWithVertical implements ISkierWithVertical{

  /**
   * SkierWithVertical Constructor.
   * @param skierId id of skier
   * @param vertical vertical distance travelled by skier.
   */
  public SkierWithVertical(int skierId, int vertical) {
    this.skierId = skierId;
    this.vertical = vertical;
  }

  /**
   * Method to get id of skier.
   * @return id of skier
   */
  @Override
  public Integer getSkierId() {
    return skierId;
  }

  /**
   * method to get vertical distance.
   * @return vertical distance travelled by skier
   */
  @Override
  public Integer getVerticalDistance() {
    return vertical;
  }

  private Integer skierId;
  private Integer vertical;

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    SkierWithVertical that = (SkierWithVertical) object;
    return Objects.equals(skierId, that.skierId)
            && Objects.equals(vertical, that.vertical);
  }

  @Override
  public int hashCode() {

    return Objects.hash(skierId, vertical);
  }
}
