package edu.neu.ccs.cs5010;

public class Skier implements ISkier {
  private int skierId;
  private int totalVertical;

  @Override
  public int compareTo(ISkier other) {
    if (this.getTotalVertical() == other.getTotalVertical()) {
      return this.getSkierId() - other.getSkierId();
    } else {
      return other.getTotalVertical() - this.getTotalVertical();
    }
  }

  public Skier(int skierId){
    this.skierId = skierId;
  }

  public int getSkierId(){
    return skierId;
  }

  public void addVertical(int vertical) {
    totalVertical += vertical;
  }

  public int getTotalVertical() {
    return totalVertical;
  }
}
