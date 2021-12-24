package com.wheel.learn.algorithm.leetcode.linklist;

/**
 * @desc 删除链表的倒数第N个节点
 * {@link https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/description/}
 * @author: zhouf
 */
public class ID19 {

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

        System.out.println(removeNthFromEnd(a1, 2));
    }

    /**
     * 暴力解决，两次迭代
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
        }
        ListNode curr = head;
        int size = 0, temp = 0, currCount = 0;
        // 第一次遍历计算总数
        while (curr.next != null) {
            size++;
            curr = curr.next;
        }
        if (n > size) {
            return head;
        }
        temp = size - n;
        // 第二次遍历，剔除重复节点
        ListNode curr2 = head;
        while (curr2.next != null) {
            currCount++;
            if (temp == currCount) {
                curr2.next = curr2.next.next;
            } else {
                curr2 = curr2.next;
            }
        }
        return head;
    }

    /**
     * 双指针
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        //TODO
        return null;
    }

}
