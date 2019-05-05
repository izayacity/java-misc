package com.izayacity.algorithms.tree;

/**
 * Created by Francis Yang on 5/4/17.
 */
public class TreeNode {
    public int val;
    public TreeNode parent;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public void setLeftChildByVal(final int val) {
        TreeNode node = new TreeNode(val);
        this.setLeftChild(node);
    }

    public void setRightChildbyVal(final int val) {
        TreeNode node = new TreeNode(val);
        this.setRightChild(node);
    }

    private void setLeftChild(TreeNode left) {
        if (left == null)
            return;
        this.left = left;
        left.parent = this;
    }

    private void setRightChild(TreeNode right) {
        if (right == null)
            return;
        this.right = right;
        right.parent = this;
    }

    // Binary Search tree: left.val <= this.val <= right.val
    public boolean isBST() {
        if (left != null) {
            if (val < left.val || !left.isBST()) {
                return false;
            }
        }

        if (right != null) {
            if (val > right.val || !right.isBST()) {
                return false;
            }
        }
        return true;
    }

    public int height() {
        int leftHeight = (left != null ? left.height() : 0);
        int rightHeight = (right != null ? right.height() : 0);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public TreeNode find(int val) {
        if (val == this.val) {
            return this;
        } else if (val < this.val) {
            return this.left != null ? this.left.find(val) : null;
        } else {
            return this.right != null ? this.right.find(val) : null;
        }
    }

    public void print() {
        BTreePrinter.printNode(this);
    }

    private static TreeNode createMinimalBST(int[] arr, int start, int end) {
        if (end < start) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(arr[mid]);
        node.setLeftChild(createMinimalBST(arr, 0, mid - 1));
        node.setRightChild(createMinimalBST(arr, mid + 1, end));
        return node;
    }

    public static TreeNode createMinimalBST(int[] arr) {
        return createMinimalBST(arr, 0, arr.length - 1);
    }

    public void insertInOrder(int val) {
        if (val <= this.val) {
            if (this.left == null) {
                setLeftChild(new TreeNode(val));
            } else {
                this.left.insertInOrder(val);
            }
        } else {
            if (this.right == null) {
                setRightChild(new TreeNode(val));
            } else {
                this.right.insertInOrder(val);
            }
        }
    }
}
