package com.wheel.learn.algorithm.leetcode.linklist;

/**
 * @desc 反转链表
 * {@link https://leetcode-cn.com/problems/reverse-linked-list/description/}
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
     * 以连表 1->2->3->4->5 举例
     * 递归
     *
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        // 设置终止条件
        if (head == null || head.next == null) {
            /**
             * 直到当前节点的下一个节点为空时，返回当前的节点
             * 由于5没有下一个节点，所以此时返回节点5
             */
            return head;
        }
        // 一直都返回最后一个节点，因为并没有对他进行操作
        ListNode newHead = reverseList2(head.next);
        /**
         第一轮出栈，head为5，head.next为空，返回5
         第二轮出栈，head为4，head.next为5，执行head.next.next=head也就是5.next=4，
         把当前节点的子节点的子节点指向当前节点
         此时链表为 1->2->3->4<->5，由于4与5互相指向，所以此处要断开4.next=null
         此时链表为 1->2->3->4<-5
         返回节点5
         第三轮出栈，head为3，head.next为4，执行head.next.next=head也就是4.next=3，
         此时链表为 1->2->3<->4<-5，由于3与4互相指向，所以此处要断开3.next=null
         此时链表为 1->2->3<-4<-5
         返回节点5
         第四轮出栈，head为2，head.next为3，执行head.next.next=head也就是3.next=2，
         此时链表为 1->2<->3<-4<-5，由于2与3互相指向，所以此处要断开2.next=null
         此时链表为 1->2<-3<-4<-5
         返回节点5
         第五轮出栈，head为1，head.next为2，执行head.next.next=head也就是2.next=1，
         此时链表为 1<->2<-3<-4<-5，由于1与2互相指向，所以此处要断开1.next=null
         此时链表为 1<-2<-3<-4<-5
         返回节点5
         出栈完成，最终头节点5->4->3-2->1
         **/
        // 反转指向
        head.next.next = head;
        // 防止循环指向
        head.next = null;
        return newHead;
    }
}
