package com.izayacity.algorithms.backtracking;

import java.util.Arrays;

public class Solution {

//Problem: Imagine you are playing a board game. You roll a 6-faced dice
//and move forward the same number of spaces that you rolled.
//If the finishing point is “n” spaces away from the starting point,
//please implement a program that calculates how many possible ways
//there are to arrive exactly at the finishing point? If n=610,
//How many possible ways are there to arrive exactly at the finishing point?
    public long rollDice(int target) {
        int[] nums = {1, 2, 3, 4, 5, 6};
        long[] dp = new long[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        dp[1] = 1;
        return rollDiceUtil(nums, target, 0, dp);
    }

    private long rollDiceUtil(int[] nums, int remain, int start, long[] dp) {
        if (remain <= 0 || start == nums.length || nums[start] > remain) {
            return 0;
        }
        if (nums[start] == remain) {
            return 1;
        }
        if (dp[remain] != -1) {
            return dp[remain];
        }
        return dp[remain] = rollDiceUtil(nums, remain - nums[start], start, dp) + rollDiceUtil(nums, remain, start + 1, dp);
    }

    /* The main function that returns number of ways to get sum 'x' with 'n' dice and 'm' with m faces. */
    public long findWays(int m, int n, int x) {
        long[][] dp = new long[n + 1][x + 1];
        for (int i = 0; i < dp.length; i++) {
            if (0 == i) {
                Arrays.fill(dp[i], 0);
            } if (1 == i) {
                Arrays.fill(dp[i], 1);
                dp[i][0] = 0;
            } else {
                Arrays.fill(dp[i], -1);
                dp[i][0] = 0;
            }
        }
        return findWaysUtil(m, n, x, dp, 1);
    }

    private long findWaysUtil(int m, int n, int x, long[][] dp, int start) {
        if (x <= 0 || n <= 0 || start > m || start > x) {
            return 0;
        }
        if (dp[n][x] != -1) {
            return dp[n][x];
        }
        return dp[n][x] = findWaysUtil(m, n - 1, x - start, dp, 1) + findWaysUtil(m, n, x, dp, start + 1);
    }
}
