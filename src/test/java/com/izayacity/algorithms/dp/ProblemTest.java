package com.izayacity.algorithms.dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * Author:         Francis Xirui Yang
 * Date:            2/23/19
 * Time:            3:02 PM
 * Version:        1.0
 * Email:           izayacity@gmail.com
 * Description: dp
 */
public class ProblemTest {

    private Problem problem;

    ProblemTest() {
        this.problem = new Problem();
    }

    @Test
    public void formNumber() {
        int n = 6;
        List<Integer> list = Arrays.asList(1, 3, 5);
        int res = this.problem.formNumber(n, list);
        assertEquals(8, res);
    }

    private int[] listToArr(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    @Test
    public void lis() {
        int[] arr = new int[]{10, 22, 9, 33, 21, 50, 41, 60, 80};
        int[] expected = new int[]{10, 22, 33, 50, 60, 80};
        assertEquals(expected.length, this.problem.lis(arr));

        int[] arr1 = new int[]{3, 10, 2, 1, 20};
        int[] expected1 = new int[]{3, 10, 20};
        assertEquals(expected1.length, this.problem.lis(arr1));

        int[] arr2 = new int[]{3, 2};
        assertEquals(1, this.problem.lis(arr2));

        int[] arr3 = new int[]{50, 3, 10, 7, 40, 80};
        int[] expected3 = new int[]{3, 7, 40, 80};
        assertEquals(expected3.length, this.problem.lis(arr3));
    }

    @Test
    public void eggDropping() {
        int res = this.problem.eggDropping(3, 100);
        System.out.println(res);

        int res1 = this.problem.eggDropping(2, 36);
        assertEquals(8, res1);
    }
}