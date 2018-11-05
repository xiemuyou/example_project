package com.doushi.library.util;

import java.math.BigDecimal;

public class DoubleUtil {

    public static String formatDouble(String d) {
        return formatDouble(Double.parseDouble(d));
    }

    public static String formatDouble(double d) {
        return formatDouble(d, "#0.00");
    }

    public static String formatDouble(double d, String formatType) {
        java.text.DecimalFormat df = new java.text.DecimalFormat(formatType);
        return df.format(d);
    }

    public static String doubleForInteger(double d) {
        return doubleForInteger(String.valueOf(d));
    }

    /**
     * 去掉小数点后面的值
     *
     * @param str 浮点数字符串
     * @return 整数
     */
    public static String doubleForInteger(String str) {
        if (!str.contains(".")) {
            return str;
        }
        return str.substring(0, str.indexOf("."));
    }

    private static int maxCount = 10000;

    /**
     * 播放次数转换为字符串
     *
     * @param playCount 视频播放次数
     * @return 如果小于一万, 返回原数字字符串, 大于一万, 万+小数点后两位
     */
    public static String getPlayCount(int playCount) {
        String pc = String.valueOf(playCount);
        if (playCount > maxCount) {
            String temCount = (playCount / maxCount) + "." + (playCount % maxCount);
            pc = formatDouble(temCount) + "万";
        }
        return pc;
    }

    /**
     * 插入字符串
     *
     * @param str             原字符串
     * @param defaultFirstStr 字符串长度不够插入位置,默认添加字符串
     * @param insetStr        插入字符串
     * @param index           插入位置
     * @return 插入好的字符串
     */
    public static String insetLongString(long str, String defaultFirstStr, String insetStr, int index) {
        return insetString(String.valueOf(str), defaultFirstStr, insetStr, index);
    }

    /**
     * 插入字符串
     *
     * @param str             原字符串
     * @param defaultFirstStr 字符串长度不够插入位置,默认添加字符串
     * @param insetStr        插入字符串
     * @param index           插入位置
     * @return 插入好的字符串
     */
    public static String insetString(String str, String defaultFirstStr, String insetStr, int index) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            if (index > 0 && str.length() >= index) {
                String firstStr = str.substring(0, index);
                String lastStr = str.substring(index, str.length());
                sb.append(firstStr).append(insetStr).append(lastStr);
            } else {
                sb.append(defaultFirstStr).append(insetStr);
                for (int i = 0; i > index; i--) {
                    sb.append(defaultFirstStr);
                }
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0 || v2 == 0) {
            return v1;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
