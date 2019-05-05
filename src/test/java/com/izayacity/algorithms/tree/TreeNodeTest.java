package com.izayacity.algorithms.tree;

import org.junit.jupiter.api.Test;

import static com.izayacity.algorithms.tree.TreeNode.createMinimalBST;

/**
 * Author:  Xirui Yang
 * Date:    2019-03-22
 * Time:    15:17
 * Version: 1.0
 * Email:   xirui.yang@happyelements.com
 * Description: tree
 */
public class TreeNodeTest {

    @Test
    public void createMinimalBSTTest() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        TreeNode root = createMinimalBST(arr);
        root.print();
    }
}