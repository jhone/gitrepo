package com.redsun.platf.web.controller.converter.annontation;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: dick pan
 * Date: 2012/7/9
 * Time: 下午 4:46
 * To change this template use File | Settings | File Templates.
 */
public class MyDateFormatter implements Formatter<Date> {
    String formatString = "yyyy-MM-dd HH:mm:ss";

    public MyDateFormatter() {
    }

    public MyDateFormatter(String formatString) {
        this.formatString = formatString;
    }

    @Override
    public String print(Date date, Locale locale) {//①格式化
        System.out.println("print date!");
        if (date == null) {
            return "";
        }
//        TimeZone zone= Calendar.getInstance().getTimeZone();
//        StringConverter stringConverter=new StringConverter();
//        String s=(String)stringConverter.convert(Date.class,date);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        String str = format.format(date);
//        String str =StringConvert.INSTANCE.convertToString(zone);
        return str;
    }


    @Override
    public Date parse(String text, Locale locale) throws ParseException {//②解析
        System.out.println("parse text:[" + text + "] to Date");
        if (!StringUtils.hasLength(text)) {
            //①如果source为空 返回null
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        Date date =null;
        try {

            date = format.parse(text);
            format.format(date);

            System.out.println("parsed text:"+date);

            GregorianCalendar cal =new GregorianCalendar();
            cal.setTime(date);
            System.out.println(format.format(cal.getTime()));
        } catch (ParseException e) {
            //③如果不匹配 转换失败
            throw new IllegalArgumentException(String.format("类型转换失败，需要格式[%s]，但格式是[%s]", formatString, text));

        }
        return date;
    }
}
