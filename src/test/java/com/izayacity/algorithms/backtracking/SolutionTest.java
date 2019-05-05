package com.izayacity.algorithms.backtracking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {

    private Solution solution;

    public SolutionTest() {
        solution = new Solution();
    }

    @Test
    public void rollDice() {
        assertEquals(5, solution.rollDice(4));
    }

    @Test
    public void findWays() {
        assertEquals(0, solution.findWays(4, 2, 1));
        assertEquals(2, solution.findWays(2, 2, 3));
        assertEquals(21, solution.findWays(6, 3, 8));
        assertEquals(4, solution.findWays(4, 2, 5));
        assertEquals(6, solution.findWays(4, 3, 5));
    }
}