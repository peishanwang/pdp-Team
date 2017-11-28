package edu.neu.ccs.cs5010.skidatamodel;

import java.util.HashMap;

/**
 * Least recently used cache.
 * @param <K> key
 * @param <V> value
 */
public class LRUCache<K, V> {
  class DLLNode {
    K key;
    V val;
    DLLNode next;
    DLLNode pre;

    DLLNode(K key, V val) {
      this.key = key;
      this.val = val;
    }
  }

  HashMap<K, DLLNode> hashmap;
  DLLNode head;
  DLLNode tail;
  int capacity;
  int count;

 /**
   * Constructor of LRUCache.
   * @param capacity capacity of the cache.
   */
  public LRUCache(int capacity) {
    this.capacity = capacity;
    count = 0;
    hashmap = new HashMap<>();
    head = new DLLNode(null,null);
    tail = new DLLNode(null,null);
    head.next = tail;
    head.pre = null;
    tail.pre = head;
    tail.next = null;

  }

  /**
   * method to delete node from cache.
   * @param node node to be deleted.
   */
  public void deleteNode(DLLNode node) {
    node.pre.next = node.next;
    node.next.pre = node.pre;
  }

  /**
   * method to add to head.
   * @param node node to be added.
   */
  public void addToHead(DLLNode node) {
    node.next = head.next;
    node.next.pre = node;
    node.pre = head;
    head.next = node;
  }

  /**
   * method to get value.
   * @param key key of map.
   * @return value associated
   */
  public V get(K key) {
    if (hashmap.containsKey(key)) {
      DLLNode node = hashmap.get(key);
      deleteNode(node);
      addToHead(node);
      return node.val;
    }
    return null;
  }

  /**
   * Put a key value pair in cache.
   * @param key provided key
   * @param value provided value
   */
  public void put(K key, V value) {
    if (hashmap.containsKey(key)) {
      DLLNode node = hashmap.get(key);
      node.val = value;
      deleteNode(node);
      addToHead(node);
    } else {
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
