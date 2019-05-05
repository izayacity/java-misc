package com.izayacity.algorithms.trie;

public class Trie {
	private TrieNode root;
	private int longest;

	public Trie() {
		root = new TrieNode();
		longest = 0;
	}

	public void insert(String word) {
		TrieNode node = root;

		for (int i = 0; i < word.length(); i++) {
			char curr = word.charAt(i);

			if (!node.contains(curr)) {
				TrieNode newNode = new TrieNode();
				node.put(curr, newNode);
			}
			node = node.get(curr);
		}
		node.setEnd();

		if(word.length() > longest)
			longest = word.length();
	}

	public boolean contains(String word) {
		TrieNode node = searchPrefix(word);
		return node != null && node.isEnd();
	}

	public TrieNode searchPrefix(String prefix) {
		TrieNode node = root;

		for (int i = 0; i < prefix.length(); i++) {
			char curr = prefix.charAt(i);

			if (!node.contains(curr)) {
				return null;
			}
			node = node.get(curr);
		}
		return node;
	}

	public int getLongest() {
		return longest;
	}
}
