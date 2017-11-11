package edu.neu.ccs.cs5010;

import java.util.HashMap;
import java.util.Map;

public class SocialNetworkUsersMap {
  public static Map<Integer, GraphNode> idToNodeMap = new HashMap<>();

  public static void addNode(int nodeId, String creationDate, String gender, int age, String city) {
    idToNodeMap.put(nodeId, new GraphNode(nodeId, creationDate, gender, age, city));
  }

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
