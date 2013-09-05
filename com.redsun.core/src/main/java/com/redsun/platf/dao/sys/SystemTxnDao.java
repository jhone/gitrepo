package com.redsun.platf.dao.sys;

import org.springframework.stereotype.Component;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.sys.SystemTxn;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class SystemTxnDao extends PagedDao<SystemTxn, Long> {

	public SystemTxnDao() {
		super();
		this.entityClass=SystemTxn.class;
	}


	
}
