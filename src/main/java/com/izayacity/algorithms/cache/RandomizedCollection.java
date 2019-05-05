package com.izayacity.algorithms.cache;

import java.util.*;

public class RandomizedCollection {
	public List<Integer> list;
	public Map<Integer, Set<Integer>> map;
	private Random rand;

	/** Initialize your data structure here. */
	public RandomizedCollection() {
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
		this.rand = new Random();
	}

	/** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
	public boolean insert(int val) {
		boolean notContains = !this.map.containsKey(val);
		int size = this.list.size();

		if(notContains) {
			this.map.put(val, new HashSet<>());
		}
		this.map.get(val).add(size);
		this.list.add(val);

		return notContains;
	}

	/** Removes a value from the collection. Returns true if the collection contained the specified element. */
	public boolean remove(int val) {
		if(!this.map.containsKey(val)) {
			return false;
		}
		int size = this.list.size();
		int loc = this.map.get(val).iterator().next();
		this.map.get(val).remove(loc);

		if(loc < size - 1) {
			int listLast = this.list.get(size - 1);
			this.list.set(loc, listLast);
			this.map.get(listLast).remove(size - 1);
			this.map.get(listLast).add(loc);
		}
		this.list.remove(size - 1);

		if(this.map.get(val).isEmpty()) {
			this.map.remove(val);
		}
		return true;
	}

	/** Get a random element from the collection. */
	public int getRandom() {
		return this.list.get(this.rand.nextInt(this.list.size()));
	}
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
