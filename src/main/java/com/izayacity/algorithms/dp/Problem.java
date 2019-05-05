package com.izayacity.algorithms.dp;

import java.util.Arrays;
import java.util.List;

/**
 * Author:         Francis Xirui Yang
 * Date:            2/23/19
 * Time:            2:57 PM
 * Version:        1.0
 * Email:           izayacity@gmail.com
 * Description: dp
 */
public class Problem {
    public Problem() {
    }

    ////	Problem 1
////	Given 3 numbers {1, 3, 5}, we need to tell the total number of ways we can form a number 'N'
////	using the sum of the given three numbers.(allowing repetitions and different arrangements).
////	Total number of ways to form 6 is: 8
////			1+1+1+1+1+1
////			1+1+1+3
////			1+1+3+1
////			1+3+1+1
////			3+1+1+1
////			3+3
////			1+5
////			5+1
    public int formNumber(int n, List<Integer> list) {
        if (n == 0 || list == null || list.size() == 0) {
            return 0;
        }
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return formNumberUtil(n, list, dp);
    }

    private int formNumberUtil(int n, List<Integer> list, int[] dp) {
        if (n < 0) {
            return 0;
        }
        if (dp[n] != -1) {
            return dp[n];
        }
        dp[n] = 0;
        for (int val : list) {
            dp[n] += formNumberUtil(n - val, list, dp);
        }
        return dp[n];
    }

    ////	Problem 2
//	The Longest Increasing Subsequence (LIS) problem is to find the length of the longest subsequence
// of a given sequence such that all elements of the subsequence are sorted in increasing order.
// For example, the length of LIS for {10, 22, 9, 33, 21, 50, 41, 60, 80} is 6 and LIS is {10, 22, 33, 50, 60, 80}.
    public int lis(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[][] dp = new int[arr.length][arr.length];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
        return lisUtil(arr, 0, arr.length - 1, dp);
    }

    private int lisUtil(int[] arr, int start, int end, int[][] dp) {
        if (start > end) {
            return 0;
        }
        if (start == end) {
            return 1;
        }
        if (dp[start][end] != -1) {
            return dp[start][end];
        }
        return dp[start][end] = Math.max(lisUtil(arr, start, end - 1, dp) + (arr[end - 1] <= arr[end] ? 1 : 0),
                lisUtil(arr, start + 1, end, dp) + (arr[start] <= arr[start + 1] ? 1 : 0));
    }

    ////	Problem 3
    // n is the number of eggs
    // k is the number of floors
    public int eggDropping(int n, int k) {
        if (n <= 0 || k <= 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return eggDroppingUtil(n, k, dp);
    }

    private int eggDroppingUtil(int n, int k, int[][] dp) {
        if (n <= 0 || k <= 0) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }
        if (n == 1) {
            return k;
        }
        if (dp[n][k] != -1) {
            return dp[n][k];
        }
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= k; i++) {
            int val = 1 + Math.max(eggDroppingUtil(n - 1, i - 1, dp), eggDroppingUtil(n, k - i, dp));
            if (val < res) {
                res = val;
            }
        }
        return dp[n][k] = res;
    }
}
