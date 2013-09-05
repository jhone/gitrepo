package com.redsun.platf.service.account;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.account.AccountRole;
import com.redsun.platf.entity.account.Authority;
import com.redsun.platf.entity.account.UserAccount;
import com.redsun.platf.entity.sys.Department;
import com.redsun.platf.exception.ServiceException;
import com.redsun.platf.util.search.MorePropertyFilter;
import com.redsun.platf.util.sideutil.PagedResult;
import com.redsun.platf.util.sideutil.SpringSecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author calvin
 */
//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);



	@Resource//(name="roleDao")
	private IPagedDao<AccountRole, Long>  roleDao;

	@Resource//(name="userDao")
	private IPagedDao<UserAccount, Long> userDao;
	@Resource
	private IPagedDao<Authority, Long>  authorityDao;
    @Resource
	private IPagedDao<Department, Long>  departmentDao;
	
	
	//-- UserAccount Manager --//
	@Transactional(readOnly = true)
	public UserAccount getUser(Long id) {
		return userDao.get(id);
	}

	public void saveUser(UserAccount entity) {
		userDao.save(entity);
		
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户",
                    SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 使用属性过滤条件查询用户.
	 */
	@Transactional(readOnly = true)
	public PagedResult<UserAccount> searchUser(final PagedResult<UserAccount> page, final List<MorePropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}
    /**
	 * 使用属性过滤条件查询授權.
	 */
	@Transactional(readOnly = true)
	public PagedResult<Authority> searchAuthority(final PagedResult<Authority> page, final List<MorePropertyFilter> filters) {
		return authorityDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public UserAccount findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	//-- AccountRole Manager --//
	@Transactional(readOnly = true)
	public AccountRole getRole(Long id) {
		return roleDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<AccountRole> getAllRole() {
		return roleDao.getAll("id", true);
	}

	public void saveRole(AccountRole entity) {
		roleDao.save(entity);
	}

	public void deleteRole(Long id) {
		roleDao.delete(id);
	}

	//-- Authority Manager --//
	//顯示系統所有權限名稱
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}
	public Authority getAuthority(Long id) {
		return authorityDao.get(id);
	}
   //add 
	public void saveAuthority(Authority entity) {
		authorityDao.save(entity);
	}
	//delete
	public void deleteAuthority(Long id) {
		authorityDao.delete(id);
	}
	
	
	

	public IPagedDao<UserAccount, Long> getUserDao() {
		return userDao;
	}

	public void setUserDao(IPagedDao<UserAccount, Long> userDao) {
		this.userDao = userDao;
	}

//	@Autowired
//	public void setUserDao(UserDao userDao) {
//		this.userDao = userDao;
//	}
//
//	@Autowired
//	public void setRoleDao(RoleDao roleDao) {
//		this.roleDao = roleDao;
//	}
//
//	@Autowired
//	public void setAuthorityDao(AuthorityDao authorityDao) {
//		this.authorityDao = authorityDao;
//	}


    public IPagedDao<Department, Long> getDepartmentDao() {
        return departmentDao;
    }

    public IPagedDao<Authority, Long> getAuthorityDao() {
        return authorityDao;
    }

    public IPagedDao<AccountRole, Long> getRoleDao() {
        return roleDao;
    }
}
