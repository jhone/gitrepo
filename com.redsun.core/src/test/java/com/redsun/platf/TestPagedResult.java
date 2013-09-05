package com.redsun.platf;

import com.redsun.platf.util.sideutil.PagedResult;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/11/30
 * Time: 上午 10:44
 * To change this template use File | Settings | File Templates.
 */
public class TestPagedResult {

    @Test
    public void testPagedResult(){
        PagedResult page =new PagedResult(14);
        System.out.println(page.getPage());
        System.out.println(page.getCurrentPage());

        System.out.println("....."+page.getOrderBy()+","+page.getSord());
        page.setOrderBy(PagedResult.DESC);
        System.out.println("....."+page.getOrderBy()+","+page.getSord());


    }
}
