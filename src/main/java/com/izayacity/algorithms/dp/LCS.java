package com.izayacity.algorithms.dp;

public class LCS {
    // O(mn)
    int lcs(char[] X, char[] Y, int m, int n) {
        int L[][] = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    L[i][j] = 0;
                } else if (X[i - 1] == Y[j - 1]) {
                    L[i][j] = L[i - 1][j - 1] + 1;
                } else {
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }
        }
        return L[m][n];
    }

    // O(2^n) in worst case
    int lcsNaive(char[] X, char[] Y, int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        } else if (X[m - 1] == Y[n - 1]) {
            return 1 + lcsNaive(X, Y, m - 1, n - 1);
        } else {
            return Math.max(lcsNaive(X, Y, m, n - 1), lcsNaive(X, Y, m - 1, n));
        }
    }
}
