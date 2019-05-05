package com.izayacity.algorithms.dp;

import java.util.Arrays;

public class LIS {
    int lis(int[] arr, int n) {
        int[] dp = new int[n];
        int max = 0;
        Arrays.fill(dp, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
