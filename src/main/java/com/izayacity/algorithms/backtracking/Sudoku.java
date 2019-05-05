package com.izayacity.algorithms.backtracking;

//  A Java program to solve Sudoku Problem using backtracking.
public class Sudoku {
	private final int N = 9;
	private final int UNASSIGNED = 0;
	private int version = 0;

	// A utility function to print solution
	private void printMatrix (int matrix[][]) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(matrix[i][j] + " ");

				if (j % 3 == 2 && j != N - 1) {
					System.out.print("| ");
				}
			}
			System.out.println();

			if (i % 3 == 2 && i != N - 1) {
				System.out.println("------------------------");
			}
		}
		System.out.println();
	}

	// Retrieve digits from the string and put them into the matrix;
	// Spaces or other symbols would be filtered;
	// If the string is longer than N*N, the first N*N digits would be used.
	private void strToMatrix (int matrix[][], String str) {
		str = str.trim().replaceAll(" +", "");

		for (int k = 0, i = 0, j = 0; k < str.length(); k++) {
			if (j >= N) {
				j = 0;
				i++;
			}
			matrix[i][j++] = str.charAt(k) - '0';
		}
	}

	private String matrixToStr (int matrix[][]) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == N - 1 && j == N - 1) {
					stringBuilder.append(Integer.toString(matrix[i][j]));
				} else {
					stringBuilder.append(Integer.toString(matrix[i][j])+ ' ');
				}
			}
		}
		return stringBuilder.toString();
	}

	// Returns a boolean which indicates whether it will be legal to assign num to the given row,col location.
	private boolean isSafe (int matrix[][], int row, int col, int num) {
		return !UsedInRow(matrix, row, num) && !UsedInCol(matrix, col, num) && !UsedInBox(matrix, row, col, num);
	}

	// Find if this value has been used in this col
	private boolean UsedInCol (int matrix[][], int col, int num) {
		for (int i = 0; i < N; i++) {
			if (matrix[i][col] == num)
				return true;
		}
		return false;
	}

	// Find if this value has been used in this row
	private boolean UsedInRow (int matrix[][], int row, int num) {
		for (int i = 0; i < N; i++) {
			if (matrix[row][i] == num)
				return true;
		}
		return false;
	}

	// Find if this value has been used in this box
	private boolean UsedInBox (int matrix[][], int row, int col, int num) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matrix[row / 3 * 3 + i][col / 3 * 3 + j] == num)
					return true;
			}
		}
		return false;
	}

	// Find and return the unassigned location
	private int[] findUnassigned (int matrix[][]) {
		int[] ans = {-1, -1};

		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				if (matrix[row][col] == UNASSIGNED) {
					ans[0] = row;
					ans[1] = col;
					return ans;
				}
			}
		}
		return ans;
	}

	// process the matrix to calculate result of empty fields
	private boolean solveSudoku (int matrix[][]) {
		// Get row and col from iterating unassigned locations of the matrix
		int[] ans = findUnassigned(matrix);
		int row = ans[0];
		int col = ans[1];

		if (row == -1 && col == -1) {
			version++;
			return true;
		}

		for (int k = 1; k <= N; k++) {
			if (isSafe(matrix, row, col, k)) {
				matrix[row][col] = k;
				// get the answer
				if (solveSudoku(matrix))
					return true;
				// reset this location after the backtrack false return
				matrix[row][col] = UNASSIGNED;
			}
		}
		return false;
	}

	// Init matrix and input string; process the matrix and invoke the algorithm
	private void init () {
		int matrix[][] = new int[N][N];
		String input = "3 0 6 5 0 8 4 0 0 5 2 0 0 0 0 0 0 0 0 8 7 0 0 0 0 3 1 0 0 3 0 1 0 0 8 0 9 0 0 8 6 3 0 0 5 0 5 0 0 9 0 6 0 0 1 3 0 0 0 0 2 5 0 0 0 0 0 0 0 0 7 4 0 0 5 2 0 6 3 0 0";
		// Retrieve digits from the string and put them into the matrix

		strToMatrix(matrix, input);

		System.out.println("Input Matrix:");
		printMatrix(matrix);

		// Process the matrix to calculate result of empty fields
		if (solveSudoku(matrix)) {
			System.out.println("Result Matrix:");
			printMatrix(matrix);
			System.out.println("Output String:");
			System.out.println(matrixToStr(matrix));
		}

		if (version == 0) {
			System.out.println("Result Matrix does not exist.");
		}
	}

	// Driver program to test above function
	public static void main (String args[]) {
		Sudoku sudoku = new Sudoku();
		sudoku.init();
	}
}
