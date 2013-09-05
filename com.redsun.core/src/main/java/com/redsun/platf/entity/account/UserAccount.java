package com.redsun.platf.entity.account;

//~--- non-JDK imports --------------------------------------------------------

import com.google.common.collect.Lists;
import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.entity.sys.Department;
import com.redsun.platf.entity.sys.Plant;
import com.redsun.platf.entity.sys.SystemCompany;
import com.redsun.platf.entity.sys.SystemTxn;
import com.redsun.platf.util.convertor.ConvertUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

//~--- JDK imports ------------------------------------------------------------

/**
 * 用户.
 * <p/>
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义JPA 1.0未覆盖的部分.
 *
 * @author calvin
 * @author pyc
 * @since 1.1 add   activPlants  / Companies
 */
@Entity

//表名与类名不相同时重新定义表名.
@Table(name = "ACCT_USER")

//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserAccount extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3675953268005811114L;

    private String accountId;

    private Department deptId;                                       // 所屬部門
    private String email;
    // 字段非空且唯一, 用于提醒Entity使用者及生成DDL.
    @Column( length = 50,
            nullable = false,
            unique = true
    )
    private String loginName;

    @Column(
            nullable = false, length = 200
    )
    private String password;

    private Integer enable;
    private java.sql.Date birthday;
    private Integer age;
    // AccountRole多对多定义
    @ManyToMany(fetch = FetchType.EAGER)

    // 中间表定义,表名采用默认命名规则
    @JoinTable(
            name = "ACCT_USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")

            }
    )

    // Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)

    // 集合按id排序.
    @OrderBy("id")


    // 集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

    private List<AccountRole> roleList = Lists.newArrayList();    // 有序的关联对象集合
    //add 201208
    // Plant多对多定义
    @ManyToMany(fetch = FetchType.EAGER)

    // 中间表定义,表名采用默认命名规则
    @JoinTable(
            name = "ACCT_USER_PLANTS",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PLANT_ID")}
    )

    // Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)

    // 集合按id排序.
    @OrderBy("id")

    // 集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Plant> activPlants = Lists.newArrayList();

    // SystemCompany多对多定义
    @ManyToMany(fetch = FetchType.EAGER)

    // 中间表定义,表名采用默认命名规则
    @JoinTable(
            name = "ACCT_USER_COMPANIES",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COMPANY_ID")}
    )

    // Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)

    // 集合按id排序.
    @OrderBy("id")

    // 集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<SystemCompany> companies = Lists.newArrayList();

  // SystemCompany多对多定义
    @ManyToMany(fetch = FetchType.EAGER)

    // 中间表定义,表名采用默认命名规则
    @JoinTable(
            name = "ACCT_USER_TXN",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TXN_ID")}
    )

    // Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)

    // 集合按id排序.
    @OrderBy("id")

    // 集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<SystemTxn> txns = Lists.newArrayList();


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<AccountRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AccountRole> roleList) {
        this.roleList = roleList;
    }

    /**
     * 一人对应一个部门
     *
     * @return
     */
    @OneToOne(cascade = CascadeType.REFRESH)
    public Department getDeptId() {
        return deptId;
    }

    public void setDeptId(Department deptId) {
        this.deptId = deptId;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */

    // 非持久化属性.
    @Transient
    public String getRoleNames() {
        return ConvertUtils.convertElementPropertyToString(roleList, "name", ", ");
    }

    /**
     * 用户拥有的角色id字符串, 多个角色id用','分隔.
     */

    // 非持久化属性.
    @Transient
    @SuppressWarnings("unchecked")
    public List<Long> getRoleIds() {
        return ConvertUtils.convertElementPropertyToList(roleList, "id");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    public List<Plant> getActivPlants() {
        return activPlants;
    }

    public void setActivPlants(List<Plant> activPlants) {
        this.activPlants = activPlants;
    }


    public List<SystemCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<SystemCompany> companies) {
        companies = companies;
    }

    public List<SystemTxn> getTxns() {
        return txns;
    }

    public void setTxns(List<SystemTxn> txns) {
        this.txns = txns;
    }
}


