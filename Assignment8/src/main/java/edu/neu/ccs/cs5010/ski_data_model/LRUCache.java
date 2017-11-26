package edu.neu.ccs.cs5010.ski_data_model;

import java.util.HashMap;

class DLLNode{
  int key;
  int val;
  DLLNode next;
  DLLNode pre;
  DLLNode(int key, int val){
    this.key=key;
    this.val=val;
  }}
public class LRUCache {
  HashMap<Integer, DLLNode> hashmap;
  DLLNode head, tail;
  int capacity, count;


  public LRUCache(int capacity) {
    this.capacity = capacity;
    count = 0;
    hashmap = new HashMap<>();
    head=new DLLNode(0,0);
    tail = new DLLNode(0,0);
    head.next=tail;
    head.pre =null;
    tail.pre=head;
    tail.next=null;

  }

  public void deleteNode(DLLNode node){
    node.pre.next=node.next;
    node.next.pre=node.pre;
  }

  public void addToHead(DLLNode node){
    node.next = head.next;
    node.next.pre = node;
    node.pre = head;
    head.next = node;
  }

  public int get(int key) {
    if(hashmap.containsKey(key)){
      DLLNode node = hashmap.get(key);
      deleteNode(node);
      addToHead(node);
      return node.val;
    }
    return -1;
  }

  public void put(int key, int value) {
    if(hashmap.containsKey(key)){
      DLLNode node = hashmap.get(key);
      node.val = value;
      deleteNode(node);
      addToHead(node);
    } else{
      DLLNode node = new DLLNode(key, value);
      hashmap.put(key, node);
      if (count < capacity) {
        count++;
        addToHead(node);
      } else {
        hashmap.remove(tail.pre.key);
        deleteNode(tail.pre);
        addToHead(node);
      }
    }

  }
}
