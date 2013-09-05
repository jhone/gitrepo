package com.redsun.platf.entity.sys;

import com.google.common.collect.Lists;
import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.entity.account.UserAccount;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Title: com.walsin.platf.system.SystemConfiguration
 * </p>
 * <p>
 * Description: 系統環境設定檔
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: FreeLance
 * </p>
 *
 * @author Jason Huang
 * @version 1.1
 *          companies -manyToMany -user
 */

@Entity
// 表名与类名不相同时重新定义表名.
@Table(name = "SYS_COMPANY")
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemCompany extends BaseEntity {


    /**
     * 代碼
     */
    // 字段非空且唯一, 用于提醒Entity使用者及生成DDL.
    @Column(name = "company_no" , nullable = false, unique = true)

    private String companyNo;

    /**
     * 名稱
     */

    @Size(min=3, max=20, message="名称长度只能在3-20之间")
    private String companyName;

    /**
     * Mail
     */
    @Pattern(regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",message="{message[valEmail]}")
//    @Email
    private String email;

    /**
     * tel
     */
    private String tel;

    /**
     * fax
     */
    private String fax;

    /**
     * 稅號
     */
    private String taxNo;

    /**
     * 帳號
     */
    private String bankNo;

    /**
     * 開戶行 *
     */
    private String bankName;
    /**
     * 城市 地址
     */

    private String state;
    private String city;
    @Size(min =3,max = 20,message = "{error.field.too-low}")
    private String address;
    /**
     *   联系人
     */
    private String contact;
    /**
     * 预设折扣
     */
    @Column( columnDefinition = "decimal(5,2) default 1.00",length = 5,precision = 2 )
    @DecimalMin(value = "1",message = "{error.systemCompany.defaultRate.too-low}")
    @DecimalMax(value = "100")

    private BigDecimal defaultRate;

    /*
    * 上級  ，一對一
    */
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(nullable = true)
     private SystemCompany parentId;

//    //childern
//    @OneToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//      // 集合按id排序.
//    @OrderBy("parentId")
//    private List<SystemCompany> children;

    /**
     * 用户关联多对多
     */
    @ManyToMany

    // 中间表定义,表名采用默认命名规则
    @JoinTable(
            name = "ACCT_USER_COMPANIES",
            joinColumns = {@JoinColumn(name = "COMPANY_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")}
    )

    // Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)

    // 集合按id排序.
    @OrderBy("id")

    // 集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserAccount> users = Lists.newArrayList();


    public List<UserAccount> getUsers() {
        return users;
    }

    public void setUsers(List<UserAccount> users) {
        this.users = users;
    }


    public SystemCompany getParentId() {
        return parentId;
    }

    public void setParentId(SystemCompany parentid) {
        this.parentId = parentid;
    }


    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public BigDecimal getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(BigDecimal defaultRate) {
        this.defaultRate = defaultRate;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);

    }


}
