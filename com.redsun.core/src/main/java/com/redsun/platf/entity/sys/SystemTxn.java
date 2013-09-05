package com.redsun.platf.entity.sys;

import com.redsun.platf.entity.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * Title: com.walsin.platf.orm.entity.SystemTxn
 * </p>
 * <p>
 * Description: 交易選單資料主檔EP_SYS_TXN
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: FreeLance
 * </p>
 * 20130516增加 ：
 * 程式狀態 P－正式；T－測試 *
 * 系統別 *
 * 是否下線 *
 * 下線日期 *
 * 是否執行LOG *
 * 關聯程式代碼 *
 *
 * @author Jason Huang
 * @version 1.0
 */
@Entity
@Table(name = "SYS_TXN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemTxn extends BaseEntity {

    private static final long serialVersionUID = -2707788189716932398L;

    /**
     * 交易代碼 kb
     */
    @Column(nullable = false)

    private String txnNo;

    /**
     * 交易名稱 prompt
     */
    @Column(length = 100, nullable = false, unique = true)
    private String txnName;

    /**
     * 交易程式URL doprg
     */
    @Column(length = 100)
    private String txnUrl;

    /**
     * 交易項目描述 message
     */
    @Column(length = 200)
    private String txnDesc;

    /**
     * 是否顯示公司別下拉式選單
     */
    private String displayCompanyScrollbar;

    // /** 上階ID parentid */
    // private String upperLevelId;

    /**
     * 顯示順序
     */
    private Integer dispOrder;
    /**
     * 程式狀態 P－正式；T－測試 *
     */
    private String txnStatus;

    /**
     * 系統別 *
     */
    @ManyToOne(fetch = FetchType.EAGER ,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private SystemNo systemNo;
    /**
     * 是否下線 *
     */

    @Column(
                nullable = false,columnDefinition ="bit default 0"
        )
    private boolean closed;
    /**
     * 下線日期 *
     */
    private Timestamp closeDate;
    /**
     * 是否執行LOG *
     */


    @Column(
            nullable = false,columnDefinition ="bit default 1"
    )

    private boolean saveLog;
    /**
     * 關聯程式代碼 *
     */
//    @ManyToOne(fetch = FetchType.EAGER ,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private SystemTxn connectTxn;

    /**
     * 父選單
     */
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private SystemTxn parentId;

//    /**
//     * 子選單
//     */
//    @OneToMany(fetch = FetchType.EAGER ,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "parent_id")
//    private List<SystemTxn> childTxn;

    /**
     * report file name *
     */
    private String reportFilename;

    /*icon*/
    private String icon;

   /* //多对多定义
        @ManyToMany
        //中间表定义,表名采用默认命名规则
        @JoinTable(name = "ACCT_TXN_RESOURCES",
                joinColumns = {@JoinColumn(name = "TXN_ID")},
                inverseJoinColumns = {@JoinColumn(name = "RESOURCES_ID")})
        //Fecth策略定义
        @Fetch(FetchMode.SUBSELECT)
        //集合按id排序.
        @OrderBy("id")
        //集合中对象id的缓存.
        @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

        private  List<AccountResources> resources= Lists.newArrayList();
    */
    public SystemTxn() {
    }

    public SystemTxn(String txnNo, String txnName, String txnUrl, String txnDesc, String displayCompanyScrollbar, Integer dispOrder, String txnStatus, SystemNo systemNo, boolean closed, Timestamp closeDate, boolean saveLog, SystemTxn connectTxn, SystemTxn parentId, List<SystemTxn> childTxn, String reportFilename) {
        this.txnNo = txnNo;
        this.txnName = txnName;
        this.txnUrl = txnUrl;
        this.txnDesc = txnDesc;
        this.displayCompanyScrollbar = displayCompanyScrollbar;
        this.dispOrder = dispOrder;
        this.txnStatus = txnStatus;
        this.systemNo = systemNo;
        this.closed = closed;
        this.closeDate = closeDate;
        this.saveLog = saveLog;
        this.parentId = parentId;
        this.reportFilename = reportFilename;
    }

    public String getTxnName() {
        return txnName;
    }

    public void setTxnName(String txnName) {
        this.txnName = txnName;
    }


    public String getTxnUrl() {
        return txnUrl;
    }

    public void setTxnUrl(String txnUrl) {
        this.txnUrl = txnUrl;
    }


    public String getTxnDesc() {
        return txnDesc;
    }

    public void setTxnDesc(String txnDesc) {
        this.txnDesc = txnDesc;
    }

    public String getDisplayCompanyScrollbar() {
        return displayCompanyScrollbar;
    }

    public void setDisplayCompanyScrollbar(String displayCompanyScrollbar) {
        this.displayCompanyScrollbar = displayCompanyScrollbar;
    }


    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }





    public String getTxnNo() {
        return txnNo;
    }

    public void setTxnNo(String txnNo) {
        this.txnNo = txnNo;
    }

    public String getReportFilename() {
        return reportFilename;
    }

    public void setReportFilename(String reportFilename) {
        this.reportFilename = reportFilename;
    }


    public SystemTxn getParentId() {
        return parentId;
    }

    public void setParentId(SystemTxn parentId) {
        this.parentId = parentId;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public SystemNo getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(SystemNo systemNo) {
        this.systemNo = systemNo;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }

    public boolean isSaveLog() {
        return saveLog;
    }

    public void setSaveLog(boolean saveLog) {
        this.saveLog = saveLog;
    }

//    public SystemTxn getConnectTxn() {
//        return connectTxn;
//    }
//
//    public void setConnectTxn(SystemTxn connectTxn) {
//        this.connectTxn = connectTxn;
//    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);

    }


}
