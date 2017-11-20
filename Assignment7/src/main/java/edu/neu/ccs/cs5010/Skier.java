package edu.neu.ccs.cs5010;

/**
 * Class of skier containing information of skier's total vertical.
 */
public class Skier implements ISkier {
  private int skierId;
  private int totalVertical;

  @Override
  public int compareTo(ISkier other) {
    if (other == null) {
      throw new IllegalArgumentException();
    }
    if (this.getTotalVertical() == other.getTotalVertical()) {
      return this.getSkierId() - other.getSkierId();
    } else {
      return other.getTotalVertical() - this.getTotalVertical();
    }
  }

  /**
   * Constructor of Skier.
   * @param skierId skier's id
   */
  public Skier(int skierId) {
    this.skierId = skierId;
  }

  @Override
  public int getSkierId() {
    return skierId;
  }

  @Override
  public void addVertical(int vertical) {
    totalVertical += vertical;
  }

  @Override
  public int getTotalVertical() {
    return totalVertical;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Skier skier = (Skier) obj;

    return getSkierId() == skier.getSkierId();
  }

  @Override
  public int hashCode() {
    return getSkierId();
  }
}
