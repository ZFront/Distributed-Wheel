package com.wheel.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AmountUtil {

    /**
     * 加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2, double... v3) {
        double result = add(v1, v2);
        if (v3 != null && v3.length > 0) {
            for (int i = 0; i < v3.length; i++) {
                result = add(result, v3[i]);
            }
        }
        return result;
    }

    /**
     * 加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return new BigDecimal(Double.toString(b1.add(b2).doubleValue()));
    }

    /**
     * 加法运算
     *
     * @param v1
     * @param v2
     * @param v3
     * @return
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2, BigDecimal... v3) {
        BigDecimal result = add(v1, v2);
        if (v3 != null && v3.length > 0) {
            for (int i = 0; i < v3.length; i++) {
                result = add(result, v3[i]);
            }
        }
        return result;
    }

    /**
     * 减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 减法运算
     *
     * @param v1
     * @param v2
     * @param v3
     * @return
     */
    public static double sub(double v1, double v2, double... v3) {
        double result = sub(v1, v2);
        if (v3 != null && v3.length > 0) {
            for (int i = 0; i < v3.length; i++) {
                result = sub(result, v3[i]);
            }
        }
        return result;
    }

    /**
     * 减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2);
    }

    /**
     * @param v1
     * @param v2
     * @param v3
     * @return
     * @description v1 减去 v2、v3....的结果，结果精度默认为6
     * @author: chenyf
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2, BigDecimal... v3) {
        BigDecimal result = sub(v1, v2);
        if (v3 != null && v3.length > 0) {
            for (int i = 0; i < v3.length; i++) {
                result = sub(result, v3[i]);
            }
        }
        return result;
    }

    /**
     * 乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @param v1 运算数1，类型为BigDecimal
     * @param v2 运算数2，类型为BigDecimal
     * @return 返回运算数1和运算数2相乘后的结果
     * @description: 返回运算数1和运算数2相乘后的结果，结果类型为BigDecimal
     * @author: huang.jin
     * @date: 2017年9月21日 上午9:52:37
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        return mul(v1, v2, 2);
    }

    /**
     * @param v1    运算数1，类型为BigDecimal
     * @param v2    运算数2，类型为BigDecimal
     * @param scale 运算精度
     * @return 返回运算数1和运算数2相乘后的结果
     * @description: 返回运算数1和运算数2相乘后的结果，结果类型为BigDecimal
     * @author: huang.jin
     * @date: 2017年9月21日 上午9:54:44
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal result = v1.multiply(v2).divide(BigDecimal.ONE, scale, RoundingMode.HALF_UP);
        return result;
    }

    /**
     * 除法运算，当发生除不尽的情况时，精确到小数点以后2位，以后的数字四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, 2);
    }

    /**
     * 除法运算，当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确到小数点以后几位
     * @return
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param v1 运算数1
     * @param v2 运算数2
     * @return
     * @description: 运算数1与运算数2相除，默认精度为6
     * @author: huang.jin
     * @date: 2017年9月21日 上午10:03:19
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, 6);
    }

    /**
     * @param v1    运算数1
     * @param v2    运算数2
     * @param scale 精度
     * @return 返回运算1与运算2相除的结果，结果精度为scale
     * @description: 运算数1与运算数2相除
     * @author: huang.jin
     * @date: 2017年9月21日 上午9:59:27
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal result = v1.divide(v2, scale, RoundingMode.HALF_UP);
        return result;
    }

    /**
     * 判断 a 与 b 是否相等
     *
     * @param a
     * @param b
     * @return a==b 返回true, a!=b 返回false
     */
    public static boolean equal(double a, double b) {
        BigDecimal v1 = BigDecimal.valueOf(a);
        BigDecimal v2 = BigDecimal.valueOf(b);
        if (v1.compareTo(v2) == 0) {
            return true;
        }
        return false;
    }

    /**
     * @param a 类型为BigDecimal
     * @param b 类型为BigDecimal
     * @return a == b，则返回true，a != b, 则返回false
     * @description: 判断a与b是否相等
     * @author: huang.jin
     * @date: 2017年9月20日 下午5:37:10
     */
    public static boolean equal(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == 0;
    }

    /**
     * 判断 a 是否小于 b
     *
     * @param a
     * @param b
     * @return a&lt;b 返回true, a&gt;=b 返回 false
     */
    public static boolean lessThan(double a, double b) {
        BigDecimal v1 = BigDecimal.valueOf(a);
        BigDecimal v2 = BigDecimal.valueOf(b);
        if (v1.compareTo(v2) == -1) {
            return true;
        }
        return false;
    }

    /**
     * @param a 类型为BigDecimal
     * @param b 类型为BigDecimal
     * @return
     * @description: 判断a值是否小于b值
     * @author: huang.jin
     * @date: 2017年9月27日 下午3:21:18
     */
    public static boolean lessThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == -1;
    }

    /**
     * @param a 类型为BigDecimal
     * @param b 类型为BigDecimal
     * @return
     * @description: a值是否小于等于b
     * @author: huang.jin
     * @date: 2017年9月25日 下午4:29:14
     */
    public static boolean lessThanOrEqualTo(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }

    /**
     * @param a 类型为BigDecimal
     * @param b 类型为BigDecimal
     * @return
     * @description: 判断a是否大于b
     * @author: huang.jin
     * @date: 2017年9月26日 上午11:43:02
     */
    public static boolean greater(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == 1;
    }

    /**
     * 判断 a 是否大于 b
     *
     * @param a
     * @param b
     * @return a&gt;b 返回true, a&lt;=b 返回 false
     */
    public static boolean greater(double a, double b) {
        BigDecimal v1 = BigDecimal.valueOf(a);
        BigDecimal v2 = BigDecimal.valueOf(b);
        return v1.compareTo(v2) == 1;
    }

    /**
     * 判断 a 是否大于等于 b
     *
     * @param a
     * @param b
     * @return a&gt;=b 返回true, a&lt;b 返回false
     */
    public static boolean greaterThanOrEqualTo(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0;
    }

    /**
     * 判断 a 是否大于等于 b
     *
     * @param a
     * @param b
     * @return a&gt;=b 返回true, a&lt;b 返回false
     */
    public static boolean greaterThanOrEqualTo(double a, double b) {
        BigDecimal v1 = BigDecimal.valueOf(a);
        BigDecimal v2 = BigDecimal.valueOf(b);
        return v1.compareTo(v2) >= 0;
    }

    /**
     * 转换为千分位，且保留两位小数
     *
     * @param v1
     * @return
     */
    public static String toThousandth(BigDecimal v1) {
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        return df.format(v1);
    }
}
