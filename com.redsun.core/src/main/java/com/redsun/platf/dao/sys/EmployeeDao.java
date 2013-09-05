package com.redsun.platf.dao.sys;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.sys.Employee;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/8/10
 * Time: 下午 3:29
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeDao extends PagedDao<Employee, Long> {

    public EmployeeDao() {
        super();

        this.entityClass = Employee.class;
    }
}
