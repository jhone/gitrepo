package com.redsun.platf.entity.account;

import com.google.common.collect.Lists;
import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.entity.sys.SystemTxn;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * AccountResources entity. @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "ACCT_RESOURCES")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountResources extends BaseEntity {

    // Fields

    private String url;
    private Integer priority;
    private Integer type;
    private String resourceName;
    private String memo;

    //多对多定义
    @ManyToMany(fetch = FetchType.EAGER)
    //中间表定义,表名采用默认命名规则
    @JoinTable(name = "ACCT_ROLE_RESOURCES",
            joinColumns = {@JoinColumn(name = "RESOURCES_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    //Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)
    //集合按id排序.
    @OrderBy("id")
    //集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

    private  List<AccountRole> roles= Lists.newArrayList();

   //多对多定义
   @ManyToMany(fetch = FetchType.EAGER)
    //中间表定义,表名采用默认命名规则
    @JoinTable(name = "ACCT_TXN_RESOURCES",
            joinColumns = {@JoinColumn(name = "RESOURCES_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TXN_ID")})
    //Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)
    //集合按id排序.
    @OrderBy("id")
    //集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

    private  List<SystemTxn> txns= Lists.newArrayList();

    public List<SystemTxn> getTxns() {
        return txns;
    }

    public void setTxns(List<SystemTxn> txns) {
        this.txns = txns;
    }

    public List<AccountRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AccountRole> roles) {
        this.roles = roles;
    }



    // Constructors

    /**
     * default constructor
     */
    public AccountResources() {
    }

    public AccountResources(String url, String resourceName, Integer type, Integer priority) {
        this.url = url;
        this.resourceName = resourceName;
        this.type = type;
        this.priority = priority;
    }

    public String getMemo() {
        return memo;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}