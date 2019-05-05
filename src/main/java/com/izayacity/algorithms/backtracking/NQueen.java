package com.izayacity.algorithms.backtracking;

//  A Java program to solve N Queen Problem using backtracking.
public class NQueen {
	private final int N = 8;
	private int version = 0;

    // A utility function to print solution
    private void printSolution(int board[][]) {
		System.out.println("Result " + version);

        for (int i = 0; i < N; i++) {
        	for (int j = 0; j < N; j++) {
        		System.out.print (" " + board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-----------------------------------------");
    }

//    A utility function to check if a queen can be placed on board[row][col]. Note that this function is called when "col"
//    queens are already placed in columns from 0 to col -1. So we need to check only left side for attacking queens
    private boolean isSafe(int board[][], int row, int col) {
    	int i, j;

    	// Check this row on left side
		for (i = 0; i < col; i++) {
			if (board[row][i] == 1)	return false;
		}

		// Check upper diagonal on left side
		for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
			if (board[i][j] == 1)	return false;
		}

		// Check lower diagonal on left side
		for (i = row, j = col; j >= 0 && i < N; i++, j--) {
			if (board[i][j] == 1)	return false;
		}
		return true;
    }

    // A recursive utility function to solve N Queen problem
    private boolean solveNQUtil(int board[][], int col) {
    	// base case: If all queens are placed then return true
    	if (col >= N) {
    		version++;
			printSolution(board);
			return true;
		}

		// Consider this column and try placing this queen in all rows one by one
		for (int i = 0; i < N; i++) {
			// Check if queen can be placed on board[i][col]
			if (isSafe(board, i, col)) {
				// Place this queen in board[i][col]
				board[i][col] = 1;

				// Recur to place rest of the queens, incrementing col each time
				// BackTrack: If placing queen in board[i][col] doesn't lead to a solution then remove queen from board[i][col]
				if (!solveNQUtil(board, col + 1))
					board[i][col] = 0;
			}
		}
		// If queen can not be place in any row in this column col, then return false
		return false;
    }

//    This function solves the N Queen problem using backtracking.  It mainly uses  solveNQUtil() to solve the problem.
//    It returns false if queens cannot be placed, otherwise return true and prints placement of queens in the form of 1s.
//    Please note that there may be more than one solutions, this function prints one of the feasible solutions.
    private void solveNQ() {
		int board[][] = new int[N][N];
		solveNQUtil(board, 0);

		if (version == 0) {
			System.out.println("Solution does not exist.");
		}
		version = 0;
    }

    // Driver program to test above function
    public static void main(String args[]) {
        NQueen Queen = new NQueen();
        Queen.solveNQ();
    }
}
