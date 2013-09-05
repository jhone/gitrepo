package com.redsun.platf.util.treenode.entitynode;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.util.treenode.ITreeNodeEntity;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-8-21</br>
 * Time: 下午2:13</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-21    joker pan    created
 * <pre/>
 */
@SuppressWarnings("unchecked")
public abstract class AbstractEntityNode<T extends BaseEntity>
        implements ITreeNodeEntity<T> {

    public AbstractEntityNode(IPagedDao<T, Long> dao) {
        this.dao = dao;
    }

    IPagedDao<T, Long> dao;

    public IPagedDao<T, Long> getDao() {
        return dao;
    }



    /**
     * execute  sql for all nodes  by parent id
     *
     * @param sql
     * @param parentId
     * @return
     */
    protected List<T> execParentSql(String sql, String parentId) {
        SQLQuery query = getDao().createSQLQuery(sql);
        query.addEntity("c", getEntityClass());  //必须为add
        query.setParameter("p", Long.parseLong(parentId));

        return (List<T>) query.list();
    }
}
