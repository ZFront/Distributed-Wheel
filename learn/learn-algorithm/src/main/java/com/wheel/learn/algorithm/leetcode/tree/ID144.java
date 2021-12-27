package com.wheel.learn.algorithm.leetcode.tree;

import com.wheel.learn.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 二叉树的前序遍历
 * {@link https://leetcode-cn.com/problems/binary-tree-preorder-traversal/}
 * @author: zhouf
 * @date: 2021/12/27
 */
public class ID144 {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    public void preorder(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.val);
        preorder(root.left, result);
        preorder(root.right, result);
    }
}
