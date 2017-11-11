package edu.neu.ccs.cs5010;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * GraphNode class signifies the nodes(users) of social network graph.
 */
public class GraphNode implements IGraphNode{

  /**
   * GraphNode constructor.
   * @param nodeId unique id of user
   * @param creationDate date on which this user created profile
   * @param gender gender of user
   * @param age age of user
   * @param city place of residence
   */
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

  /**
   * method to add followee to user list.
   * @param node followee node.
   */
  public void addFollower(GraphNode node) {
    friendSet.add(node.getNodeId());
    node.numFollowers++;
  }

  /**
   * method to get node id.
   * @return node id
   */
  public int getNodeId() {
    return nodeId;
  }

  /**
   * method to get list of people user is following.
   * @return set of people user follows
   */
  public Set<Integer> getFriends() {
    return friendSet;
  }

  /**
   * method to get number of people who are following me.
   * @return number of followers
   */
  public int getNumFollowers() {
    return numFollowers;
  }

  /**
   * method to get profile creation date.
   * @return profile creation date
   */
  public Date getProfileCreationDate() {
    return profileCreationDate;
  }

  /**
   * method to add count fot one more recommendation for this user.
   */
  public void addOneRecommended() {
    recommendedTimes++;
  }

  /**
   * method to get how many times this user was recommended.
   * @return number of times this user was recommended
   */
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


