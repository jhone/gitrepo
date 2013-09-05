package com.redsun.platf.dao.tag.easyui.model.entity;

import com.redsun.platf.entity.IdEntity;

import javax.persistence.*;

/**
 * TSRoleUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_s_role_user")
public class TSRoleUser extends IdEntity implements java.io.Serializable {
	private TSUser TSUser;
	private TSRole TSRole;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	public TSUser getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(TSUser TSUser) {
		this.TSUser = TSUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleid")
	public TSRole getTSRole() {
		return this.TSRole;
	}

	public void setTSRole(TSRole TSRole) {
		this.TSRole = TSRole;
	}

}