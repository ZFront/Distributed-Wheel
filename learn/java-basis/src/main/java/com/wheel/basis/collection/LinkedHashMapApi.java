package com.wheel.basis.collection;

import com.wheel.common.util.JsonUtil;

import java.util.LinkedHashMap;

/**
 * @desc 跟 hashMap不同的是，linkedHashMap 保存了插入的顺序
 * @author: zhouf
 */
public class LinkedHashMapApi {

    public static void main(String[] args) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap();

        linkedHashMap.put("A", "A");

        linkedHashMap.put("B", "B");

        linkedHashMap.put("C", "C");

        System.out.println(JsonUtil.toString(linkedHashMap));

        linkedHashMap.remove("B");

        System.out.println(JsonUtil.toString(linkedHashMap));

    }
}
