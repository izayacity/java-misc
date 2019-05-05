package com.izayacity.algorithms.trie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class SolutionTest {

    private Solution solution;

    public SolutionTest() {
        this.solution = new Solution();
    }

    @Test
    public void wordBreak() {
        assertEquals(
                Arrays.asList("cat sand dog", "cats and dog"),
                solution.wordBreak("catsanddog", Arrays.asList("cat", "cats", "and", "sand", "dog"))
        );
        assertEquals(
                Arrays.asList("aaa aaaa", "aaaa aaa"),
                solution.wordBreak("aaaaaaa", Arrays.asList("aaaa", "aaa"))
        );
    }
}