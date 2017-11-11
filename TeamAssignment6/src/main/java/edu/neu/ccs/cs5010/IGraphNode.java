package edu.neu.ccs.cs5010;

import java.util.Date;
import java.util.Set;

/**
 * IGraphNode interface just signifies the nodes(users) of this social network.
 */
public interface IGraphNode {

  /**
   * method to add followee to user list.
   * @param node followee node.
   */
  void addFollower(GraphNode node);

  /**
   * method to get node id.
   * @return node id
   */
  int getNodeId();

  /**
   * method to get list of people user is following.
   * @return set of people user follows
   */
  Set<Integer> getFriends();

  /**
   * method to get number of people who are following me.
   * @return number of followers
   */
  int getNumFollowers();

  /**
   * method to get profile creation date.
   * @return profile creation date
   */
  Date getProfileCreationDate();


  /**
   * method to add one count fot more recommendation for this user.
   */
  void addOneRecommended();

  /**
   * method to get how many times this user was recommended.
   * @return number of times this user was recommended
   */
  int getRecommendedTimes();
}
