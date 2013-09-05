package com.redsun.platf.dao.interceptor;

import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.entity.account.UserAccount;
import com.redsun.platf.util.LogUtils;
import com.redsun.platf.web.framework.RequestThreadResourceManager;
import org.apache.commons.lang.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * <p>Title: com.walsin.platf.dao.hibernate.support.StandardWhoColumnIntercepter</p>
 * <p>Description: Hibernate Entity 攔截器 for preset StandardWhoColumn</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 *
 * @author Jason Huang
 * @version 1.0
 */
public class StandardWhoColumnInterceptor extends EmptyInterceptor {

    protected Logger logger = LogUtils.getLogger(this.getClass());

    private static final long serialVersionUID = 7987230107543037978L;

    private static final String LAST_CREATED_BY_COLUMN = "createdBy";

    private static final String LAST_CREATED_DATE_COLUMN = "creationDate";

    private static final String LAST_UPDATED_BY_COLUMN = "lastUpdatedBy";

    private static final String LAST_UPDATED_DATE_COLUMN = "lastUpdateDate";

    /**
     * 系統預設帳號
     */
    private static final String EP_SYSTEM_USER = "SYSTEM";

    /**
     * 自動寫入『修改者、更新時間』
     *
     * @param entity
     * @param id
     * @param currentState
     * @param previousState
     * @param propertyNames
     * @param types
     * @return 在login success 後，RequestThreadResourceManager.setResource(UserAccount.class,userAccount);
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id,
                                Object[] currentState, Object[] previousState,
                                String[] propertyNames, Type[] types) {
        boolean modified = false;

        if (entity instanceof BaseEntity) {
            logger.info("entity flushDirty:" + entity);
            UserAccount User = (UserAccount) RequestThreadResourceManager.getResource(UserAccount.class);
            String userId = (User == null ? EP_SYSTEM_USER : User.getAccountId());
            for (int i = 0, size = propertyNames.length; i < size; i++) {
                String propertyName = propertyNames[i];
                if (StringUtils.equalsIgnoreCase(LAST_UPDATED_BY_COLUMN, propertyName)) {
                    modified = true;
                    if (currentState[i] == null) currentState[i] = userId;
                }
                if (StringUtils.equalsIgnoreCase(LAST_UPDATED_DATE_COLUMN, propertyName)) {
                    modified = true;
                    currentState[i] = new Timestamp(Calendar.getInstance().getTime().getTime())/*Calendar.getInstance().getTime())*/;
                }
                //保存原來的建立者、建立日期
//                if (StringUtils.equalsIgnoreCase(LAST_CREATED_BY_COLUMN, propertyName)) {
//                    modified = true;
//                    if (currentState[i] == null)
//                        logger.info("修改時，建立者被清空了") ;
//                        currentState[i] = userId;
//                }
//
//                if (StringUtils.equalsIgnoreCase(LAST_CREATED_DATE_COLUMN, propertyName)) {
//                    modified = true;
//                    if (currentState[i] == null)
//                        currentState[i] = new Timestamp(Calendar.getInstance().getTime().getTime());
//                }

            }
        }
        return modified;
    }

    /**
     * 新增時保存 「建立者、建立時間」
     *
     * @param entity
     * @param id
     * @param currentState
     * @param propertyNames
     * @param types
     * @return
     */
    @Override
    public boolean onSave(Object entity, Serializable id,
                          Object[] currentState,
                          String[] propertyNames, Type[] types) {
        boolean modified = false;
        if (entity instanceof BaseEntity) {
            logger.info("entity onSave:" + entity);
            UserAccount user = (UserAccount) RequestThreadResourceManager.getResource(UserAccount.class);
            String userId = (user == null ? EP_SYSTEM_USER : user.getAccountId());
            for (int i = 0, size = propertyNames.length; i < size; i++) {
                String propertyName = propertyNames[i];
                if (StringUtils.equalsIgnoreCase(LAST_CREATED_BY_COLUMN, propertyName)) {
                    modified = true;
                    if (currentState[i] == null) currentState[i] = userId;
                }

                if (StringUtils.equalsIgnoreCase(LAST_CREATED_DATE_COLUMN, propertyName)) {
                    modified = true;
                    currentState[i] = new Timestamp(Calendar.getInstance().getTime().getTime());
                }
//                新增時，修改者應為空
              /*  if(StringUtils.equalsIgnoreCase(LAST_UPDATED_BY_COLUMN, propertyName)) {
                    modified = true;
                    if(currentState[i] == null) currentState[i] = userId;
                }
                if(StringUtils.equalsIgnoreCase(LAST_UPDATED_DATE_COLUMN, propertyName)) {
                    modified = true;
                    currentState[i] =  new Timestamp(Calendar.getInstance().getTime().getTime());
                }*/
            }
        }
        return modified;
    }

//    @Override
//    public Object getEntity(String entityName, Serializable id) {
//        logger.info("getEntity:" + id);
//        return super.getEntity(entityName, id);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
//        if (entity instanceof BaseEntity) {
//            logger.info("onLoad:" + entity);
//        }
//        return super.onLoad(entity, id, state, propertyNames, types);    //To change body of overridden methods use File | Settings | File Templates.
//    }
}
