package com.redsun.platf.util.treenode.entitynode;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.sys.SystemCompany;

import java.util.List;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-8-16</br>
 * Time: 下午1:47</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :  取得Company的树节点                                                                    </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-16    joker pan    created
 * <pre/>
 */


public class CompanyNode  extends AbstractEntityNode<SystemCompany> {


    public CompanyNode(IPagedDao<SystemCompany, Long> dao) {
        super(dao);
    }


    @Override
    public String getText(SystemCompany node) {

        return node.getCompanyName();
    }

    /**
     * call netive sql
     * @param parentId 0-root
     * @return
     */
    @Override
    public List<SystemCompany> getRootNodes(String parentId) {

//        return getDao().findBy("parentId",parentId);

        String hql = "select {c.*} from sys_company as c where c.parent_id=:p or (c.parent_id is null) ";
//        SQLQuery query = getDao().createSQLQuery(hql);
//        query.addEntity("c", SystemCompany.class);  //必须为add
//        query.setParameter("p", Long.parseLong(parentId));
//
//        return (List<SystemCompany>) query.list();
        return execParentSql(hql,parentId);
    }

    @Override
    public List<SystemCompany> getSubNodes(SystemCompany node) {

        String hql = "select {c.*} from sys_company as c where c.parent_id=:p";
//        SQLQuery query = getDao().createSQLQuery(hql);
//        query.addEntity("c", SystemCompany.class);  //必须为add
//        query.setParameter("p", node.getId());
//
//        List<SystemCompany> txns = getDao().findBy("parentId.id", node.getId());
        return execParentSql(hql,node.getId().toString());
    }

    @Override
    public String getId(SystemCompany node) {
        return node.getId().toString();
    }

    @Override
    public SystemCompany getParentId(SystemCompany id) {

        return id.getParentId();
    }

    @Override
    public Class getEntityClass() {
        return SystemCompany.class;
    }

}
