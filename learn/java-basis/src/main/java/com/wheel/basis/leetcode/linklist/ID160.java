package com.wheel.basis.leetcode.linklist;

import com.wheel.common.util.JsonUtil;

/**
 * @desc {@link https://leetcode-cn.com/problems/intersection-of-two-linked-lists/description/}
 * @author: zhouf
 */
public class ID160 {

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
        b2.next = a3;
        a3.next = b3;

        System.out.println(JsonUtil.toString(getIntersectionNode(a1, b1)));
    }

    /**
     * 双指针方法
     *
     * @param headA
     * @param headB
     * @return
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode l1 = headA, l2 = headB;
        while (l1 != l2) {
            l1 = (l1 == null) ? headB : l1.next;
            l2 = (l2 == null) ? headA : l2.next;
        }
        return l1;
    }
}
