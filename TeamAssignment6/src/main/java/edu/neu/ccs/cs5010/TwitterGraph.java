package edu.neu.ccs.cs5010;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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

public class TwitterGraph {

  public TwitterGraph() {
    idToNodeMap = new HashMap<>();
  }

  public void addNode(int nodeId, String creationDate, String gender, int age, String city) {
    idToNodeMap.put(nodeId, new GraphNode(nodeId, creationDate, gender, age, city));
  }

  public void addEdge(int srcNodeId, int dstNodeId) {
    GraphNode srcNode = idToNodeMap.get(srcNodeId);
    GraphNode dstNode = idToNodeMap.get(dstNodeId);

    if (srcNode == null || dstNode == null) {
      System.out.println("Ignore relationship for one of non existant node: " + srcNodeId + " -> " + dstNodeId);
      return;
    }
    srcNode.addFollower(dstNode);
  }

  public List<Integer> generateInfluencers(int maxInfluencers) {
    Comparator<GraphNode> followerComparator = new Comparator<GraphNode>() {
      @Override
      public int compare(GraphNode o1, GraphNode o2) {
        return o1.getNumFollowers() - o2.getNumFollowers();
      }
    };
    // min heap of max influencers
    PriorityQueue<GraphNode> influencersPQ = new PriorityQueue<>(maxInfluencers, followerComparator);
    // go over all the nodes in graph and add to queue
    for (GraphNode node : idToNodeMap.values()) {
      if (influencersPQ.size() < maxInfluencers) {
        influencersPQ.add(node);
      } else {
        if (node.getNumFollowers() > influencersPQ.peek().getNumFollowers()) {
          influencersPQ.poll();
          influencersPQ.add(node);
        }
      }
    }

    List<Integer> influencersList = new ArrayList<>(maxInfluencers);
    while (!influencersPQ.isEmpty()) {
      influencersList.add(influencersPQ.poll().getNodeId());
    }
    Collections.sort(influencersList);
    return influencersList;
  }

  public List<UserRecommendationList> generateRecommendation(int numUsersToProcess, int numRecommendationsPerUser, int maxInfluencersCount) {
    // first generate sort influencers list
    // TODO take arg to process user in that orders
    List<UserRecommendationList> usersRecommendation = new ArrayList<>();
    Date currentDate = new Date();
    long monthTimeMs = 31L * 24L * 60L * 60L * 1000L;
    for (int i = 1; i <= numUsersToProcess; i++) {
      UserRecommendationList recommendationList = new UserRecommendationList(i);
      usersRecommendation.add(recommendationList);

      Rule rule = new Rule1GetFriendWithMaxFriends();
      recommendationList = rule.generateRecommendations(recommendationList,
              i,
              idToNodeMap,
              numRecommendationsPerUser);

      if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
        // recommendation finished for this user
        continue;
      }

      rule = new Rule2FriendOfFriend();
      recommendationList = rule.generateRecommendations(recommendationList,
              i,
              idToNodeMap,
              numRecommendationsPerUser);
      if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
        // recommendation finished for this user
        continue;
      }

      rule = new Rule3FollowInfluencer();
      recommendationList = rule.generateRecommendations(recommendationList,
              i,
              idToNodeMap,
              numRecommendationsPerUser);
      if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
        // recommendation finished for this user
        continue;
      }

      // Rule 4: propose randomly from graph
      rule = new Rule4FollowRandomUser();
      recommendationList = rule.generateRecommendations(
              recommendationList,
              i,
              idToNodeMap,
              numRecommendationsPerUser
      );
    }
    return usersRecommendation;
  }

  public Map<Integer, GraphNode> idToNodeMap;



  public static void main(String[] args) throws IOException {
    TwitterGraph graph = new TwitterGraph();

    // read node data
    BufferedReader reader = new BufferedReader(new FileReader(new File("nodes_10000.csv")));
    String line = reader.readLine();
    if (line == null) {
      throw new IllegalArgumentException("Node File doesn't contain header");
    }
    while ((line = reader.readLine()) != null) {
      String cols[] = line.split(",");
      graph.addNode(Integer.parseInt(cols[0]), cols[1], cols[2], Integer.parseInt(cols[3]), cols[4]);
    }
    reader.close();

    // read edge data
    reader = new BufferedReader(new FileReader(new File("edges_10000.csv")));
    line = reader.readLine();
    if (line == null) {
      throw new IllegalArgumentException("Edge File doesn't contain header");
    }
    while ((line = reader.readLine()) != null) {
      String cols[] = line.split(",");
      graph.addEdge(Integer.parseInt(cols[0]), Integer.parseInt(cols[1]));
    }
    reader.close();

    File file = new File("output1.csv");
    BufferedWriter bufferedWriter;
    bufferedWriter = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file.getAbsoluteFile()), StandardCharsets.UTF_8));


    // run recommendation algorithm
    List<UserRecommendationList> recommendations = graph.generateRecommendation(10, 5, 250);
    for (UserRecommendationList userRecommendationList : recommendations) {
      System.out.print("User: " + userRecommendationList.getUserId() + " -> [");
      bufferedWriter.write(userRecommendationList.getUserId() +"-> [");
      for (Integer nodeId : userRecommendationList.getRecommendations()) {
        System.out.print(nodeId + ", ");
        bufferedWriter.write(nodeId + ",");

      }
      System.out.println("]");
      bufferedWriter.write("]");
      bufferedWriter.write("\n");
    }

    bufferedWriter.close();
  }
}
