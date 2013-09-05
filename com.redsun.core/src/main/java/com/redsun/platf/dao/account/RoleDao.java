package com.redsun.platf.dao.account;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.account.AccountRole;
import com.redsun.platf.entity.account.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色对象的泛型DAO.
 * 
 * @author calvin
 */
@Repository
public class RoleDao extends PagedDao<AccountRole, Long> {

	private static final String QUERY_USER_BY_ROLEID = "select u from UserAccount u left join u.roleList r where r.id=?";

	
	public RoleDao() {
		super();
		this.entityClass=AccountRole.class;
	}


	/**
	 * 重载函数,因为Role中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) {
		AccountRole role = get(id);
		//查询出拥有该角色的用户,并删除该用户的角色.
		List<UserAccount> users = createQuery(QUERY_USER_BY_ROLEID, role.getId()).list();
		for (UserAccount u : users) {
			u.getRoleList().remove(role);
		}
		super.delete(role);

    }

}
