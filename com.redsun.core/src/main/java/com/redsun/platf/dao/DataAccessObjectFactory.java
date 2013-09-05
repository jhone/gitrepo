package com.redsun.platf.dao;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.account.AccountResources;
import com.redsun.platf.entity.account.AccountRole;
import com.redsun.platf.entity.account.Authority;
import com.redsun.platf.entity.account.UserAccount;
import com.redsun.platf.entity.sys.*;
import com.redsun.platf.entity.tag.Attachment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * <p>Title        : com.webapp        </p>
 * <p>Description  :   所有DAO的集合                </p>
 * <p>Copyright    : Copyright (c) 2011</p>
 * <p>Company      : FreedomSoft       </p>
 *
 */

/**
 * @author dick pan
 * @version 1.0
 * @since 1.0
 *        <p>
 *        <H3>Change history</H3>
 *        </p>
 *        <p>
 *        2011/1/25 : Created
 *        </p> 2012/06/08 : add user resource ,user department
 *        </p>
 */
@Component("dataAccessObjectFactory")
// @Transactional //need't just in dao
public class DataAccessObjectFactory implements Serializable {

    private static final long serialVersionUID = -5450000925993172262L;
    private static DataAccessObjectFactory instance;

    public static DataAccessObjectFactory getInstance() {
        if (instance == null)
            instance = new DataAccessObjectFactory();
        return instance;
    }


    // authority
    @Resource(name = "systemLanguageDao")
    private IPagedDao<SystemLanguage, Long> systemLanguageDao;

    // authority
    @Resource(name = "systemThemeDao")
    private IPagedDao<SystemTheme, Long> systemThemeDao;
    // private SystemThemeDao systemThemeDao;

    @Resource(name = "systemValueDao")
    private IPagedDao<SystemValue, Long> systemValueDao;

    @Resource(name = "systemTxnDao")
    private IPagedDao<SystemTxn, Long> systemTxnDao;

    @Resource(name = "authorityDao")
    private IPagedDao<Authority, Long> authorityDao;

    // role
    @Resource(name = "roleDao")
    private  IPagedDao<AccountRole, Long> roleDao;
//    private RoleDao roleDao;

    // user
    @Resource(name = "userDao")
    private IPagedDao<UserAccount, Long> userDao;
    // user resources
    // add by pyc.2012.6.8
    @Resource(name = "accountResourcesDao")
    private IPagedDao<AccountResources, Long> accountResourcesDao;

    // department
    @Resource(name = "departmentDao")
    private IPagedDao<Department, Long> departmentDao;

    @Resource(name = "systemCompanyDao")
    private  IPagedDao<SystemCompany, Long>  systemCompanyDao;

   @Resource(name = "systemMailDao")
    private IPagedDao<SystemMail, Long> systemMailDao;

    @Resource(name = "attachmentDao")
    private IPagedDao<Attachment, Long>  attachmentDao;


    public IPagedDao<SystemCompany, Long>   getSystemCompanyDao() {
        return systemCompanyDao;
    }


    public IPagedDao<SystemLanguage, Long> getSystemLanguageDao() {
        return systemLanguageDao;
    }

    public IPagedDao<SystemTheme, Long> getSystemThemeDao() {
        // error // public SystemThemeDao getSystemThemeDao() {
        return systemThemeDao;
    }

    public IPagedDao<SystemValue, Long> getSystemValueDao() {
        return systemValueDao;
    }

    public IPagedDao<Authority, Long> getAuthorityDao() {
        return authorityDao;
    }

    public IPagedDao<AccountRole, Long> getRoleDao() {
        return roleDao;
    }

    public IPagedDao<UserAccount, Long> getUserDao() {
        return userDao;
    }

    public IPagedDao<SystemTxn, Long> getSystemTxnDao() {
        return systemTxnDao;
    }

    public IPagedDao<AccountResources, Long> getAccountResourcesDao() {
        return accountResourcesDao;
    }

    public IPagedDao<Department, Long> getDepartmentDao() {
        return departmentDao;
    }

    public IPagedDao<SystemMail, Long> getSystemMailDao() {
        return systemMailDao;
    }

    public IPagedDao<Attachment, Long>  getAttachmentDao() {
        return attachmentDao;
    }
}
