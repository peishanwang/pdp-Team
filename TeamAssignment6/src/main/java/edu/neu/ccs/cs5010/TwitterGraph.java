package edu.neu.ccs.cs5010;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

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
  class GraphNode {
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

  private List<Integer> generateInfluencers(int maxInfluencers) {
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
    List<Integer> influencersList = generateInfluencers(maxInfluencersCount);
    // TODO take arg to process user in that orders
    List<UserRecommendationList> usersRecommendation = new ArrayList<>();
    Date currentDate = new Date();
    long monthTimeMs = 31L * 24L * 60L * 60L * 1000L;
    for (int i = 1; i <= numUsersToProcess; i++) {
      UserRecommendationList recommendationList = new UserRecommendationList(i);
      usersRecommendation.add(recommendationList);
      // Rule 1: if profile less than 1 month old, find friend which has most friends and
      // follow what he follows
      GraphNode userNode = idToNodeMap.get(i);
      if (userNode == null) {throw new IllegalStateException("invalid nodeId: " + i);}
      // TODO make this check proper if users are from 1 to numUsers
      if ((currentDate.getTime() - userNode.getProfileCreationDate().getTime()) <= monthTimeMs) {
        int maxfriends = 0;
        GraphNode nodeWithMaxfriends = null;
        for (Integer friendsNodeId : userNode.getFriends()) {
          GraphNode friendsNode = idToNodeMap.get(friendsNodeId);
          if (friendsNodeId == null) {throw new IllegalStateException("friendNode not found for id: " + friendsNodeId);}
          if (friendsNode.getFriends().size() > maxfriends) {
            nodeWithMaxfriends = friendsNode;
            maxfriends = nodeWithMaxfriends.getFriends().size();
          }
        }
        if (maxfriends > 0) {
          for (Integer friendsNodeId : nodeWithMaxfriends.getFriends()) {
            if (recommendationList.getRecommendationSize() < numRecommendationsPerUser &&
                    !userNode.getFriends().contains(friendsNodeId)) {
              recommendationList.tryRecommendUser(friendsNodeId);
            }
          }
        }
      }
      if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
        // recommendation finished for this user
        continue;
      }

      // Rule 2: look at all friend of userNode friend and add in increasing order of id
      Set<Integer> fOfFs = new HashSet<>();
      for (Integer friendId : userNode.getFriends()) {
        GraphNode friendNode = idToNodeMap.get(friendId);
        if (friendNode == null) {
          throw new IllegalStateException("friendNode not found for id: " + friendId);
        }
        for (Integer fOfF : friendNode.getFriends()) {
          if (userNode.getNodeId() != fOfF && !userNode.getFriends().contains(fOfF)) {
            fOfFs.add(fOfF);
          }
        }
      }
      // check if already in recommendation list
      List<Integer> fOfFsList = new ArrayList<>(fOfFs);
      fOfFs.clear();
      Collections.sort(fOfFsList);
      for (Integer fOfFId : fOfFsList) {
        recommendationList.tryRecommendUser(fOfFId);
        if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
          break;
        }
      }
      if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
        // recommendation finished for this user
        continue;
      }

      // Rule 3: look at most popular person (influencer) in graph with most followers
      for (Integer nodeId : influencersList) {
        if (!userNode.getFriends().contains(nodeId)) {
          recommendationList.tryRecommendUser(nodeId);
          if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
            break;
          }
        }
      }
      if (recommendationList.getRecommendationSize() >= numRecommendationsPerUser) {
        // recommendation finished for this user
        continue;
      }

      // Rule 4: propose randomly from graph
      Random random = new Random();
      while (recommendationList.getRecommendationSize() < numRecommendationsPerUser) {
        // assuming nodeId are sequentially given
        int randomNodeId = random.nextInt(idToNodeMap.size()) + 1;
        if (!userNode.getFriends().contains(randomNodeId)) {
          recommendationList.tryRecommendUser(randomNodeId);
        }
      }
    }
    return usersRecommendation;
  }

  private Map<Integer, GraphNode> idToNodeMap;



  public static void main(String[] args) throws IOException {
    TwitterGraph graph = new TwitterGraph();

    // read node data
    BufferedReader reader = new BufferedReader(new FileReader(new File("nodes_small.csv")));
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
    reader = new BufferedReader(new FileReader(new File("edges_small.csv")));
    line = reader.readLine();
    if (line == null) {
      throw new IllegalArgumentException("Edge File doesn't contain header");
    }
    while ((line = reader.readLine()) != null) {
      String cols[] = line.split(",");
      graph.addEdge(Integer.parseInt(cols[0]), Integer.parseInt(cols[1]));
    }
    reader.close();

    // run recommendation algorithm
    List<UserRecommendationList> recommendations = graph.generateRecommendation(50, 25, 250);
    for (UserRecommendationList userRecommendationList : recommendations) {
      System.out.print("User: " + userRecommendationList.getUserId() + " -> [");
      for (Integer nodeId : userRecommendationList.getRecommendations()) {
        System.out.print(nodeId + ", ");
      }
      System.out.println("]");
    }
  }
}
