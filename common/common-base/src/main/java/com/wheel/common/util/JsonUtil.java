/**
 * @Project: SNS_Platform
 * @Author: squll
 * @Date: 2010-12-30
 * @Copyright: (c) 2010 广州菠萝信息技术有限公司 All rights reserved.
 */
package com.wheel.common.util;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.List;

public class JsonUtil {
    static SerializeConfig snakeCaseConfig = new SerializeConfig();
    static SerializeConfig camelCaseConfig = new SerializeConfig();
    static ParserConfig parserCamelCase = new ParserConfig();

    static {
        snakeCaseConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        camelCaseConfig.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
        parserCamelCase.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
    }

    /**
     * 将一个对像转成一个json字符串
     */
    public static final String toString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 把一个对象转换成Json字符串，并指定相关的格式要求
     *
     * @param obj
     * @param feature
     * @return
     */
    public static final String toString(Object obj, SerializerFeature... feature) {
        return JSON.toJSONString(obj, feature);
    }

    /**
     * 把一个对象转换成Json字符串，并保留值为null的属性
     *
     * @param obj
     * @return
     */
    public static final String toStringWithNull(Object obj) {
        return JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    /**
     * 把一个对象转换成Json字符串，并美化输出
     *
     * @param obj
     * @return
     */
    public static final String toStringPretty(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
    }

    /**
     * 生成友好的JSON字符串，友好的具体表现在如下：
     * 1、把Long转换成String，可兼容到javascript中
     * 2、把Date转成 yyyy-MM-dd HH:mm:ss 格式的字符串，人眼查看方便
     * 3、会把null的属性也输出
     *
     * @param obj
     * @return
     */
    public static final String toStringFriendly(Object obj) {
        SerializeConfig config = new SerializeConfig();
        config.put(Long.class, new StringSerializer());
        config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        return JSON.toJSONString(obj, config, SerializerFeature.WriteMapNullValue);
    }

    public static final String toStringFriendlyNotNull(Object obj) {
        SerializeConfig config = new SerializeConfig();
        config.put(Long.class, new StringSerializer());
        config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        return JSON.toJSONString(obj, config);
    }

    /**
     * 把字段的属性命名方式从驼峰转成下划线(只对POJO起效，对Map的key不起效)
     *
     * @param obj
     * @return
     */
    public static String toStringUnderline(Object obj) {
        return JSON.toJSONString(obj, snakeCaseConfig, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 把字段的属性命名方式从下划线转成驼峰(只对POJO起效，对Map的key不起效)
     *
     * @param obj
     * @return
     */
    public static String toStringCamel(Object obj) {
        return JSON.toJSONString(obj, camelCaseConfig, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue);
    }


    public static <T> T toBeanOrderly(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz, Feature.OrderedField);
    }

    public static <T> T toBeanOrderly(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz, Feature.OrderedField);
    }

    /**
     * 把下划线分割转成驼峰
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toBeanCamel(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz, parserCamelCase);
    }

    /**
     * 把json字符串转换成Object对象
     *
     * @param text
     * @return
     */
    public static Object toBean(String text) {
        return JSON.parse(text);
    }

    /**
     * 把json字符串转换成指定Class的对象
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * 把json字节转换成指定Class的对象
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toBean(byte[] text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * 把json字符串转换成指定类型的对象
     *
     * @param text
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toBean(String text, Type type) {
        return JSON.parseObject(text, type);
    }

    /**
     * 把json字符串转换成指定类型的对象
     *
     * @param text
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toBean(String text, TypeReference<T> type) {
        return JSON.parseObject(text, type);
    }

    /**
     * 把json字符串转换为Object数组
     *
     * @param text
     * @return
     */
    public static Object[] toArray(String text) {
        return toArray(text, null);
    }

    /**
     * 把json字符串转换为指定Class的数组
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T[] toArray(String text, Class<T> clazz) {
        return (T[]) toList(text, clazz).toArray();
    }

    /**
     * 把json字符串转换为指定Class的List
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> List<T> toList(Object obj, Class<T> clazz) {
        if (obj == null) {
            return null;
        }
        return JSONArray.parseArray(obj.toString(), clazz);
    }

    /**
     * 根据键获取Json里String类型的值
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static String getString(String jsonStr, String key) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        if (!isBlank(jsonObj)) {
            return String.valueOf(jsonObj.get(key));
        }
        return "";
    }

    /**
     * 根据键获取Json里int类型的值
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static int getInteger(String jsonStr, String key) {
        return Integer.valueOf(getString(jsonStr, key));
    }

    /**
     * 根据键获取Json里long类型的值
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static long getLong(String jsonStr, String key) {
        return Long.valueOf(getString(jsonStr, key));
    }

    /**
     * 判断一个字符串是否是合法的JSON格式
     */
    public static boolean isJson(String str) {
        return str != null && str.trim().length() > 0 && new JsonValidator().validate(str);
    }

    /**
     * 判断一个对象是否为空，包括：null和空字符串
     *
     * @param obj
     * @return
     */
    private static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            if (((String) obj).trim().length() <= 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }

    /**
     * 把一些数据类型转为String的序列化类
     */
    static class StringSerializer implements ObjectSerializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            SerializeWriter out = serializer.getWriter();
            if (object == null) {
                if (out.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
                    out.write('0');
                } else {
                    out.writeNull();
                }
                return;
            }
            out.writeString(object.toString());
        }
    }

    /**
     * 校验一个字符串是否是合法的JSON格式的类
     *
     * @Author chenyf
     */
    static class JsonValidator {
        private CharacterIterator it;
        private char c;
        private int col;

        public JsonValidator() {
        }

        /**
         * 验证一个字符串是否是合法的JSON串
         *
         * @param input 要验证的字符串
         * @return true-合法 ，false-非法
         */
        public boolean validate(String input) {
            input = input.trim();
            return valid(input);
        }

        private boolean valid(String input) {
            if ("".equals(input)) {
                return true;
            }

            boolean ret = true;
            it = new StringCharacterIterator(input);
            c = it.first();
            col = 1;
            if (!value()) {
                ret = error("value", 1);
            } else {
                skipWhiteSpace();
                if (c != CharacterIterator.DONE) {
                    ret = error("end", col);
                }
            }

            return ret;
        }

        private boolean value() {
            return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
        }

        private boolean literal(String text) {
            CharacterIterator ci = new StringCharacterIterator(text);
            char t = ci.first();
            if (c != t) {
                return false;
            }

            int start = col;
            boolean ret = true;
            for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
                if (t != nextCharacter()) {
                    ret = false;
                    break;
                }
            }
            nextCharacter();
            if (!ret) {
                error("literal " + text, start);
            }
            return ret;
        }

        private boolean array() {
            return aggregate('[', ']', false);
        }

        private boolean object() {
            return aggregate('{', '}', true);
        }

        private boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
            if (c != entryCharacter) {
                return false;
            }
            nextCharacter();
            skipWhiteSpace();
            if (c == exitCharacter) {
                nextCharacter();
                return true;
            }

            for (; ; ) {
                if (prefix) {
                    int start = col;
                    if (!string()) {
                        return error("string", start);
                    }
                    skipWhiteSpace();
                    if (c != ':') {
                        return error("colon", col);
                    }
                    nextCharacter();
                    skipWhiteSpace();
                }
                if (value()) {
                    skipWhiteSpace();
                    if (c == ',') {
                        nextCharacter();
                    } else if (c == exitCharacter) {
                        break;
                    } else {
                        return error("comma or " + exitCharacter, col);
                    }
                } else {
                    return error("value", col);
                }
                skipWhiteSpace();
            }

            nextCharacter();
            return true;
        }

        private boolean number() {
            if (!Character.isDigit(c) && c != '-') {
                return false;
            }
            int start = col;
            if (c == '-') {
                nextCharacter();
            }
            if (c == '0') {
                nextCharacter();
            } else if (Character.isDigit(c)) {
                while (Character.isDigit(c)) {
                    nextCharacter();
                }
            } else {
                return error("number", start);
            }
            if (c == '.') {
                nextCharacter();
                if (Character.isDigit(c)) {
                    while (Character.isDigit(c)) {
                        nextCharacter();
                    }
                } else {
                    return error("number", start);
                }
            }
            if (c == 'e' || c == 'E') {
                nextCharacter();
                if (c == '+' || c == '-') {
                    nextCharacter();
                }
                if (Character.isDigit(c)) {
                    while (Character.isDigit(c)) {
                        nextCharacter();
                    }
                } else {
                    return error("number", start);
                }
            }
            return true;
        }

        private boolean string() {
            if (c != '"') {
                return false;
            }

            int start = col;
            boolean escaped = false;
            for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {
                if (!escaped && c == '\\') {
                    escaped = true;
                } else if (escaped) {
                    if (!escape()) {
                        return false;
                    }
                    escaped = false;
                } else if (c == '"') {
                    nextCharacter();
                    return true;
                }
            }
            return error("quoted string", start);
        }

        private boolean escape() {
            int start = col - 1;
            if (" \\\"/bfnrtu".indexOf(c) < 0) {
                return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", start);
            }
            if (c == 'u') {
                if (!isHex(nextCharacter()) || !isHex(nextCharacter()) || !isHex(nextCharacter())
                        || !isHex(nextCharacter())) {
                    return error("unicode escape sequence  \\uxxxx ", start);
                }
            }
            return true;
        }

        private boolean isHex(char d) {
            return "0123456789abcdefABCDEF".indexOf(d) >= 0;
        }

        private char nextCharacter() {
            c = it.next();
            ++col;
            return c;
        }

        private void skipWhiteSpace() {
            while (Character.isWhitespace(c)) {
                nextCharacter();
            }
        }

        private boolean error(String type, int col) {
            System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
            return false;
        }
    }
}
