package com.redsun.platf.entity.sys;

import com.redsun.platf.entity.BaseEntity;

// default package
// Generated 2010/8/12 下午 05:52:38 by Hibernate Tools 3.3.0.GA

/**
 * 資料庫最高權限使用記錄 Sys105m generated by hbm2java
 */
public class Sys105m extends BaseEntity {

    private static final long serialVersionUID = -5026098794926601832L;
    private String oshost;
    private String osUser;
    private String privilege;
    private String terminal;
    private String status;
    private String cmdText;
    private Sys105mId sys105mId;

    public Sys105m() {
    }

    public Sys105m getNewSys105m(Sys105m oSys105m) {
	Sys105m obj = new Sys105m(oSys105m.getSys105mId());
	obj.setOshost(oSys105m.getOshost());
	obj.setOshost(oSys105m.getOsUser());
	obj.setPrivilege(oSys105m.getPrivilege());
	obj.setTerminal(oSys105m.getTerminal());
	obj.setStatus(oSys105m.getStatus());
	obj.setCmdText(oSys105m.getCmdText());
	return obj;
    }

    public Sys105m(Sys105mId id) {
	this.sys105mId = id;
    }

    public Sys105m(Sys105mId id, String oshost, String osUser,
	    String privilege, String terminal, String status, String cmdText) {
	this.sys105mId = id;
	this.oshost = oshost;
	this.osUser = osUser;
	this.privilege = privilege;
	this.terminal = terminal;
	this.status = status;
	this.cmdText = cmdText;
    }

    public Sys105mId getSys105mId() {
	return this.sys105mId;
    }

    public void setId(Sys105mId id) {
	this.sys105mId = id;
    }

    public String getOshost() {
	return this.oshost;
    }

    public void setOshost(String oshost) {
	this.oshost = oshost;
    }

    public String getOsUser() {
	return this.osUser;
    }

    public void setOsUser(String osUser) {
	this.osUser = osUser;
    }

    public String getPrivilege() {
	return this.privilege;
    }

    public void setPrivilege(String privilege) {
	this.privilege = privilege;
    }

    public String getTerminal() {
	return this.terminal;
    }

    public void setTerminal(String terminal) {
	this.terminal = terminal;
    }

    public String getStatus() {
	return this.status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getCmdText() {
	return this.cmdText;
    }

    public void setCmdText(String cmdText) {
	this.cmdText = cmdText;
    }

}
