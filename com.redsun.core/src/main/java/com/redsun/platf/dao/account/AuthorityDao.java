package com.redsun.platf.dao.account;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.account.Authority;
import org.springframework.stereotype.Repository;

/**
 * 授权对象的泛型DAO.
 * 
 * @author calvin
 */
@Repository
public class AuthorityDao extends PagedDao<Authority, Long> {
	public AuthorityDao() {
		super();
		this.entityClass=Authority.class;
	}

}
