package com.wheel.algorithm.leetcode.tree;

import com.wheel.algorithm.common.TreeNode;
import com.wheel.algorithm.common.TreeNodeUtil;

/**
 * @desc
 * @author: zhouf
 */
public class ID226 {

    public static void main(String[] args) {

        TreeNode tree = TreeNodeUtil.arrayToTreeNode(new Integer[]{4, 2, 7, 1, 3, 6, 9});

        TreeNodeUtil.show(tree);

        TreeNodeUtil.show(invertTree(tree));

    }

    /**
     * 反转树
     *
     * @param root
     * @return
     */
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
}