package com.wheel.basis.leetcode.linklist;

/**
 * @desc {@link https://leetcode-cn.com/problems/reverse-linked-list/description/}
 * @author: zhouf
 */
public class ID206 {

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

        System.out.println(reverseList(a1));
        System.out.println(reverseList2(a1));
    }

    /**
     * 迭代
     *
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        // 定义一个空节点
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /**
     * 递归
     *
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        // 设置终止条件
        if (head == null || head.next == null) {
            return head;
        }
        // 一直都返回最后一个节点，因为并没有对他进行操作
        ListNode newHead = reverseList2(head.next);
        head.next.next = head;
        // 防止循环指向
        head.next = null;
        return newHead;
    }
}
