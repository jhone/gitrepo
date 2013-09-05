package com.redsun.platf.dao.sys;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.sys.SystemCompany;
import com.redsun.platf.entity.tag.Attachment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户对象的泛型DAO类.
 *
 * @author calvin
 */
@Repository
@Transactional
public class SystemCompanyDao extends PagedDao<SystemCompany, Long> {

    public SystemCompanyDao() {
        super();
        this.entityClass = SystemCompany.class;
    }

    public SystemCompany findByCompanyCode(String value) {
        return findById(value);

    }

    public List<SystemCompany> loadAll() {
        return find("from SystemCompany");
    }

    //?? 附件圖檔
    public List<Attachment> findbySourceId(String stdNum) {
        List<Attachment> attachments = new ArrayList<>();
        return attachments;

    }

//    /**
//     * 选取当前父类，不能是parent_id=id, or id =id
//     *
//     * @param id
//     * @return
//     */
////    @SuppressWarnings("unchecked")
//    public List<SystemCompany> selectCompanyParent(String id) {
//        Criteria criteria = getSession().createCriteria(SystemCompany.class);
//
//        SimpleExpression exp1 = Restrictions.eq("parentId.id", Long.parseLong(id));
//        SimpleExpression exp2 = Restrictions.eq("parentId.id", null);
//
//        return criteria.add(Restrictions.not(Restrictions.or(exp1,exp2))).list();
//    }

}
