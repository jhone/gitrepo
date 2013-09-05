package com.redsun.platf.dao.sys;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.sys.Department;
import org.springframework.stereotype.Repository;

/**
 * 用户对象的泛型DAO类.
 *
 * @author calvin
 */
@Repository
public class DepartmentDao extends PagedDao<Department, Long> {

    public DepartmentDao() {
        super();

        this.entityClass = Department.class;
    }

}
