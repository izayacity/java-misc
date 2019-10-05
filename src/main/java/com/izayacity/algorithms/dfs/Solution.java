package com.izayacity.algorithms.dfs;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        10/5/19
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
class Solution {

    private final static int WHITE = 0;
    private final static int GRAY = 1;
    private final static int BLACK = 2;

    private int[] rowArr = new int[]{-1, 0, 1, 0};
    private int[] colArr = new int[]{0, -1, 0, 1};

    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        int[][] visited = new int[grid.length][grid[0].length];
        int result = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (visited[i][j] != WHITE || grid[i][j] == '0') {
                    continue;
                }
                result++;
                dfs(grid, i, j, visited);
            }
        }
        return result;
    }

    private void dfs(char[][] grid, int row, int col, int[][] visited) {
        visited[row][col] = GRAY;
        for (int i = 0; i < rowArr.length; i++) {
            int rowNext = row + rowArr[i];
            int colNext = col + colArr[i];

            if (checkSearchable(grid, rowNext, colNext, visited)) {
                dfs(grid, rowNext, colNext, visited);
            }
        }
        visited[row][col] = BLACK;
    }

    private boolean checkSearchable(char[][] grid, int i, int j, int[][] visited) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == '1' && visited[i][j] == WHITE;
    }
}