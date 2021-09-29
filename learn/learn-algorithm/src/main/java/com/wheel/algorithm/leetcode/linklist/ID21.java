package com.wheel.algorithm.leetcode.linklist;

import com.wheel.common.util.JsonUtil;

/**
 * @desc {@link https://leetcode-cn.com/problems/merge-two-sorted-lists/description/}
 * @author: zhouf
 */
public class ID21 {

    public static void main(String[] args) {
        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(3);
        ListNode a3 = new ListNode(5);
        ListNode a4 = new ListNode(7);
        ListNode a5 = new ListNode(9);
        a1.next = a2;
        a2.next = a3;
        a3.next = a4;
        a4.next = a5;

        ListNode b1 = new ListNode(2);
        ListNode b2 = new ListNode(4);
        ListNode b3 = new ListNode(8);
        b1.next = b2;
        b2.next = b3;

        System.out.println(JsonUtil.toString(mergeTwoLists(a1, b1)));

        System.out.println(JsonUtil.toString(mergeTwoLists2(a1, b1)));
    }

    /**
     * 迭代
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 设置一个前面的节点
        ListNode preHead = new ListNode(-1);

        ListNode prev = preHead;
        // 如果节点不为空
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = l1 == null ? l2 : l1;

        // 返回头结点
        return preHead.next;
    }

    /**
     * 递归
     */
    public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

}
