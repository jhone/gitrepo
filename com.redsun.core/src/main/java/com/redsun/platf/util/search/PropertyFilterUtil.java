package com.redsun.platf.util.search;

import com.redsun.platf.util.JsonUtils;
import com.redsun.platf.util.LogUtils;
import com.redsun.platf.util.MultipleSearchFilter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.*;

/**
 * 自由查找的相關類
 *
 * @author pyc 2012/12/12
 */
public class PropertyFilterUtil {

    protected static Logger logger = LogUtils.getLogger(PropertyFilterUtil.class);

    private static final String split = "_";  //連接符
    private static String[] matchTypes;   //操作字
    private static Map<Class, String> typeEnmString = new HashMap<Class, String>();//類型字

    /**
     * 初始化所有  matchType & filterType
     */
    static {
        EnumSet<MorePropertyFilter.MatchType> codeSet =
                EnumSet.allOf(MorePropertyFilter.MatchType.class);
        matchTypes = new String[codeSet.size()];

        int i = 0;
        for (MorePropertyFilter.MatchType tc : codeSet) {
            matchTypes[i++] = tc.toString();
        }

        setAllProperType();
    }

    /**
     * 取得支持的欄位類型，與字竄，eg:java.lang.String ->S
     *
     * @return Map<Class, String> 返回所有類型 字竄表
     */

    private static void setAllProperType() {

//        Map<Class, String> typeEnmString = new HashMap<Class, String>();

        /*通过values()获得枚举值的数组*/
        for (MorePropertyFilter.PropertyType c : MorePropertyFilter.PropertyType.values()) {
            //            System.out.println(c);
            Class<?> clazz = Enum.valueOf(MorePropertyFilter.PropertyType.class, c.name()).getValue();
            //            System.out.println(clazz);

            typeEnmString.put(clazz, c.toString());
            //            findClass(clazz,c.toString());


        }

    }


    /**
     * @param clzz          entity class
     * @param operationType 比较操作符 EQ LQ LIKE
     * @param searchField   查找的字段名称
     * @param searchString  查找的内容
     * @return list< PropertyFilter>
     * @author P.Y.C
     * @since 1.0.0 (2011/03/16)
     *        <p/>
     *        Build a ProperFilter String 根据给定的类、字段、查找内容，建立查找条件
     */
    public static List<MorePropertyFilter> buildSearchFilter(Class<?> clzz,
                                                             MorePropertyFilter.MatchType operationType,
                                                             String searchField,
                                                             String searchString) {

        String propertyTypeCode = decodePropertyType(clzz, searchField);

        List<MorePropertyFilter> searchfilters = new ArrayList<MorePropertyFilter>();
        // String split = "_";
        String filterName = operationType + propertyTypeCode + split
                + searchField;// eg.LIKES_NAME_OR_LOGIN_NAME

        searchfilters.add(new MorePropertyFilter(filterName, searchString));
        return searchfilters;
    }

    /**
     * @param clzz
     * @param operationType
     * @param searchField
     * @param searchString
     * @return
     * @see PropertyFilterUtil//.buildSearchFilter()
     */

    private static MorePropertyFilter buildOneFilter(Class<?> clzz,
                                                     String operationType,
                                                     String searchField,
                                                     String searchString) {
        //check available code . fieldName
        check(clzz, operationType, searchField, searchString);

        String propertyTypeCode = decodePropertyType(clzz, searchField);

//        List<MorePropertyFilter> searchfilters = new ArrayList<MorePropertyFilter>();
        // String split = "_";
        String filterName = operationType + propertyTypeCode + split
                + searchField;// eg.LIKES_NAME_OR_LOGIN_NAME

        return new MorePropertyFilter(filterName, searchString);
    }

    private static void check(Class<?> clazz, String operationType, String searchField, String searchString) {
        if (!isMatchTypeAvaiable(operationType))//操作符合法
            throw new IllegalArgumentException(String.format(
                    "非法操作符\"%s\"", operationType));

        Map<String, Class> fieldsTypeList = getEntityProperType(clazz);//類型列表

        if (fieldsTypeList.get(searchField) == null) {
            throw new IllegalArgumentException(String.format(
                    "类\"%s\"中不存在字段名稱不存在\"%s\"，請注意大小寫！",
                    clazz.getCanonicalName(),
                    searchField));
        }

    }

    /**
     * @param clzz
     * @param operationType
     * @param searchField
     * @param searchString
     * @return
     */
    public static List<MorePropertyFilter> buildSearchFilter(Class<?> clzz,
                                                             String operationType, String searchField, String searchString) {

        String propertyTypeCode = decodePropertyType(clzz, searchField);

        List<MorePropertyFilter> searchfilters = new ArrayList<MorePropertyFilter>();

        // build filter string eg.LIKES_NAME_OR_LOGIN_NAME

        operationType = operationType.toUpperCase();
        // contains ==>like
        if (operationType.equals("CN")) {
            operationType = MorePropertyFilter.MatchType.LIKE.toString();
        }

        String filterName = operationType + propertyTypeCode + split
                + searchField;

        // System.out.println(filterName+",operationType:"+operationType);

        searchfilters.add(new MorePropertyFilter(filterName, searchString));

        return searchfilters;
    }

    /**
     * get simple code of gen type
     *
     * @param clazz
     * @param searchField
     * @return 类别代码
     */
    private static String decodePropertyType(Class<?> clazz, String searchField) {
        Map<String, String> typeStringMap = findPropertyTypeString(clazz);


        return typeStringMap.get(searchField);
    }
//    private static String decodePropertyType(Class<?> clazz, String searchField) {
//        Field fieldName = null;
//        try {
//            fieldName = clazz.getDeclaredField(searchField);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            throw new IllegalArgumentException(String.format(
//                    "类\"%s\"中不存在字段名\"%s\"！", clazz.getCanonicalName(),
//                    searchField));
//            // e.printStackTrace();
//        }
//
//        EnumSet<PropertyType> set = EnumSet.allOf(PropertyType.class);
//
//        Map<Class<?>, String> codeMap = new HashMap<Class<?>, String>(set
//                .size());
//        for (PropertyType tc : set) {
//            codeMap.put(tc.getValue(), tc.toString());
//            // System.out.println(tc.getClass().getSimpleName());
//            // System.out.println(tc.getValue());
//            // System.out.println(tc.toString());
//        }
//        return eval(codeMap, fieldName.getType());
//
//    }

    /**
     * search type code from map<type,code>
     *
     * @param codeMap
     * @param clzzType
     * @return 类别代码
     */
    private static String eval(Map<Class<?>, String> codeMap, Class<?> clzzType) {
        if (clzzType == null)
            return null;

        if (codeMap.containsKey(clzzType)) {
            return codeMap.get(clzzType);
        } else {
            throw new IllegalArgumentException(String.format(
                    "代码\"%s\"不是一个合法的类别代码！", clzzType));
        }
    }


    /**
     * 查找給定的類型 的所有字段，對應的類型字符竄
     * eg:Date creatationDate  D ;
     * Long id              N;
     *
     * @param clazzEntity
     * @author pyc 2012/12/12
     */
    public static Map<String, String> findPropertyTypeString(Class clazzEntity) {


        Map<String, String> typeMap = new HashMap<String, String>();

        Map<String, Class> fieldsTypeList = getEntityProperType(clazzEntity);//類型列表

        //        System.out.println("total record:" + fieldsTypeList.size());
        //        for (String name : fieldsTypeList.keySet())
        //            System.out.println(name + ":" + fieldsTypeList.get(name).getCanonicalName());

        Iterator it = fieldsTypeList.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();

            Class<?> value = (Class<?>) entry.getValue();
            boolean found = false;

            // skip java.lang.Class
            if (value.getCanonicalName().equals(Class.class.getCanonicalName())) {
                //                System.out.println("class skip" + value.getCanonicalName());
                continue;   //go next
            }

            //            System.out.println("find...:" + key);
            for (Class clazz : typeEnmString.keySet()) {

                if (clazz.getCanonicalName().equals(value.getCanonicalName())) {
                    String typeString = typeEnmString.get(clazz);

                    typeMap.put(key, typeString);
                    found = true;
                    //                    System.out.println("field:" + key + ", type String:" + typeString);
                    break;  //found and go out
                }
            }
            if (!found)
                System.out.println("err field:" + key + ",not found type" + value.getCanonicalName());
        }

        return typeMap;

    }


    /**
     * 取得給定類的各欄位類型 class
     *
     * @param clazz
     * @return Map <FieldName,Class>
     * @author pyc 2012/12/12
     */
    private static Map<String, Class> getEntityProperType(final Class<?> clazz) {
        Map<String, Class> typeList = new HashMap<String, Class>();

        PropertyDescriptor[] propertyDescriptors =
                PropertyUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            if (null != descriptor.getReadMethod()) {
                String fieldName = descriptor.getName();
                typeList.put(fieldName, descriptor.getPropertyType());
            } /*else {
                       System.out.println(descriptor.getName() + "--can't found readMethod ");
                   }*/

        }

        return typeList;
    }


    /**
     * 簡單查詢
     *
     * @return
     */
    //filter:
    //  {"groupOp":"OR","rules":[
    //        {"field":"name","op":"le","data":"CP"},
    //        {"field":"lastUpdatedBy","op":"eq","data":"EPSYS"},
    //        {"field":"name","op":"eq","data":"EPSYS"}
    //    ]}
    private static MultipleSearchFilter praseSearchJson(final String filtersJSON) {
        logger.debug("json string:{}", filtersJSON);
        MultipleSearchFilter multipleSearchFilter = null;
        try {
            multipleSearchFilter = (MultipleSearchFilter) JsonUtils.prase(filtersJSON, MultipleSearchFilter.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipleSearchFilter;

    }


    /**
     * 从json string中创建PropertyFilter列表
     * PropertyFilter命名规则为Filter属性前缀_比较类型属性类型_属性名.
     * <p/>
     * eg.
     * filter_EQS_name
     * filter_LIKES_name_OR_email
     */

    public static List<MorePropertyFilter> buildFilterFromJson(final String filtersJSON,
                                                               Class<?> clazz) {

        MultipleSearchFilter multipleSearchFilter = praseSearchJson(filtersJSON);

        List<MorePropertyFilter> filterList = new ArrayList<MorePropertyFilter>();


        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        // Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request, filterPrefix + "_");

        //分析参数List,构造PropertyFilter列表
        List<SearchFilterRule> rules = multipleSearchFilter.getRules();
        String andOr = multipleSearchFilter.getGroupOp().toUpperCase();

        String andOper = "";
        if ("OR".equalsIgnoreCase(andOr))
            andOper = MorePropertyFilter.OR_SEPARATOR;

        for (SearchFilterRule rule : rules) {
            String searchField = rule.getField();
            String value = rule.getData();
            String operationType = rule.getOp().toUpperCase();

            //類型
//              String fieldType = fieldsTypeList.get(field.toLowerCase()).getCanonicalName();


            //操作動作+比较属性+_+属性名稱。。。
//              String filterName = field + "_" + operation + "_" + andOr;


            //如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {

                 /*new MorePropertyFilter(filterName, value)*/
                MorePropertyFilter filter = buildOneFilter(clazz, operationType,searchField,
                                                        value);
                filterList.add(filter);
                logger.debug("filter add:{}", filter);

            }
        }

        return filterList;
    }

    /**
     * 檢查code 是否有效
     *
     * @param code
     * @return
     */

    private static boolean isMatchTypeAvaiable(String code) {

        return ArrayUtils.indexOf(matchTypes, code) > -1;
    }

}
