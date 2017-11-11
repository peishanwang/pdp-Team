package edu.neu.ccs.cs5010;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GraphNode implements IGraphNode{
  GraphNode(int nodeId, String creationDate, String gender, int age, String city) {
    recommendedTimes = 0;
    this.nodeId = nodeId;

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    try {
      profileCreationDate = dateFormat.parse(creationDate);
    } catch (ParseException e) {
      throw new IllegalArgumentException("creation date not properly formatted.");
    }

    this.gender = Gender.fromString(gender);
    if (this.gender == null) {
      throw new IllegalArgumentException("provided string: " + gender + " is not a valid gender");
    }

    if (age < 0 || age > 100) {
      throw new IllegalArgumentException("Age outside of range [0, 100]");
    }
    this.age = age;

    if (city.isEmpty()) {
      throw new IllegalArgumentException("Provided city is empty.");
    }
    this.city = city;

    this.friendSet = new HashSet<>();
    friendSet.size();
    this.numFollowers = 0;
  }

  public void addFollower(GraphNode node) {
    friendSet.add(node.getNodeId());
    node.numFollowers++;
  }

  public int getNodeId() {
    return nodeId;
  }

  public Set<Integer> getFriends() {
    return friendSet;
  }

  public int getNumFollowers() {
    return numFollowers;
  }

  public Date getProfileCreationDate() {
    return profileCreationDate;
  }

  public void addOneRecommended() {
    recommendedTimes++;
  }

  public int getRecommendedTimes() {
    return recommendedTimes;
  }

  private int nodeId;
  private Set<Integer> friendSet;
  private int numFollowers;
  private Gender gender;
  private int age;
  private Date profileCreationDate;
  private String city;
  private int recommendedTimes;
}

enum Gender {
  MALE("M"), FEMALE("F"), Other("O");

  Gender(String gender) {
    this.genderStr = gender;
  }

  @Override
  public String toString() {
    return this.genderStr;
  }

  public static Gender fromString(String genderStr) {
    for (Gender g : Gender.values()) {
      if (g.toString().equalsIgnoreCase(genderStr)) {
        return g;
      }
    }
    return null;
  }

  private final String genderStr;
}
