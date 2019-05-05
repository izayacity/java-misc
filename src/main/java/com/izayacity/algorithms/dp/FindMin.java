package com.izayacity.algorithms.dp;

public class FindMin {
    // Time Complexity = O(n*sum) where n is number of elements and sum is sum of all elements.
    //Note that the above solution is in Pseudo Polynomial Time
    // (time complexity is dependent on numeric value of input).
    public int findMin(int arr[], int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }
        //	dp[i][j] = true if some subset from 1st to i'th has a sum that equals to j
        boolean dp[][] = new boolean[n + 1][sum + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= sum; i++) {
            dp[0][i] = false;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                // if i'th element is excluded
                dp[i][j] = dp[i - 1][j];
                // if i'th element is included, dp[i][j] is true when dp[i - 1][j] is true
                // or dp[i - 1][j - arr[i - 1]] is true
                if (arr[i - 1] <= j) {
                    dp[i][j] |= dp[i - 1][j - arr[i - 1]];
                }
            }
        }
        int diff = Integer.MAX_VALUE;

        // Find the largest j such that dp[n][j]
        // is true where j loops from sum/2 t0 0
        for (int j = sum / 2; j >= 0; j--) {
            if (dp[n][j]) {
                diff = sum - 2 * j;
                break;
            }
        }
        return diff;
    }

    public int findMinNaive(int arr[], int n) {
        int sumTotal = 0;
        for (int i = 0; i < n; i++) {
            sumTotal += arr[i];
        }
        return findMinRec(arr, n, 0, sumTotal);
    }

    // So time complexity will be 2*2*..... *2 (For n times), that is O(2^n).
    public int findMinRec(int arr[], int i, int sumCalculated, int sumTotal) {
        if (i == 0) {
            return Math.abs((sumTotal - sumCalculated) - sumCalculated);
        }
        return Math.min(findMinRec(arr, i - 1, sumCalculated + arr[i - 1],
                sumTotal), findMinRec(arr, i - 1, sumCalculated, sumTotal));
    }
}
