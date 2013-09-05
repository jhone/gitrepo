package com.redsun.platf.dao.sys;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.sys.SystemMail;
import org.springframework.stereotype.Component;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class SystemMailDao extends PagedDao<SystemMail, Long> {

	public SystemMailDao() {
		super();
		this.entityClass=SystemMail.class;
	}


	
}
