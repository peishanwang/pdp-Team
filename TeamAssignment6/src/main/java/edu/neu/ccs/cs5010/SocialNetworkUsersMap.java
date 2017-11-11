package edu.neu.ccs.cs5010;

import java.util.HashMap;
import java.util.Map;

/**
 * SocialNetworkUsersMap is class to add nodes and edges to graph.
 */
public class SocialNetworkUsersMap {
  protected static Map<Integer, GraphNode> idToNodeMap = new HashMap<>();

  /**
   * method to add new user to graph.
   * @param nodeId node id of user
   * @param creationDate profile creation date
   * @param gender gender of user
   * @param age age of user
   * @param city city of user
   */
  public static void addNode(int nodeId, String creationDate, String gender, int age, String city) {
    idToNodeMap.put(nodeId, new GraphNode(nodeId, creationDate, gender, age, city));
  }

  /**
   * method to add edge between two users.
   * @param srcNodeId user1 node id
   * @param dstNodeId user2 node id
   */
  public static void addEdge(int srcNodeId, int dstNodeId) {
    GraphNode srcNode = idToNodeMap.get(srcNodeId);
    GraphNode dstNode = idToNodeMap.get(dstNodeId);

    if (srcNode == null || dstNode == null) {
      System.out.println("Ignore relationship for one of non existant node: "
              + srcNodeId + " -> " + dstNodeId);
      return;
    }
    srcNode.addFollower(dstNode);
  }


}
