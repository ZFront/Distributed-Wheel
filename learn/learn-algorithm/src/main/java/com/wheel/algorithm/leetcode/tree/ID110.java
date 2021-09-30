package com.wheel.algorithm.leetcode.tree;

import com.wheel.algorithm.common.TreeNode;
import com.wheel.algorithm.common.TreeNodeUtil;

/**
 * @desc
 * @author: zhouf
 */
public class ID110 {

    private static boolean result = true;

    public static void main(String[] args) {
        System.out.println(isBalanced(TreeNodeUtil.arrayToTreeNode(new Integer[]{3, 9, 20, null, null, 15, 7})));

        System.out.println(isBalanced(TreeNodeUtil.arrayToTreeNode(new Integer[]{1, 2, 2, 3, 3, null, null, 4, 4})));
    }

    public static boolean isBalanced(TreeNode root) {
        maxDepth(root);
        return result;
    }

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = maxDepth(root.left);
        int r = maxDepth(root.right);
        if (Math.abs(l - r) > 1) {
            result = false;
        }
        return 1 + Math.max(l, r);
    }
}
