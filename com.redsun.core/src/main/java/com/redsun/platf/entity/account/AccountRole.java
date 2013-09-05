package com.redsun.platf.entity.account;

import com.google.common.collect.Lists;
import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.entity.sys.Employee;
import com.redsun.platf.entity.sys.SystemCompany;
import com.redsun.platf.entity.sys.SystemValue;
import com.redsun.platf.util.convertor.ConvertUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * 角色.
 * <p/>
 * 注释见{@link SystemValue}.
 *
 * @author calvin
 */
@Entity
@Table(name = "ACCT_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountRole extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 156789436193718012L;

    /*用户组与权限名称对应关系*/
   /*
    @ManyToMany
    @JoinTable(name = "ACCT_ROLE_AUTHORITY",
            joinColumns = {@JoinColumn(name = "ROLE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id")
    */
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Transient
    private List<Authority> authorityList = Lists.newArrayList();

    @ManyToMany(fetch = FetchType.EAGER)

    @JoinTable(name = "ACCT_ROLE_RESOURCES",
            joinColumns = {@JoinColumn(name = "ROLE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RESOURCES_ID")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AccountResources> resourcesList = Lists.newArrayList();
    private Integer enable;

    /**
     * 公司代碼
     */
    private String companyCode;

    /**
     * 角色名稱
     */
    private String roleName;

    /**
     * 公司物件
     */
    private SystemCompany company;

    /**
     * 狀態
     */
    private Boolean enableFlag;

    private Employee createdEmployee;

    private Employee lastUpdatedEmployee;

    public AccountRole() {

    }

    public AccountRole(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }




    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }

    @Transient
    public String getAuthNames() {
        return ConvertUtils.convertElementPropertyToString(authorityList, "name", ", ");
    }


    public List<AccountResources> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(List<AccountResources> resourceses) {
        this.resourcesList = resourceses;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    @Column(nullable = false, unique = true)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public SystemCompany getCompany() {
        return company;
    }

    public void setCompany(SystemCompany company) {
        this.company = company;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Employee getCreatedEmployee() {
        return createdEmployee;
    }

    public void setCreatedEmployee(Employee createdEmployee) {
        this.createdEmployee = createdEmployee;
    }

    public Employee getLastUpdatedEmployee() {
        return lastUpdatedEmployee;
    }

    public void setLastUpdatedEmployee(Employee lastUpdatedEmployee) {
        this.lastUpdatedEmployee = lastUpdatedEmployee;
    }

    @Transient
    @SuppressWarnings("unchecked")
    public List<Long> getAuthIds() {
        return ConvertUtils.convertElementPropertyToList(authorityList, "id");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
