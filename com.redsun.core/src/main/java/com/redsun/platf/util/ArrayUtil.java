package com.redsun.platf.util;

import java.util.List;

public class ArrayUtil {
    
    public static String[] toString(Object[] data) {
        String[] tmp = new String[data.length];
        for(int i = 0; i < data.length; i++) {
            tmp[i] = data[i].toString();
        }
        return tmp;
    }
    
    public static String[] toString(List<Object> data) {
        String[] tmp = new String[data.size()];
        for(int i = 0; i < data.size(); i++) {
            tmp[i] = data.get(i).toString();
        }
        return tmp;
    }
    
    public static String[] listTransToArray(List<String> als){
        String[] sa=new String[als.size()];
        als.toArray(sa);
        return sa;
    }
    public static String arrayToString(Object[] a, String separator) {
        StringBuffer result = new StringBuffer();
        if (a.length > 0) {
            result.append(a[0]);
            for (int i=1; i<a.length; i++) {
                result.append(separator);
                result.append(a[i]);
            }
        }
        return result.toString();
    }

}
