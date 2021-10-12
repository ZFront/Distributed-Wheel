package com.wheel.learn.algorithm.leetcode.tree;

import com.wheel.learn.algorithm.common.TreeNode;
import com.wheel.learn.algorithm.common.TreeNodeUtil;

/**
 * @desc 路径和是否等于一个数
 * @author: zhouf
 */
public class ID112 {

    public static void main(String[] args) {
        TreeNode tree = TreeNodeUtil.arrayToTreeNode(new Integer[]{5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1});

        TreeNodeUtil.show(tree);

        System.out.println(hasPathSum(tree, 22));

        System.out.println(hasPathSum(tree, 7));
    }

    /**
     * 树的路径和是否等于一个数
     *
     * @param root
     * @param targetSum
     * @return
     */
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null && root.val == targetSum) {
            return true;
        }
        return hasPathSum(root.left, targetSum - root.val)
                || hasPathSum(root.right, targetSum - root.val);
    }
}
