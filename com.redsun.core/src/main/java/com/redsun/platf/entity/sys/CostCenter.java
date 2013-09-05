package com.redsun.platf.entity.sys;

import com.redsun.platf.entity.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/8/10
 * Time: 下午 3:41
 * To change this template use File | Settings | File Templates.
 */
public class CostCenter extends BaseEntity {
       private String costCenterName;

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }
}
