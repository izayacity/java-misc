package com.izayacity.algorithms.trie;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Solution {

    public Solution() {
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        Trie tree = new Trie();
        // insert each word into the trie
        for (String str : wordDict) {
            tree.insert(str);
        }
        return wordBreakUtil(s, tree);
    }

    public List<String> wordBreakUtil(String s, Trie trie) {
        List<String> result = new ArrayList<String>();
        // check if breakable
        for (int i = s.length() - 1; i >= 0; i--) {
            if (trie.contains(s.substring(0, i + 1))) break;
            else if (i == 0) return result;
        }
        for (int i = 0; i < s.length() - 1; i++) {
            if (trie.contains(s.substring(0, i + 1))) {
                List strs = wordBreakUtil(s.substring(i + 1, s.length()), trie);

                if (!strs.isEmpty()) {
                    for (Iterator<String> it = strs.iterator(); it.hasNext(); ) {
                        result.add(s.substring(0, i + 1) + " " + it.next());
                    }
                }
            }
        }
        if (trie.contains(s)) result.add(s);
        return result;
    }
}
