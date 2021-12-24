package com.wheel.learn.algorithm.leetcode.tree;

import com.wheel.learn.algorithm.common.TreeNode;
import com.wheel.learn.algorithm.common.TreeNodeUtil;

/**
 * @desc 二叉树的直径
 * {@link https://leetcode-cn.com/problems/diameter-of-binary-tree/}
 * @author: zhouf
 */
public class ID617 {

    public static void main(String[] args) {
        TreeNode tree1 = TreeNodeUtil.arrayToTreeNode(new Integer[]{1, 3, 2, 5, null, null});

        TreeNode tree2 = TreeNodeUtil.arrayToTreeNode(new Integer[]{2, 1, 3, null, 4, null, 7});

        TreeNode merged = mergeTrees(tree1, tree2);

        TreeNodeUtil.show(merged);
    }

    /**
     * 合并二叉树
     */
    public static TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        TreeNode merge = new TreeNode(root1.val + root2.val);
        merge.left = mergeTrees(root1.left, root2.left);
        merge.right = mergeTrees(root1.right, root2.right);
        return merge;
    }
}
