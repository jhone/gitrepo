package com.redsun.platf.dao.tag.easyui.model.common;

import com.redsun.platf.entity.account.UserAccount;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;


public class SessionInfo implements Serializable {

	private UserAccount user;
	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

}
