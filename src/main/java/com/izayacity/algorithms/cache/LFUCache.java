package com.izayacity.algorithms.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache {
	public int capacity;
	public int min;
	public HashMap<Integer, Integer> values;
	public HashMap<Integer, Integer> counts;
	public HashMap<Integer, LinkedHashSet<Integer>> lists;

	public LFUCache(int capacity) {
		this.capacity = capacity;
		this.min = 0;
		this.values = new HashMap<>();
		this.counts = new HashMap<>();
		this.lists = new HashMap<>();
	}

	public int get(int key) {
		if(!this.values.containsKey(key)) {
			return -1;
		}
		int count = this.counts.get(key);
		this.counts.put(key, count + 1);
		this.lists.get(count).remove(key);

		if(count == this.min && this.lists.get(count).size() == 0) {
			this.min = count + 1;
			this.lists.remove(count);
		}
		if(!this.lists.containsKey(count + 1)) {
			this.lists.put(count + 1, new LinkedHashSet<>());
		}
		this.lists.get(count + 1).add(key);
		return this.values.get(key);
	}

	public void put(int key, int value) {
		if(this.values.containsKey(key)) {
			this.values.put(key, value);
			get(key);
			return;
		}
		if(this.values.size() == this.capacity) {
			int least = this.lists.get(this.min).iterator().next();
			this.lists.get(this.min).remove(least);
			if(this.lists.get(this.min).size() == 0) {
				this.lists.remove(this.min);
			}
			this.values.remove(least);
			this.counts.remove(least);
		}
		this.values.put(key, value);
		this.counts.put(key, 1);
		this.min = 1;
		if(!this.lists.containsKey(1)) {
			this.lists.put(1, new LinkedHashSet<>());
		}
		this.lists.get(1).add(key);
	}
}
