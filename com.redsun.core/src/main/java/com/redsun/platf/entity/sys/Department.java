package com.redsun.platf.entity.sys;

//~--- non-JDK imports --------------------------------------------------------

import com.redsun.platf.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

//~--- JDK imports ------------------------------------------------------------

/**
 * Notice entity. @author MyEclipse Persistence Tools
 */
@Entity

//表名与类名不相同时重新定义表名.
@Table(name = "ACCT_DEPT")

//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 4558123186305133370L;

//    private List<UserAccount> userList = new ArrayList<UserAccount>();

    private Department costs;
    private String department;
    private String deptNo;



    /** 部門代碼 */
//        private String id;

    /**
     * 部門名稱
     */
    private String deptName;

    /**
     * 部門主管員工代碼
     */
    private String mgrUserId;

    /**
     * 上階部門代碼
     */
    private String upDeptId;

    /**
     * 層級代碼
     */
    private String levelId;

    /**
     * 失效日
     */
    private Date inactiveDate;

    /**
     * 上層部門物件
     */
    private Department upDept;

    /**
     * 主管員工物件
     */
    private Employee mgrUser;

      /*
    * 上級部門  ，一對一
    */
    @OneToOne(cascade = CascadeType.ALL)
    private Department parentid;

    /**
     * default constructor
     */
    public Department() {
    }


    public Department getParentid() {
        return parentid;
    }

    public void setParentid(Department parentid) {
        this.parentid = parentid;
    }


    /**
     * 用戶所屬部門，一對多
     */
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "acc_dept_user",
//            joinColumns = {@JoinColumn(
//                    name = "dept_id",
//                    unique = true
//            )},
//            inverseJoinColumns = {@JoinColumn(name = "user_id")}
//    )
//    public List<UserAccount> getUserList() {
//        return userList;
//    }
//
//    public void setUserList(List<UserAccount> userList) {
//        this.userList = userList;
//    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 成本中心，一對一
     */
    @OneToOne(cascade = CascadeType.ALL)

    public Department getCosts() {
        return costs;
    }

    public void setCosts(Department costs) {
        this.costs = costs;
    }


    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    @Override
    public String toString() {
        return "Department{" +
                ", department='" + department + '\'' +
                ", deptNo='" + deptNo + '\'' +
                '}';
    }
}



