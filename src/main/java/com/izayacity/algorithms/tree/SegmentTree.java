package com.izayacity.algorithms.tree;

import java.util.LinkedList;
import java.util.List;

public class SegmentTree {

    private int[] buildTreeArr(int[] nums) {
        int n = nums.length;
        int[] tree = new int[n * 2];

        for (int i = n, j = 0; i < 2 * n; i++, j++) {
            tree[i] = nums[j];
        }
        for (int i = n - 1; i > 0; i--) {
            tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
        return tree;
    }

    public void update (int[] tree, int pos, int val) {
        pos += tree.length;
        tree[pos] = val;

        while (pos > 0) {
            int left = pos;
            int right = pos;

            if (pos % 2 == 0) {
                right = pos + 1;
            } else {
                left = pos - 1;
            }
            pos /= 2;
            tree[pos] = tree[left] + tree[right];
        }
    }

    public int sumRange (int[] tree, int l, int r) {
        int n = tree.length;
        l += n;
        r += n;
        int sum = 0;

        while (l <= r) {
            /*ll is right child of PP. Then PP contains sum of range of ll and
            another child which is outside the range [l, r][l,r] and
            we don't need parent PP sum. Add ll to sumsum without its parent PP and
            set ll to point to the right of PP on the upper level.
             */
            if (l % 2 == 1) {
                sum += tree[l++];
            }
            // r is left child of PP
            if (r % 2 == 0) {
                sum += tree[r--];
            }
            // l is not right child of PP.
            // Then parent PP contains sum of range which lies in [l, r][l,r].
            // Add PP to sum and set ll to point to the parent of PP
            l /= 2;
            // rr is not left child of PP
            r /= 2;
        }
        return sum;
    }

    public List<Integer> countSmaller(int[] nums) {
        LinkedList<Integer> list = new LinkedList<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        SegTreeNode root = new SegTreeNode(min, max);

        for (int i = nums.length - 1; i >= 0; i--) {
            // minus 1 to handle the equal case
            list.addFirst(this.count(nums[i] - 1, root));
            this.insert(nums[i], root);
        }
        return list;
    }

    private int count (int x, SegTreeNode root) {
        if (root == null) {
            return 0;
        }
        if (x >= root.max) {
            return root.count;
        }
        if (x <= root.mid()) {
            return this.count(x, root.left);
        }
        return this.count(x, root.left) + this.count(x, root.right);
    }

    private void insert (int x, SegTreeNode root) {
        if (x < root.min || x > root.max) {
            return;
        }
        root.count++;
        // very important to prevent recursion overflow
        if (root.max == root.min) {
            return;
        }
        int mid = root.mid();

        if (x <= mid) {
            if (root.left == null) {
                root.left = new SegTreeNode(root.min, mid);
            }
            this.insert(x, root.left);
        } else {
            if (root.right == null) {
                root.right = new SegTreeNode(mid + 1, root.max);
            }
            this.insert(x, root.right);
        }
    }
}
