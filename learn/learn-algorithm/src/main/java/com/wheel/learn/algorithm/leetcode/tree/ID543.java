package com.wheel.learn.algorithm.leetcode.tree;

import com.wheel.learn.algorithm.common.TreeNode;
import com.wheel.learn.algorithm.common.TreeNodeUtil;

/**
 * @desc
 * @author: zhouf
 */
public class ID543 {

    private static int max = 0;

    public static void main(String[] args) {
        TreeNode tree = TreeNodeUtil.arrayToTreeNode(new Integer[]{1, 2, 3, 4, 5, null, null});

        TreeNodeUtil.show(tree);

        System.out.println("最大直径为：" + diameterOfBinaryTree(tree));
    }

    /**
     * 查找最大直径。其实本质就是
     *
     * @param root
     * @return
     */
    public static int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return max;
    }

    private static int depth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lDepth = depth(root.left);
        int rDepth = depth(root.right);

        max = Math.max(max, lDepth + rDepth);
        // 目的是为了迭代返回深度
        return Math.max(lDepth, rDepth) + 1;
    }
}
