package com.redsun.platf.util.string;

/**
 * <p>Title        : com.webapp        </p>
 * <p>Description  :                   </p>
 * <p>Copyright    : Copyright (c) 2010</p>
 * <p>Company      : FreedomSoft       </p>
 *
 */

/**
 * @author Dick Pan 
 * @version 1.0
 * @since 1.0
 * <p><H3>Change history</H3></p>
 * <p>2010/10/28   : Created </p>
 *
 */

import com.redsun.platf.util.convertor.Convertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("unchecked")
public final class StringUtils {
    public static final String EMPTY = "";
    public static final Pattern PATTERN_BLANK = Pattern.compile("[\\s]*");
    public static final String EMAIL_PATTERN_STR = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,}){1}$";
    public static final String PASSWORD_PATTERN_STR = "^[A-Za-z0-9~!@#\\$%\\^\\&\\*\\(\\)\\-_\\+=\\[\\]\\{\\}\\\\|;:\\\'\\\"<>,\\.\\?/]{6,16}$";

    /**
     * 判断字符串是否为空，可能是空串或者null
     *
     * @param str
     * @return
     */
//    public static boolean isEmptyOrNull(String str) {
//        return (str == null || EMPTY.equals(str));
//    }
    public static boolean isEmptyOrNull(Object str) {
        return (str == null || EMPTY.equals(str));
    }

    /**
     * 判断字符串是否不为空，既不是null也不是空串
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return (str != null && !EMPTY.equals(str));
    }

    public static boolean isNotBlank(String str) {
        if (str == null) {
            return false;
        } else {
            return !PATTERN_BLANK.matcher(str).matches();
        }
    }


    private static final Pattern latin = Pattern.compile("[\\p{InBasicLatin}\\p{InLatin_1_Supplement}\\p{InLatin_Extended_A}\\p{InLatin_Extended_B}]*");

    /**
     * 判断字符串是否全部为英文字母（ASCII）
     *
     * @param str
     * @return
     */
    public static boolean isFullLatin(String str) {
        if (str == null) return true;
        return latin.matcher(str).matches();
    }

    /**
     * 判断是否为汉字字符串
     *
     * @param str
     * @return
     */
    private static final Pattern chinese = Pattern.compile("[\\u4E00-\\u9FFF]*");
    private static final Pattern non_chinese = Pattern.compile("[^\\u4E00-\\u9FFF]+");

    public static boolean isFullChinese(String str) {
        if (str == null) return true;
        return chinese.matcher(str).matches();
    }

    public static boolean isFullChineseIgnoreSpace(String str) {
        if (str == null) return true;
        return chinese.matcher(clearSpace(str)).matches();
    }

    public static String clearNonChinese(String str) {
        if (str == null) return str;
        return non_chinese.matcher(str).replaceAll("");
    }

    /**
     * 不可打印的字符：ASCII码中去掉可打印的。
     */
    private static final String UNPRITABLE = "[\\x00-\\xFF&&[^\\p{Print}]]";
    private static final Pattern PATTERN_TO_REMOVE = Pattern.compile("[\\x00-\\xFF&&[^\\p{Print}]]|^\\s+|\\s+$");
    private static final Pattern PATTERN_TO_REDUCE = Pattern.compile("\\s{2,}");

    /**
     * 删除重复的空白
     *
     * @param str
     * @return
     */
    public static String clearDuplicateSpace(String str) {
        if (str == null) return null;
        //String res = str.replaceAll(UNPRITABLE, " ");
        //return res.trim().replaceAll("\\s{2,}", " ");
        return PATTERN_TO_REMOVE.matcher(PATTERN_TO_REDUCE.matcher(str).replaceAll(" ")).replaceAll("");
    }

    /**
     * 删除所有空白
     *
     * @param str
     * @return
     */
    public static String clearSpace(String str) {
        if (str == null) return null;

        String res = str.replaceAll(UNPRITABLE, " ");
        return res.trim().replaceAll("\\s+", "");
    }

//        /**
//         * 将繁体汉字转化为简体汉字
//         * @param str
//         * @return
//         */
//        public static String simplify(String str){
//                if(str == null) {
//                        return null;
//                }
//                return TSChineseMapping.convertChineseT2S(str);
//        }

    /**
     * 将null转化为空串，避免NullPointerException
     *
     * @param str
     * @return
     */
    public static String avoidNull(String str) {
        return avoidNull(str, EMPTY);
    }

    /**
     * 将null转化为指定空串，避免NullPointerException
     *
     * @param str
     * @return
     */
    public static String avoidNull(String str, String empty) {
        if (str == null) {
            return empty;
        } else {
            return str;
        }
    }

    /**
     * 将空串转化为null
     *
     * @param str
     * @return
     */
    public static String avoidEmpty(String str) {
        if (str == null || EMPTY.equals(str)) {
            return null;
        } else {
            return str;
        }
    }

    /**
     * 安全地转化为全大写字母
     *
     * @param str
     * @return
     */
    public static String toUpper(String str) {
        if (str == null) {
            return null;
        } else {
            return str.toUpperCase();
        }
    }

    public static String toFirstUpper(String str) {
        if (str == null) {
            return null;
        }

        String[] components = str.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String c : components) {
            if (!c.isEmpty()) {
                sb.append(c.substring(0, 1).toUpperCase());
                sb.append(c.substring(1).toLowerCase());
            }
            sb.append(" ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * trim的安全版本
     *
     * @param str
     * @return
     */
    public static String safeTrim(String str) {
        if (str == null) {
            return null;
        } else {
            return str.trim();
        }
    }

    public static boolean isValidEmail(String str) {
        return avoidNull(str).toLowerCase().matches(EMAIL_PATTERN_STR);
    }

    public static boolean isValidPassword(String str) {
        return avoidNull(str).toLowerCase().matches(PASSWORD_PATTERN_STR);
    }


    public static <T> String join(String delimeter, T[] array) {
        return join(delimeter, Arrays.asList(array));
    }

    public static <T> String join(String delimeter, Collection<T> c) {
        if (c.size() == 0) {
            return EMPTY;
        }
        StringBuffer buffer = new StringBuffer();
        for (T obj : c) {
            buffer.append(obj.toString());
            buffer.append(delimeter);
        }
        int length = buffer.length();
        buffer.delete(length - delimeter.length(), length);
        return buffer.toString();
    }

    public static <T> String join(String delimeter, Collection<T> c, Convertor<? super T, String> convertor) {
        if (c.size() == 0) {
            return EMPTY;
        }
        StringBuffer buffer = new StringBuffer();
        for (T obj : c) {
            buffer.append(convertor.convert(obj));
            buffer.append(delimeter);
        }
        int length = buffer.length();
        buffer.delete(length - delimeter.length(), length);
        return buffer.toString();
    }

    /**
     * 比较字符list
     */
    public static Comparator<List<String>> listComparator = new Comparator<List<String>>() {
        @Override
        public int compare(List<String> o1, List<String> o2) {
            for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
                int elmentCompare = o1.get(i).compareTo(o2.get(i));
                if (elmentCompare != 0) {
                    return elmentCompare;
                }
            }
            return o1.size() - o2.size();
        }

    };
    /*********************************************************************************
     ** jeecg utils
     *********************************************************************************/
    /**
     * java去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;

    }

    /**
     * 判断元素是否在数组内
     *
     * @param substring
     * @param source
     * @return
     */
    public static boolean isIn(String substring, String[] source) {
        if (source == null || source.length == 0) {
            return false;
        }
        for (int i = 0; i < source.length; i++) {
            String aSource = source[i];
            if (aSource.equals(substring)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Map对象
     */
    public static Map<Object, Object> getHashMap() {
        return new HashMap<Object, Object>();
    }


    /**
     * SET转换MAP
     *
     * @param setobj
     * @return
     */
    public static Map<Object, Object> SetToMap(Set<Object> setobj) {
        Map<Object, Object> map = getHashMap();
        for (Iterator iterator = setobj.iterator(); iterator.hasNext(); ) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iterator.next();
            map.put(entry.getKey().toString(), entry.getValue() == null ? "" : entry.getValue().toString().trim());
        }
        return map;

    }

    /**
     * 判断一个类是否为基本数据类型。
     *
     * @param clazz 要判断的类。
     * @return true 表示为基本数据类型。
     */
    private static boolean isBaseDataType(Class clazz) throws Exception {
        return (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(java.sql.Date.class) || clazz.isPrimitive());
    }

    public static String getString(String s, String defval) {
        if (isEmptyOrNull(s)) {
            return (defval);
        }
        return (s.trim());
    }

    public static String getString(Object s, String defval) {
        if (isEmptyOrNull(s)) {
            return (defval);
        }
        return (s.toString().trim());
    }

    public static String getString(String s) {
        return (getString(s, ""));
    }

    public static String getString(Object object) {
        if (isEmptyOrNull(object)) {
            return "";
        }
        return (object.toString().trim());
    }

    public static String getString(int i) {
        return (String.valueOf(i));
    }

    public static String getString(float i) {
        return (String.valueOf(i));
    }
}
