package com.wheel.learn.algorithm.leetcode.linklist;

/**
 * @desc 删除连表中重复的元素
 * {@link https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/description/}
 * @author: zhouf
 */
public class ID83 {

    public static void main(String[] args) {
        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(3);
        ListNode a3 = new ListNode(5);
        ListNode a4 = new ListNode(5);
        ListNode a5 = new ListNode(9);
        a1.next = a2;
        a2.next = a3;
        a3.next = a4;
        a4.next = a5;

        System.out.println(deleteDuplicates(a1));
    }

    /**
     * 迭代
     */
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode curr = head;
        while (curr.next != null) {
            if (curr.val == curr.next.val) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return head;
    }
}
