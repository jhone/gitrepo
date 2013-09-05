package com.redsun.platf.util.convertor;

import com.redsun.platf.util.EPDateUtils;
import com.redsun.platf.util.string.StringUtils;

import java.util.Date;

@SuppressWarnings("unchecked")
public class StringConvertor {
    public static Convertor<Object, String> converterObjectToString = new Convertor<Object, String>() {

        //              //  @Override
        public String convert(Object s) {
            return s == null ? null : s.toString();
        }

    };
    public static Convertor<String, Integer> convertorStringToInteger = new Convertor<String, Integer>() {

        //              //  @Override
        public Integer convert(String s) {
            if (s == null) return null;
            return Integer.parseInt(s);
        }

    };
    public static Convertor<String, Date> convertorStringToDate = new Convertor<String, Date>() {

        //              //  @Override
        public Date convert(String s) {
            return EPDateUtils.parse(s);
        }

    };
    public static Convertor<Date, String> convertorDateToString = new Convertor<Date, String>() {

        //              //  @Override
        public String convert(Date s) {
            return EPDateUtils.formatDateEn(s);
        }

    };
    public static Convertor<String, String> convertorClearSpace = new Convertor<String, String>() {

        //              //  @Override
        public String convert(String s) {
            return StringUtils.clearSpace(s);
        }

    };
    public static Convertor<String, String> convertorClearDuplicateSpaces = new Convertor<String, String>() {

        //              //  @Override
        public String convert(String s) {
            return StringUtils.clearDuplicateSpace(s);
        }

    };
    public static Convertor<String, String> convertorClearNonChinese = new Convertor<String, String>() {

        //  @Override
        public String convert(String s) {
            return StringUtils.clearNonChinese(s);
        }

    };
    public static Convertor<String, String> convertorForceNull = new Convertor<String, String>() {

        //  @Override
        public String convert(String s) {
            return StringUtils.avoidEmpty(s);
        }

    };
    public static Convertor<String, String> convertorAvoidNull = new Convertor<String, String>() {

        //  @Override
        public String convert(String s) {
            return StringUtils.avoidNull(s);
        }

    };
    public static String chainConvertors(String str, Convertor<String, String> ... convertors) {
                   String res = str;
                   for(int i = 0; i < convertors.length; i++) {
                           res = convertors[i].convert(res);
                   }
                   return res;
           }
}