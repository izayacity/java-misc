package com.izayacity.algorithms.binarySearch;

import org.junit.jupiter.api.Test;

/**
 * Author:         Francis Xirui Yang
 * Date:            3/17/19
 * Time:            5:47 PM
 * Version:        1.0
 * Email:           izayacity@gmail.com
 * Description: binarySearch
 */
public class SolutionTest {

    private Solution solution;

    public SolutionTest() {
        this.solution = new Solution();
    }

    @Test
    public void searchRange() {
        int[] nums = new int[]{5, 7, 7, 8, 8, 10};
        int[] result = this.solution.searchRange(nums, 8);
        for (int val : result) {
            System.out.println(val);
        }
    }
}