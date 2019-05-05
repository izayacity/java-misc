package com.izayacity.algorithms.trie;

public class TrieNode {
    private final int SIZE = 26;
    private TrieNode[] links;
    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[SIZE];
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd() {
        isEnd = true;
    }

    public void put(char ch, TrieNode node) {
        links[ch - 'a'] = node;
    }

    public TrieNode get(char ch) {
        return links[ch - 'a'];
    }

    public boolean contains(char ch) {
        return links[ch - 'a'] != null;
    }
}
