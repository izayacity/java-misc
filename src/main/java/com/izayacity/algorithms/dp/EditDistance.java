package com.izayacity.algorithms.dp;

public class EditDistance {
    int editDist(String str1, String str2, int m, int n) {
        int dp[][] = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    int editDistNaive(String str1, String str2, int m, int n) {
        if (m == 0) {
            return n;
        }
        if (n == 0) {
            return m;
        }
        if (str1.charAt(m - 1) == str2.charAt(n - 1)) {
            return editDistNaive(str1, str2, m - 1, n - 1);
        }
        return 1 + min(editDistNaive(str1, str2, m, n - 1),        // insert
                editDistNaive(str1, str2, m - 1, n),                    // remove
                editDistNaive(str1, str2, m - 1, n - 1));        // replace
    }

    private int min(int i, int i1, int i2) {
        if (i <= i1 && i <= i2) {
            return i;
        } else if (i1 <= i && i1 <= i2) {
            return i1;
        }
        return i2;
    }
}
