package com.izayacity.algorithms.cache;
import java.util.*;

class LRUCache {

    private int capacity;
    private Map<Integer, CacheNode> dict = new HashMap<>();
    private LinkedList<CacheNode> ll = new LinkedList<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!this.dict.containsKey(key)) {
            return -1;
        }
        CacheNode node = this.dict.get(key);
        this.remove(node);
        this.addFirst(node);

        // Iterator<CacheNode> it = this.ll.iterator();
        // while (it.hasNext()) {
        //     System.out.print(it.next().value + " ");
        // }
        // System.out.println();
        return node.value;
    }

    public void put(int key, int value) {
        if (!this.dict.containsKey(key) && ll.size() >= this.capacity) {
            this.remove(ll.getLast());
        }
        if (this.get(key) == -1) {
            this.addFirst(new CacheNode(key, value));
        } else {
            this.dict.get(key).value = value;
        }
    }

    private void remove(CacheNode node) {
        dict.remove(node.key);
        ll.remove(node);
    }

    private void addFirst(CacheNode node) {
        dict.put(node.key, node);
        ll.addFirst(node);
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // cache is {1=1}
        lRUCache.put(2, 2); // cache is {1=1, 2=2}
        lRUCache.get(1);    // return 1
        lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        lRUCache.get(2);    // returns -1 (not found)
        lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        lRUCache.get(1);    // return -1 (not found)
        lRUCache.get(3);    // return 3
        lRUCache.get(4);    // return 4
    }
}

class CacheNode {
    Integer key;
    Integer value;

    public CacheNode(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }
}