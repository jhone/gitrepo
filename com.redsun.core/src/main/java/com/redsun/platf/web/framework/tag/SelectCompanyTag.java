package com.redsun.platf.web.framework.tag;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.dao.sys.SystemCompanyDao;
import com.redsun.platf.entity.sys.SystemCompany;
import org.hibernate.SQLQuery;

import javax.servlet.ServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: com.redsun.platf.web.framework.tag.SelectCompanyTag</p>
 * <p>Description: 公司標籤</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>company: FreeLance</p>
 *
 * @author Jason Huang
 * @version 1.0
 */
public class SelectCompanyTag extends AbstractSelectEntityTag<SystemCompany> {


    @Override
    public String showText(SystemCompany entity) {
        return entity.getCompanyName();
    }


    @Override
    public List<Long> forceRecycleParentId(Long parentId) {

        //找到parentid =id


/*
        List<SystemCompany> parentEntity =getDao(pageContext.getRequest())
                      .findBy("parentId.id", parentId);

        List<Long> parents =new ArrayList<Long>();
        for (SystemCompany c:parentEntity)
                 parents.add(c.getId());
*/
        String sql = "select id from sys_company  where parent_id=:p  ";
        SQLQuery query = getDao(pageContext.getRequest())
                .createSQLQuery(sql);
        query.setParameter("p", parentId);
        List<BigInteger> parentEntity = query.list();
        List<Long> parents = new ArrayList<Long>();
        for (BigInteger c : parentEntity)
            parents.add(c.longValue());


        //排除掉这个id
        return parents;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IPagedDao<SystemCompany, Long> getDao(ServletRequest request) {
        return getBean(request, SystemCompanyDao.class);
    }
}
