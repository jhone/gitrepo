package com.redsun.platf.web.framework.tag;

import com.redsun.platf.dao.sys.SystemTxnDao;
import com.redsun.platf.entity.sys.SystemTxn;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: com.redsun.platf.web.framework.tag.SelectCompanyTag</p>
 * <p>Description: 公司標籤</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>model: FreeLance</p>
 *
 * @author Jason Huang
 * @version 1.0
 */
public class SelectTxnTag extends AbstractSelectEntityTag<SystemTxn> {


    @Override
    public String showText(SystemTxn entity) {
        return entity.getTxnName();
    }

    @Override
    public List<Long> forceRecycleParentId(Long parentId) {

        //找到parentid =id

        List<SystemTxn> parentEntity =getDao(pageContext.getRequest())
                          .findBy("parentId.id", parentId);
            List<Long> parents =new ArrayList<Long>();
            for (SystemTxn c:parentEntity)
                     parents.add(c.getId());

//        String sql = "select id from sys_txn  where parent_id=:p  ";
//        SQLQuery query = getDao(pageContext.getRequest())
//                .createSQLQuery(sql);
//        query.setParameter("p", parentId);
//        List<Long> parentEntity = query.list();
//         List<Long> parents =new ArrayList<Long>();
 //         for (BigInteger c:parentEntity)
 //                         parents.add(c.longValue());

        //排除掉这个id
        return parents;

    }

    @SuppressWarnings("unchecked")
    public SystemTxnDao getDao(ServletRequest request) {
        return getBean(request, SystemTxnDao.class);
    }
}
