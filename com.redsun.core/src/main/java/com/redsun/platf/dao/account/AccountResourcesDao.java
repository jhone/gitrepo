package com.redsun.platf.dao.account;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.account.AccountResources;
import org.springframework.stereotype.Repository;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Repository
public class AccountResourcesDao extends PagedDao<AccountResources, Long> {

	public AccountResourcesDao() {
		super();
		this.entityClass=AccountResources.class;
	}
	
}
