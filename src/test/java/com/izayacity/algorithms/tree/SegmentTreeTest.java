package com.izayacity.algorithms.tree;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTreeTest {

    public void display(Object obj) {
        if (obj.getClass().isPrimitive() || Primitives.isWrapperType(obj.getClass())) {
            System.out.println(obj);
        } else {
            System.out.println(new Gson().toJson(obj));
        }
    }

    @Test
    public void countSmaller() {
        SegmentTree segmentTree = new SegmentTree();
        int[] nums = new int[]{5, 5, 2, 6, 1};
        List<Integer> res = segmentTree.countSmaller(nums);
        display(res);
    }
}