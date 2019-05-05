package com.izayacity.algorithms.dp;

import java.util.Arrays;

// https://www.geeksforgeeks.org/knapsack-problem/
public class Knapsack {
    // O(nW) where n is the number of items and W is the capacity of knapsack.
    int knapSack(int W, int wt[], int val[], int n) {
        int dp[][] = new int[n + 1][W + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return knapSackUtil(W, wt, val, n, dp);
    }

    int knapSackUtil(int W, int wt[], int val[], int n, int[][] dp) {
        if (n == 0 || W == 0) {
            return 0;
        }
        if (dp[n][W] != -1) {
            return dp[n][W];
        }
        if (wt[n - 1] > W) {
            return knapSackUtil(W, wt, val, n - 1, dp);
        }
        return dp[n][W] = Math.max(val[n - 1] + knapSackUtil(W - wt[n - 1], wt, val, n - 1, dp),
                knapSackUtil(W, wt, val, n - 1, dp));
    }

    // Time complexity of this naive recursive solution is exponential (2^n).
    int knapSackNaive(int W, int wt[], int val[], int n) {
        if (n == 0 || W == 0) {
            return 0;
        }
        if (wt[n - 1] > W) {
            return knapSackNaive(W, wt, val, n - 1);
        }
        // Return the maximum of two cases:
        // (1) nth item included
        // (2) not included
        return Math.max(val[n - 1] + knapSackNaive(W - wt[n - 1], wt, val, n - 1),
                knapSackNaive(W, wt, val, n - 1));
    }
}
