package com.izayacity.oa.aws;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        10/11/19
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
class SolutionTest {

    private Solution solution;
    private Gson gson;

    public SolutionTest() {
        this.solution = new Solution();
        this.gson = new Gson();
    }

    private boolean listEquals(List<Integer> list1, List<Integer> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Test
    void getPairs() {
        List<int[]> res = this.solution.getPairs(Arrays.asList(
                new int[]{1, 2},
                new int[]{2, 4},
                new int[]{3, 6}
        ), Arrays.asList(
                new int[]{1, 2}
        ), 7);
        System.out.println(gson.toJson(res));

        res = this.solution.getPairs(Arrays.asList(
                new int[]{1, 3},
                new int[]{2, 5},
                new int[]{3, 7},
                new int[]{4, 10}
        ), Arrays.asList(
                new int[]{1, 2},
                new int[]{2, 3},
                new int[]{3, 4},
                new int[]{4, 5}
        ), 10);
        System.out.println(gson.toJson(res));

        res = this.solution.getPairs(Arrays.asList(
                new int[]{1, 8},
                new int[]{2, 7},
                new int[]{3, 14}
        ), Arrays.asList(
                new int[]{1, 5},
                new int[]{2, 10},
                new int[]{3, 14}
        ), 20);
        System.out.println(gson.toJson(res));

        res = this.solution.getPairs(Arrays.asList(
                new int[]{1, 8},
                new int[]{2, 15},
                new int[]{3, 9}
        ), Arrays.asList(
                new int[]{1, 8},
                new int[]{2, 11},
                new int[]{3, 12}
        ), 20);
        System.out.println(gson.toJson(res));
    }

    @Test
    void minCost() {
        Assertions.assertEquals(58, this.solution.minCost(Arrays.asList(8, 4, 6, 12)));
        Assertions.assertEquals(54, this.solution.minCost(Arrays.asList(20, 4, 8, 2)));
        Assertions.assertEquals(224, this.solution.minCost(Arrays.asList(1, 2, 5, 10, 35, 89)));
        Assertions.assertEquals(20, this.solution.minCost(Arrays.asList(2, 2, 3, 3)));
    }

    @Test
    void minSteps() {
        char[][] grid = {{'O', 'O', 'O', 'O'},
                {'D', 'O', 'D', 'O'},
                {'O', 'O', 'O', 'O'},
                {'X', 'D', 'D', 'O'}};
        Assertions.assertEquals(5, this.solution.minSteps(grid, new int[][]{{0, 0}}));

        grid = new char[][]{{'S', 'O', 'O', 'S', 'S'},
                {'D', 'O', 'D', 'O', 'D'},
                {'O', 'O', 'O', 'O', 'X'},
                {'X', 'D', 'D', 'O', 'O'},
                {'X', 'D', 'D', 'D', 'O'}};
        Assertions.assertEquals(3, this.solution.minSteps(grid, new int[][]{
                {0, 0},
                {0, 3},
                {0, 4},
        }));
    }

    @Test
    void findPairWithGivenSum() {
        Assertions.assertTrue(listEquals(Arrays.asList(2, 3), this.solution.findPairWithGivenSum(Arrays.asList(1, 10, 25, 35, 60), 90)));
        Assertions.assertTrue(listEquals(Arrays.asList(1, 5), this.solution.findPairWithGivenSum(Arrays.asList(20, 50, 40, 25, 30, 10), 90)));
        Assertions.assertTrue(listEquals(Arrays.asList(0, 1), this.solution.findPairWithGivenSum(Arrays.asList(5, 55, 40, 20, 30, 30), 90)));
    }

    @Test
    void lattice() {
        Assertions.assertEquals("[2,-1]", this.gson.toJson(this.solution.lattice(-1, 3, 3, 1)));
    }

    @Test
    void favoriteGenre() {
        Map<String, List<String>> userMap = new HashMap<>();
        userMap.put("David", Arrays.asList("song1", "song2", "song3", "song4", "song8"));
        userMap.put("Emma", Arrays.asList("song5", "song6", "song7"));

        Map<String, List<String>> genreMap = new HashMap<>();
        genreMap.put("Rock", Arrays.asList("song1", "song3"));
        genreMap.put("Dubstep", Arrays.asList("song7"));
        genreMap.put("Techno", Arrays.asList("song2", "song4"));
        genreMap.put("Pop", Arrays.asList("song5", "song6"));
        genreMap.put("Jazz", Arrays.asList("song8", "song9"));

        System.out.println(new Solution().favoriteGenre(userMap, genreMap));

        userMap.put("David", Arrays.asList("song1", "song2"));
        userMap.put("Emma", Arrays.asList("song3", "song4"));
        genreMap = new HashMap<>();
        System.out.println(new Solution().favoriteGenre(userMap, genreMap));
    }

    @Test
    void findUniquePairs() {
        int[] nums1 = {1, 1, 2, 45, 46, 46};
        int target1 = 47;
        Assertions.assertEquals(2, this.solution.findUniquePairs(nums1, target1));

        int[] nums2 = {1, 1};
        int target2 = 2;
        Assertions.assertEquals(1, this.solution.findUniquePairs(nums2, target2));
    }

    @Test
    void maximumPath() {
        int[][] matrix = new int[][]{
                {5, 1},
                {4, 5}
        };
        Assertions.assertEquals(4, this.solution.maximumPath(matrix));

        matrix = new int[][]{
                {6, 7, 8},
                {5, 4, 2},
                {8, 7, 6}
        };
        Assertions.assertEquals(5, this.solution.maximumPath(matrix));
    }

    @Test
    void longestPalindrome() {
        Assertions.assertEquals("bb", this.solution.longestPalindrome("cbbd"));
        Assertions.assertEquals("bbabababb", this.solution.longestPalindrome("bbabababbab"));
    }

    @Test
    void twoSumCount() {
        Assertions.assertEquals(3, this.solution.twoSumCount(new int[]{1, 1, 2, 3, 4}, 5));
    }

    @Test
    void computeArea() {
        int res = this.solution.computeArea(0, 2, 2, 0, 1, 3, 3, 1);
        System.out.println(res);

        res = this.solution.computeArea(0, 2, 2, 0, 2, 2, 4, 0);
        System.out.println(res);
    }

    @Test
    void twoSumClosest() {
        int[] res = this.solution.twoSumClosest(new int[]{90, 85, 75, 60, 120, 150, 125}, 220);
        System.out.println(this.gson.toJson(res));
    }
}