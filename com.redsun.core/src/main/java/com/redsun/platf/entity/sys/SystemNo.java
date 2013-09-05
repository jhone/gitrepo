package com.redsun.platf.entity.sys;

import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.entity.account.UserAccount;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * <p>
 * Title: com.walsin.platf.system.SystemConfiguration
 * </p>
 * <p>
 * Description: 系統環境設定檔  SERVER NO設定
 * </p>
 * <p>
 * backup_SERVER,exe_server ,mail_server,file_server(report file server)
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
 */

@Entity

@Table(name = "SYS_NO")// 表名

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)// 默认的缓存策略.
public class SystemNo extends BaseEntity {


    /**
     * 代碼
     */
    // 字段非空且唯一, 用于提醒Entity使用者及生成DDL.
    @Column(name = "no", nullable = false, unique = true)
    private String systemNO;

    /**
     * 名稱
     */
    private String systemName;


    /**
     * user
     */
    private String DBAlias;
    /**
     * user
     */
    private String DBUsername;

    /**
     * pass
     */
    private String DBPassword;


    /**
     * 管理员
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserAccount adminUsername;

    public String getSystemNO() {
        return systemNO;
    }

    public void setSystemNO(String systemNO) {
        this.systemNO = systemNO;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDBAlias() {
        return DBAlias;
    }

    public void setDBAlias(String DBAlias) {
        this.DBAlias = DBAlias;
    }

    public String getDBUsername() {
        return DBUsername;
    }

    public void setDBUsername(String DBUsername) {
        this.DBUsername = DBUsername;
    }

    public String getDBPassword() {
        return DBPassword;
    }

    public void setDBPassword(String DBPassword) {
        this.DBPassword = DBPassword;
    }

    public UserAccount getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(UserAccount adminUsername) {
        this.adminUsername = adminUsername;
    }
}
