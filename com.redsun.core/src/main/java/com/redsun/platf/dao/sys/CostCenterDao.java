package com.redsun.platf.dao.sys;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.sys.CostCenter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/8/10
 * Time: 下午 3:42
 * To change this template use File | Settings | File Templates.
 */
public class CostCenterDao extends PagedDao<CostCenter, Long> {

	public CostCenterDao() {
		super();
		this.entityClass = CostCenter.class;
	}


    public CostCenter findById(String value) {
           return findBy("id",value).get(0);
       }
}
