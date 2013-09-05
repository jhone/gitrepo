package com.redsun.platf.dao.account;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.account.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
//@Component
@Repository
public class     UserDao extends PagedDao<UserAccount, Long> {

	public UserDao() {
		super();
		this.entityClass=UserAccount.class;
	}


}
