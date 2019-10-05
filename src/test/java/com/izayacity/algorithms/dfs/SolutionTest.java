package com.izayacity.algorithms.dfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        10/5/19
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
class SolutionTest {

    private Solution solution;

    public SolutionTest() {
        this.solution = new Solution();
    }

    @Test
    void numIslands() {
        char[][] input = new char[][]{{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}};
        int res = solution.numIslands(input);
        Assertions.assertEquals(1, res);

        char[][] input0 = new char[][]{
                {'1', '1', '0'},
                {'0', '1', '1'},
                {'1', '0', '0'}
        };
        int res0 = solution.numIslands(input0);
        Assertions.assertEquals(2, res0);

        char[][] input1 = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'0', '1', '0', '0', '1'},
                {'1', '0', '0', '1', '1'},
                {'0', '0', '0', '0', '0'},
                {'1', '0', '1', '0', '1'}
        };
        int res1 = solution.numIslands(input1);
        Assertions.assertEquals(6, res1);
    }
}