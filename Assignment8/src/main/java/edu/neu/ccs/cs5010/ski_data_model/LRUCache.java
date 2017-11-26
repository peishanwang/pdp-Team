package edu.neu.ccs.cs5010.ski_data_model;

import java.util.HashMap;

public class LRUCache<K, V> {
  class DLLNode {
    K key;
    V val;
    DLLNode next;
    DLLNode pre;
    DLLNode(K key, V val){
      this.key=key;
      this.val=val;
    }
  }
  HashMap<K, DLLNode> hashmap;
  DLLNode head, tail;
  int capacity, count;


  public LRUCache(int capacity) {
    this.capacity = capacity;
    count = 0;
    hashmap = new HashMap<>();
    head=new DLLNode(null,null);
    tail = new DLLNode(null,null);
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

  public V get(K key) {
    if(hashmap.containsKey(key)){
      DLLNode node = hashmap.get(key);
      deleteNode(node);
      addToHead(node);
      return node.val;
    }
    return null;
  }

  public void put(K key, V value) {
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

  public static void main(String[] args) {
    LRUCache<String, String> cache = new LRUCache<>(2);
    cache.put("a", "1");
    cache.put("b", "2");
    System.out.println(cache.get("a"));
    cache.put("c", "3");
    System.out.println(cache.get("b"));
  }
}
