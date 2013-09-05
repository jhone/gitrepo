package com.redsun.platf.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义id的entity基类.
 * <p/>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 *
 * @author calvin
 */
//JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    //@GeneratedValue(generator = "system-uuid")
    //@GenericGenerator(name = "system-uuid", strategy = "uuid")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 如果是一個字竄時(1,2,3)，只用第一個
     *
     * @param idListString
     * @Author PYC
     * @since 2012.12.04
     * httprequest 仍無法使用
     *
     */
    /*public void setId(String idListString) {
        Long lId = -1L;
        if (!StringUtils.isEmpty(idListString)) {
            String[] source = idListString.split(",");
            String s = source[0];
            lId = (Long) ConvertUtils.convertStringToObject(s, Long.class);
            System.out.println("long id Prase"+lId);
        }
        this.id = lId;
    }*/
}
