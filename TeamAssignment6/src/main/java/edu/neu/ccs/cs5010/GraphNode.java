package edu.neu.ccs.cs5010;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GraphNode {
  GraphNode(int nodeId, String creationDate, String gender, int age, String city) {
    this.nodeId = nodeId;

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    try {
      profileCreationDate = df.parse(creationDate);
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

  void addFollower(GraphNode node) {
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

  int nodeId;
  Set<Integer> friendSet;
  int numFollowers;

  Gender gender;
  int age;
  Date profileCreationDate;
  String city;
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
