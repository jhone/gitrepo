package com.redsun.platf.service.sys.impl;

import com.redsun.platf.dao.DataAccessObjectFactory;
import com.redsun.platf.entity.sys.SystemValue;
import com.redsun.platf.sys.SystemConfiguration;
import com.redsun.platf.util.search.MorePropertyFilter;
import com.redsun.platf.util.sideutil.Page;
import com.redsun.platf.util.sideutil.PagedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author calvin
 */
@Service
@Transactional
public class SystemManager {

	private static Logger logger = LoggerFactory.getLogger(SystemManager.class);

	@Autowired
	SystemConfiguration systemConfig;

	@Autowired
	DataAccessObjectFactory daoFactory;

	/****************** begin of system value *******************/
	@Transactional(readOnly = true)
	public SystemValue getSystemValue(Long id) {
		return daoFactory.getSystemValueDao().get(id);
	}

	public void saveSystemValue(SystemValue entity) {
		daoFactory.getSystemValueDao().save(entity);
		logger.info("saved ok!");
	}

	public void deleteSystemValue(Long id) {
		daoFactory.getSystemValueDao().delete(id);
	}

	@Transactional(readOnly = true)
	public Page<SystemValue> findPageSystemValue(final PagedResult<SystemValue> page,
			final List<MorePropertyFilter> filters) {
		return daoFactory.getSystemValueDao().findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SystemValue findUniqueBySystemValueKey(String s) {
		return daoFactory.getSystemValueDao().findUniqueBy("sysNo", s);
	}

	@Transactional(readOnly = true)
	public boolean isSystemValueNoUnique(String newValue, String oldValue) {
		String fieldName = "sysNo";

		return daoFactory.getSystemValueDao().isPropertyUnique(fieldName,
				newValue, oldValue);
	}

	public List<SystemValue> getAllSystemValue() {
		return daoFactory.getSystemValueDao().getAll();
	}

	/****************** end of system value *******************/

	
	
}
