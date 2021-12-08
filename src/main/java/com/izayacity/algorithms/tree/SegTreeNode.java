package com.izayacity.algorithms.tree;

public class SegTreeNode {

    public int min, max;
    public int count;
    public SegTreeNode left, right;

    public int mid () {
        return min + (max - min) / 2;
    }

    public SegTreeNode (int min, int max) {
        this.min = min;
        this.max = max;
        this.count = 0;
    }
}
