package com.wheel.basis.collection;

import com.wheel.common.util.JsonUtil;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @desc
 * @author: zhouf
 */
public class QueueApi {

    /**
     * 队列：先进先出
     * 常用API使用
     * 主要注意且类似的使用：
     * offer、add
     * poll、remove
     * peek、element
     */
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();

        // add如果失败，会抛出异常
        queue.add("a");
        // 添加元素，不抛出异常
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        queue.offer("f");

        System.out.println(JsonUtil.toString(queue));

        System.out.println(queue.peek());

        // 返回第一个元素，并在队列中删除
        System.out.println(queue.poll());

        // 返回第一个元素，会抛出异常
        System.out.println(queue.element());

        System.out.println(JsonUtil.toString(queue));
    }
}
