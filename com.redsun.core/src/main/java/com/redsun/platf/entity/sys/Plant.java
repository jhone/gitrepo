package com.redsun.platf.entity.sys;

import com.google.common.collect.Lists;
import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.entity.account.UserAccount;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/8/10
 * Time: 下午 3:31
 * To change this template use File | Settings | File Templates.
 */
@Entity

//工厂
@Table(name = "ACCT_PLANT")

//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Plant extends BaseEntity {
    private String plantName;

    /**
     * 用户关联多对多*
     */

    @ManyToMany

    // 中间表定义,表名采用默认命名规则
    @JoinTable(
            name = "ACCT_USER_PLANTS",
            joinColumns = {@JoinColumn(name = "PLANT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")}
    )

    // Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)
    // 集合按id排序.
    @OrderBy("id")
    // 集合中对象id的缓存.
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserAccount> users = Lists.newArrayList();

    public Plant() {
    }

    public Plant(String plantName) {
        this.plantName = plantName;
    }


    public List<UserAccount> getUsers() {
        return users;
    }

    public void setUsers(List<UserAccount> users) {
        this.users = users;
    }


    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}
