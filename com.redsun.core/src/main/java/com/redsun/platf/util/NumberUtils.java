package com.redsun.platf.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <p>Title: com.walsin.platf.util.NumberUtils</p>
 * <p>Description: 數字相關共用方法</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {
    
    private final static DecimalFormat df = new DecimalFormat("#,###.##############################");
    
    /**
     * 格式化數字
     * @param value 值
     * @return 返回格式化數字ex:123,321.123
     */
    public static String format(BigDecimal value) {
        return value == null ? StringUtils.EMPTY : df.format(value.doubleValue());
    }

    /**
        * 提供精确的加法运算。
        *
        * @param v1 被加数
        * @param v2 加数
        * @return 两个参数的和
        */
       public static double add(double v1, double v2) {
           BigDecimal b1 = new BigDecimal(Double.toString(v1));
           BigDecimal b2 = new BigDecimal(Double.toString(v2));
           return b1.add(b2).doubleValue();
       }

       /**
        * 提供精确的减法运算。
        *
        * @param v1 被减数
        * @param v2 减数
        * @return 两个参数的差
        */
       public static double sub(double v1, double v2) {
           BigDecimal b1 = new BigDecimal(Double.toString(v1));
           BigDecimal b2 = new BigDecimal(Double.toString(v2));
           return b1.subtract(b2).doubleValue();
       }

       /**
        * 提供精确的乘法运算。
        *
        * @param v1 被乘数
        * @param v2 乘数
        * @return 两个参数的积
        */
       public static double mul(double v1, double v2) {
           BigDecimal b1 = new BigDecimal(Double.toString(v1));
           BigDecimal b2 = new BigDecimal(Double.toString(v2));
           return b1.multiply(b2).doubleValue();
       }

       /**
        * 提供精确的除法运算。
        *
        * @param v1 被除数
        * @param v2 除数
        * @return 两个参数的商
        */
       public static double div(double v1, double v2) {
           BigDecimal b1 = new BigDecimal(Double.toString(v1));
           BigDecimal b2 = new BigDecimal(Double.toString(v2));
           return b1.divide(b2).doubleValue();
       }

}
