package com.redsun.platf.dao.sys;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.sys.Plant;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/8/10
 * Time: 下午 3:46
 * To change this template use File | Settings | File Templates.
 */
public class PlantDao extends PagedDao<Plant, Long> {

    public PlantDao() {
        super();

        this.entityClass = Plant.class;
    }
}
