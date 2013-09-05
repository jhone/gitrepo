package com.redsun.platf.entity;

/**
 * <p>Title        : com.webapp        </p>
 * <p>Copyright    : Copyright (c) 2010</p>
 * <p>Company      : FreedomSoft       </p>
 *
 * <pre>
 * ----------------------------------------------------------------------------- </pre>
 * Program ID   : @See com.redsun.platf.entity.account.TxnResourceAuthority
 * Program Name :  权限自定议类型
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------
 * 1.0     13-6-6      joker       权限码AUDIP ，和txn=>resource关联=>role
 *                                 authorCode=0x11111111;
 *                                 16个权限码，全为1时表示所有权限
 * </pre>
 */

import com.redsun.platf.util.convertor.Convertor;
import com.redsun.platf.util.convertor.Stringfier;
import org.springframework.security.acls.domain.BasePermission;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;

public enum AuthorityType implements Serializable {
    /**
     * 新增, "1000000000000"
     * 修改, "0100000000000"
     */
    SUPER(1 + 2 + 4 + 6 + 8 + 16, "所有权限"),
    ADD(1, "新增"),
    CREATE(2, "修改"),
    DELETE(4, "删除"),
    QUERY(8, "查询"),
    PRINT(16, "打印");

    BasePermission permission;//mask

    private Integer code;
    private String cnname;

    private AuthorityType(Integer code, String name) {
        this.code = code;
        this.cnname = name;
    }

    public String getCnname() {
        return cnname;
    }


    public Integer getCode() {
        return code;
    }

    public String toString() {
        return code.toString();
    }

    public static boolean canAll(AuthorityType c) {
        return c == SUPER;
    }

    public static boolean canRead(AuthorityType c) {
        return c == ADD;
    }

    public static boolean canWrite(AuthorityType c) {
        return c == CREATE;
    }

    public static boolean canDelete(AuthorityType c) {
        return c == DELETE;
    }

    public static boolean canQuery(AuthorityType c) {
        return c == QUERY;
    }

    public static boolean canPrint(AuthorityType c) {
        return c == PRINT;
    }

    /**
     * 返回当前类型的所有值hashMap
     */
    public static final HashMap<Integer, AuthorityType> codeMap;

    static {
        EnumSet<AuthorityType> set = EnumSet.allOf(AuthorityType.class);
        codeMap = new HashMap<Integer, AuthorityType>(set.size());
        for (AuthorityType c : set) {
            codeMap.put(c.code, c);
        }
    }

    public static AuthorityType eval(Integer str) {
        if (str == 0) {
            return null;
        }
        if (codeMap.containsKey(str)) {
            return codeMap.get(str);
        } else {
            throw new IllegalArgumentException(String.format(
                    "代码\"%d\"不是一个合法的权限代码！", str));
        }
    }

    /**
     * 根据类别代码显示类别
     */
    public static Convertor<Integer, AuthorityType> convertor = new Convertor<Integer, AuthorityType>() {

        @Override
        public AuthorityType convert(Integer s) {
            return AuthorityType.eval(s);
        }

    };

    /**
     * 显示中文
     */
    public static Stringfier<AuthorityType> cnStringfier = new Stringfier<AuthorityType>() {

        @Override
        public String convert(AuthorityType s) {

            return s.getCnname();
        }

    };


    /**
     * type define
     *
     * @author dick pan
     * @version 1.0
     * @since 1.0
     *        <p>
     *        <H3>Change history</H3>
     *        </p>
     *        <p>
     *        2011/3/4 : Created
     *        </p>
     */

//    public static class Type implements EnhancedUserType {
//        @Override
//        public Object fromXMLString(String arg0) {
//            return AuthorityType.eval(Integer.parseInt(arg0));
//        }
//
//        @Override
//        public String objectToSQLString(Object arg0) {
//            return String.format("\'%s\'", arg0.toString());
//        }
//
//        @Override
//        public String toXMLString(Object arg0) {
//            return arg0.toString();
//        }
//
//        @Override
//        public Object assemble(Serializable arg0, Object arg1)
//                throws HibernateException {
//            return arg0;
//        }
//
//        @Override
//        public Object deepCopy(Object arg0) throws HibernateException {
//            return arg0;
//        }
//
//        @Override
//        public Serializable disassemble(Object arg0) throws HibernateException {
//            return (Serializable) arg0;
//        }
//
//        @Override
//        public boolean equals(Object arg0, Object arg1)
//                throws HibernateException {
//            if (arg0 == arg1)
//                return true;
//            else
//                return false;
//        }
//
//        @Override
//        public int hashCode(Object arg0) throws HibernateException {
//            return arg0.hashCode();
//        }
//
//        @Override
//        public boolean isMutable() {
//            return false;
//        }
//
//        @Override
//        public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
//                throws HibernateException, SQLException {
//            String code = rs.getString(names[0]);
//            return rs.wasNull() ? null : AuthorityType.eval(Integer.parseInt(code));
//        }
//
//        @Override
//        public void nullSafeSet(PreparedStatement ps, Object value, int index)
//                throws HibernateException, SQLException {
//            if (value == null) {
//                ps.setNull(index, Types.VARCHAR);
//            } else {
//                ps.setString(index, value.toString());
//            }
//
//        }
//
//        @Override
//        public Object replace(Object arg0, Object arg1, Object arg2)
//                throws HibernateException {
//            return arg0;
//        }
//
//        @Override
//        public Class<AuthorityType> returnedClass() {
//            return AuthorityType.class;
//        }
//
//        @Override
//        public int[] sqlTypes() {
//            return new int[]{Types.VARCHAR};
//        }
//    }


}
