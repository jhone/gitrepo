package com.redsun.platf.entity.sys;

import com.redsun.platf.entity.BaseEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.util.Date;



/**
 * <p>Title: com.walsin.platf.orm.entity.Employee</p>
 * <p>Description: EP_CMN_EMPLOYEE EP員工主檔</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public class Employee extends BaseEntity {

    private static final long serialVersionUID = 9105030358953102570L;
    
    /** 員工編號 */
    private String empId;
       
    /** 員工姓名 */
    private String userName;
       
    /** 員工所屬部門ID */
    private String deptId;
    
    /** E-mail */
    private String mail;
    
    /** Notes Mail */
    private String notesMail;
       
    /** 分機代碼 */
    private String extention;
       
    /** 職稱 */
    private String title;
    
    /** 使用者自訂電話號碼 */
    private String userPhoneNo;

       
    /** 失效日 */
    private Date inactiveDate;
    
//    /** EP系統員工物件 */
//    private SystemUser systemUser;
    
    /** 部門物件 */
    private Department department;
    
    /** 預設公司 */
    private String defaultCompanyCode;
    
    /** 部門對應工廠物件 */
//    private DeptPlantMap deptPlantMap;
    
    
    /**
     * 取得英文名稱
     * @return
     */
    public String userNameEN() {
        return StringUtils.isBlank(notesMail) ? StringUtils.EMPTY : notesMail.substring(0, notesMail.indexOf("/"));
    }
    
    
    public String getEmpId() {
    	empId = empId.replaceAll("\"", "");
        return empId;
    }

    public void setId(String empId) {
            this.empId = empId;
     }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getNotesMail() {
        return notesMail;
    }

    public void setNotesMail(String notesMail) {
        this.notesMail = notesMail;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getInactiveDate() {
        return inactiveDate;
    }

    public void setInactiveDate(Date inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDefaultCompanyCode() {
        return defaultCompanyCode;
    }

    public void setDefaultCompanyCode(String defaultCompanyCode) {
        this.defaultCompanyCode = defaultCompanyCode;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

//    public DeptPlantMap getDeptPlantMap() {
//        return deptPlantMap;
//    }
//
//    public void setDeptPlantMap(DeptPlantMap deptPlantMap) {
//        this.deptPlantMap = deptPlantMap;
//    }
    
    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }


    @Override
    public boolean equals(Object o) {
        Employee employee = (Employee)o;
        return new EqualsBuilder().append(getId(), employee.getId()).isEquals();
    }

 
}
