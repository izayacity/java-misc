package com.izayacity.algorithms.dp;

import java.util.Arrays;

public class MatrixPath {
    //	Time complexity of the above solution is O(n2).
//	All values of dp[i][j] are computed only once.
    public int longestOverall(int matrix[][]) {
        int res = 1;
        int n = matrix.length;
        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == -1) {
                    longestFromCell(i, j, matrix, dp);
                }
                res = Math.max(res, dp[i][j]);
            }
        }
        return res;
    }

    int longestFromCell(int i, int j, int matrix[][], int dp[][]) {
        int n = matrix.length;

        if (i < 0 || i >= n || j < 0 || j >= n) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        if (j < n - 1 && ((matrix[i][j] + 1) == matrix[i][j + 1]))
            return dp[i][j] = 1 + longestFromCell(i, j + 1, matrix, dp);

        if (j > 0 && (matrix[i][j] + 1 == matrix[i][j - 1]))
            return dp[i][j] = 1 + longestFromCell(i, j - 1, matrix, dp);

        if (i > 0 && (matrix[i][j] + 1 == matrix[i - 1][j]))
            return dp[i][j] = 1 + longestFromCell(i - 1, j, matrix, dp);

        if (i < n - 1 && (matrix[i][j] + 1 == matrix[i + 1][j]))
            return dp[i][j] = 1 + longestFromCell(i + 1, j, matrix, dp);

        // If none of the adjacent fours is one greater
        return dp[i][j] = 1;
    }
}
