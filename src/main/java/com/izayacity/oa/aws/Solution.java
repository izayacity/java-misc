package com.izayacity.oa.aws;

import java.util.*;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        10/11/19
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
public class Solution {

    private static final int[][] DIRS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    // Optimal Utilization
    // https://leetcode.com/discuss/interview-question/373202
    // NlgN
    public List<int[]> getPairs(List<int[]> a, List<int[]> b, int target) {
        a.sort((i, j) -> i[1] - j[1]);
        b.sort(Comparator.comparingInt(i -> i[1]));

        List<int[]> result = new ArrayList<>();
        int max = Integer.MIN_VALUE;

        int m = a.size();
        int n = b.size();
        int i = 0;
        int j = n - 1;

        while (i < m && j >= 0) {
            int sum = a.get(i)[1] + b.get(j)[1];
            if (sum > target) {
                j--;
                continue;
            }
            if (max <= sum) {
                if (max < sum) {
                    max = sum;
                    result.clear();
                }
                result.add(new int[]{a.get(i)[0], b.get(j)[0]});
                int index = j - 1;

                while (index >= 0 && b.get(index)[1] == b.get(index + 1)[1]) {
                    result.add(new int[]{a.get(i)[0], b.get(index--)[0]});
                }
            }
            i++;
        }
        return result;
    }

    // Min Cost to Connect Ropes
    // https://leetcode.com/discuss/interview-question/344677
    // https://leetcode.com/discuss/interview-question/344677/Amazon-or-Online-Assessment-2019-or-Min-Cost-to-Connect-Ropes
    // O(NlogN) time complexity and O(N) space
    public int minCost(List<Integer> ropes) {
        Queue<Integer> pq = new PriorityQueue<>(ropes);
        int totalCost = 0;
        while (pq.size() > 1) {
            int cost = pq.poll() + pq.poll();
            pq.add(cost);
            totalCost += cost;
        }
        return totalCost;
    }

    // Treasure Island
    // https://leetcode.com/discuss/interview-question/347457
    // https://leetcode.com/discuss/interview-question/347457/Amazon-or-OA-2019-or-Treasure-Island
    public int minSteps(char[][] grid, int[][] starts) {
        Queue<Point> queue = new ArrayDeque<>();
        for (int[] start : starts) {
            queue.add(new Point(start[0], start[1]));
            // mark as visited
            grid[start[0]][start[1]] = 'D';
        }
        int steps = 1;

        while (!queue.isEmpty()) {
            // the current queue contains all cells with the same level towards the start
            // the for loop below would poll them out from the queue, update visited status,
            // and push their neighbors into the queue for the next level.
            // When we reach the destination, we return the level as result.
            for (int sz = queue.size(); sz > 0; sz--) {
                Point point = queue.poll();

                for (int[] dir : DIRS) {
                    int r = point.row + dir[0];
                    int c = point.col + dir[1];

                    if (!isSafeMinSteps(grid, r, c)) {
                        continue;
                    }
                    if (grid[r][c] == 'X') {
                        return steps;
                    }
                    grid[r][c] = 'D';
                    queue.add(new Point(r, c));
                }
            }
            steps++;
        }
        return -1;
    }

    private static boolean isSafeMinSteps(char[][] grid, int r, int c) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] != 'D';
    }

    private static class Point {
        int row, col;

        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public List<Integer> findPairWithGivenSum(List<Integer> nums, int target) {
        target -= 30;
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> result = Arrays.asList(-1, -1);
        int largest = 0;

        for (int i = 0; i < nums.size(); i++) {
            int complement = target - nums.get(i);
            if ((nums.get(i) > largest || complement > largest) && map.containsKey(complement)) {
                result.set(0, map.get(complement));
                result.set(1, i);
                largest = Math.max(nums.get(i), complement);
            }
            map.put(nums.get(i), i);
        }
        return result;
    }

    public int[] lattice(int ax, int ay, int bx, int by) {
        int dx = bx - ax;
        int dy = by - ay;
        // k1 = dy / dx; k2 = - dx / dy, gradient k1 * k2 = -1 if they are orthogonal vectors from each ohter
        // below is clockwise rotating 90 degree
        int rx = dy;
        int ry = -dx;

        // reduce
        int gcd = Math.abs(gcd(rx, ry));
        rx /= gcd;
        ry /= gcd;

        return new int[]{bx + rx, by + ry};
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    // Approach is simple: initialize a mapping of songsToGenre.
    // Then, using our mapping, we iterate through each song (we don't know if songs have multiple genres, so we handle that here),
    // and count up the genres while keeping a 'maximum count'. After that, we find out all of the songs that are equal to our maximum mapping.
    public Map<String, List<String>> favoriteGenre(Map<String, List<String>> userMap, Map<String, List<String>> genreMap) {
        // name, genres
        Map<String, List<String>> result = new HashMap<>();
        // songName, genres
        Map<String, List<String>> songsToGenre = new HashMap<>();

        // Initialize songsToGenre
        for (Map.Entry<String, List<String>> entry : genreMap.entrySet()) {
            String genre = entry.getKey();
            List<String> songList = entry.getValue();

            for (String song : songList) {
                if (songsToGenre.containsKey(song)) {
                    // add a new genre to the list.
                    songsToGenre.get(song).add(genre);
                } else {
                    // initialize list of songs, and add to list
                    List<String> songGenres = new ArrayList<String>();
                    songGenres.add(genre);
                    songsToGenre.put(song, songGenres);
                }
            }
        }
        // Iterate through userMap, lookup song, and keep a running count for each genre that appears for each song.
        for (Map.Entry<String, List<String>> entry : userMap.entrySet()) {
            String user = entry.getKey();
            List<String> favoriteSongs = entry.getValue();
            // <Genre, Count>
            Map<String, Integer> genreCount = new HashMap<>();
            List<String> favoriteGenres = new ArrayList<String>();
            int maxCount = 0;

            for (String song : favoriteSongs) {
                if (!songsToGenre.containsKey(song)) {
                    continue;
                }
                // Loop through every genre, iterate the count. While we iterate, we check if it's the max value.
                List<String> genresInSong = songsToGenre.get(song);
                for (String genre : genresInSong) {
                    if (!genreCount.containsKey(genre)) {
                        genreCount.put(genre, 1);
                        continue;
                    }
                    // just iterate by 1
                    genreCount.replace(genre, genreCount.get(genre) + 1);
                    // if the getCount is the same as the maxCount, go ahead and add it in to the list.
                    if (maxCount == genreCount.get(genre)) {
                        favoriteGenres.add(genre);
                    } else if (maxCount < genreCount.get(genre)) {
                        // otherwise, clear the favorite genre list, and update the list.
                        favoriteGenres.clear();
                        maxCount = genreCount.get(genre);
                        favoriteGenres.add(genre);
                    }
                }
            }
            result.put(user, favoriteGenres);
        }
        return result;
    }

    public int findUniquePairs(int[] nums, int target) {
        Map<Integer, Boolean> prev = new HashMap<>();
        int pairs = 0;

        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];

            if (!prev.containsKey(diff)) {
                prev.put(nums[i], false);
                continue;
            }
            if (!prev.get(diff)) {
                ++pairs;
                prev.put(diff, true);
            }
            prev.put(nums[i], true);
        }
        return pairs;
    }

    public int maximumPath(int[][] matrix) {
        int l = matrix.length;
        int w = matrix[0].length;

        int minPath = 0;
        boolean[][] visited = new boolean[l][w];
        Stack<int[]> stack = new Stack<>();

        stack.push(new int[]{0, 0, matrix[0][0]});
        visited[0][0] = true;

        while (!stack.isEmpty()) {
            int[] currPos = stack.pop();
            int smallest = currPos[2];

            int[] xDir = new int[]{1, 0};
            int[] yDir = new int[]{0, 1};

            for (int i = 0; i < xDir.length; i++) {
                int newY = currPos[0] + yDir[i];
                int newX = currPos[1] + xDir[i];

                if (newY >= 0 && newY < l && newX >= 0 && newX < w) {
                    if (newX == l - 1 && newY == w - 1) {
                        if (smallest > minPath) {
                            minPath = smallest;
                        }
                        continue;
                    }
                    if (!visited[newY][newX]) {
                        visited[newY][newX] = true;
                        int tmp = smallest;
                        if (matrix[newY][newX] < smallest) {
                            tmp = matrix[newY][newX];
                        }
                        stack.push(new int[]{newY, newX, tmp});
                    }
                }
            }
        }
        return minPath;
    }

    // https://web.archive.org/web/20150313044313/http://articles.leetcode.com/2011/11/longest-palindromic-substring-part-ii.html
    // https://leetcode.com/problems/longest-palindromic-substring/solution/
    // https://medium.com/hackernoon/manachers-algorithm-explained-longest-palindromic-substring-22cb27a5e96f
    public String longestPalindrome(String s) {
        String T = preProcess(s);
        int n = T.length();
        int[] p = new int[n];
        int center = 0, right = 0;

        for (int i = 1; i < n - 1; i++) {
            int j = 2 * center - i;  //j and i are symmetric around center
            p[i] = (right > i) ? Math.min(right - i, p[j]) : 0;

            // Expand palindrome centered at i
            while (T.charAt(i + 1 + p[i]) == T.charAt(i - 1 - p[i])) {
                p[i]++;
            }
            // If palindrome centered at i expand past right,
            // then adjust center based on expand palindrome
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }
        //  Find the longest palindrome
        int maxLength = 0, centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (p[i] > maxLength) {
                maxLength = p[i];
                centerIndex = i;
            }
        }
        centerIndex = (centerIndex - 1 - maxLength) / 2;
        return s.substring(centerIndex, centerIndex + maxLength);
    }

    // preProcess the original string s.
    // For example, s = "abcdefg", then the rvalue = "^#a#b#c#d#e#f#g#$"
    private String preProcess(String s) {
        if (s == null || s.length() == 0) return "^$";
        StringBuilder rvalue = new StringBuilder("^");
        for (int i = 0; i < s.length(); i++)
            rvalue.append("#").append(s.substring(i, i + 1));
        rvalue.append("#$");
        return rvalue.toString();
    }

    public int twoSumCount(int[] nums, int target) {
        if (nums == null || nums.length < 2)
            return 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i]))
                count += map.get(target - nums[i]);
            if (!map.containsKey(nums[i]))
                map.put(nums[i], 1);
            else map.put(nums[i], map.get(nums[i]) + 1);
        }
        return count;
    }

    // Overlap Rectangle
    // Rect 1: top-left(A, B), bottom-right(C, D)
    // Rect 2: top-left(E, F), bottom-right(G, H)
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int innerL = Math.max(A, E);
        int innerR = Math.max(innerL, Math.min(C, G));
        int innerT = Math.max(B, F);
        int innerB = Math.max(innerT, Math.min(D, H));
        return (C - A) * (B - D) - (innerR - innerL) * (innerT - innerB) + (G - E) * (F - H);
    }

    public int[] twoSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }
        if (nums.length == 2) {
            return nums;
        }
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            indexMap.put(nums[i], i);
        }
        int[] res = new int[2];
        Arrays.sort(nums);

        int pr = nums.length - 1;
        int minDiff = Integer.MAX_VALUE;

        for (int pl = 0; pl < pr; pl++) {
            while (nums[pl] + nums[pr] > target) {
                pr--;
            }
            int sum = nums[pl] + nums[pr];
            int diff = target - sum;

            if (diff == 0) {
                return new int[]{indexMap.get(nums[pl]), indexMap.get(nums[pr])};
            }
            if (diff < minDiff) {
                minDiff = diff;
                res = new int[]{indexMap.get(nums[pl]), indexMap.get(nums[pr])};
            }
        }
        return res;
    }

    public static class TreeNode {
        int val;
        List<TreeNode> children;

        public TreeNode(int val, List<TreeNode> children) {
            this.val = val;
            this.children = children;
        }
    }

    TreeNode maxNode = null;
    double max = Integer.MIN_VALUE;

    public TreeNode maximumAverageSubtree(TreeNode root) {
        if (root == null) {
            return null;
        }
        helper(root);
        return maxNode;
    }

    private double[] helper(TreeNode root) {
        if (root == null) {
            return new double[]{0, 0};
        }
        double curTotal = root.val;
        double count = 1;

        for (TreeNode child : root.children) {
            double[] cur = helper(child);
            curTotal += cur[0];
            count += cur[1];
        }
        double avg = curTotal / count;
        //taking "at least 1 child" into account
        if (count > 1 && avg > max) {
            max = avg;
            maxNode = root;
        }
        return new double[]{curTotal, count};
    }

    int[] parents;

    public int minCostToConnect(int n, int[][] edges, int[][] edgesToRepair) {
        int connect = n;
        int totalCost = 0;
        parents = new int[n + 1];
        Set<String> set = new HashSet<>();

        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        for (int[] edge : edgesToRepair) {
            StringBuilder sb = new StringBuilder();
            sb.append(edge[0]).append("#").append(edge[1]);
            set.add(sb.toString());
        }
        for (int[] edge : edges) {
            StringBuilder sb = new StringBuilder();
            sb.append(edge[0]).append("#").append(edge[1]);
            if (!set.contains(sb.toString())) {
                this.union(edge[0], edge[1]);
                connect--;
            }
        }
        Arrays.sort(edgesToRepair, new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });
        for (int[] edge : edgesToRepair) {
            if (this.union(edge[0], edge[1])) {
                totalCost += edge[2];
                connect--;
            }
            if (connect == 1) {
                return totalCost;
            }
        }
        return connect == 1 ? totalCost : -1;
    }

    private boolean union(int x, int y) {
        int setX = find(x);
        int setY = find(y);

        if (setX != setY) {
            parents[setY] = setX;
            return true;
        }
        return false;
    }

    private int find(int num) {
        if (parents[num] != num) {
            parents[num] = find(parents[num]);
        }
        return parents[num];
    }
}
