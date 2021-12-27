package com.wheel.learn.algorithm.leetcode.tree;


import com.wheel.learn.algorithm.common.TreeNode;
import com.wheel.learn.algorithm.common.TreeNodeUtil;

/**
 * @desc 二叉树的最大深度
 * {@link https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/description/}
 * @author: zhouf
 */
public class ID104 {

    public static void main(String[] args) {
        TreeNode root = TreeNodeUtil.arrayToTreeNode(new Integer[]{1, 2, 7, 3, 4, null, null, null, null, 5, 6});

        TreeNodeUtil.show(root);

        System.out.println(maxDepth(root));

        System.out.println(leftDepth(root));

        System.out.println(rightDepth(root));
    }

    /**
     * DFS （Depth First Search） 算法
     * 如果我们知道左子树和右子树的最大深度l 和 r,那么该二叉树的最大深度即为:
     * max(l,r) + 1
     *
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    public static int leftDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        return left + 1;
    }

    public static int rightDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int right = maxDepth(root.right);
        return right + 1;
    }
}
