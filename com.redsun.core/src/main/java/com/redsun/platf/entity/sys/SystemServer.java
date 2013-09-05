package com.redsun.platf.entity.sys;

import com.redsun.platf.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 * Title: com.walsin.platf.system.SystemConfiguration
 * </p>
 * <p>
 * Description: 系統環境設定檔  SERVER設定
 * </p>
 * <p>
 *    backup_SERVER,exe_server ,mail_server,file_server(report file server)
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
 *
 */

@Entity
// 表名与类名不相同时重新定义表名.
@Table(name = "SYS_SERVER")
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemServer extends BaseEntity {


    /**
     * 代碼
     */
    // 字段非空且唯一, 用于提醒Entity使用者及生成DDL.
    @Column(name = "no", nullable = false, unique = true)
    private String serverNo;

    /**
     * 名稱
     */
    private String serverName;

    /**
     * IP
     */
    private String IP;

    /**
     * user
     */
    private String username;

    /**
     * pass
     */
    private String password;

    /**
     * path
     */
    private String path;

    public String getServerNo() {
        return serverNo;
    }

    public void setServerNo(String serverNo) {
        this.serverNo = serverNo;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
