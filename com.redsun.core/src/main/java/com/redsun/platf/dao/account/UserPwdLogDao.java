package com.redsun.platf.dao.account;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.account.UserPwdLog;
import com.redsun.platf.util.PasswordUtil;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/8/10
 * Time: 下午 3:40
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserPwdLogDao extends PagedDao<UserPwdLog, Long> {

	public UserPwdLogDao() {
		super();
		this.entityClass=UserPwdLog.class ;
    }

    public  String getEnCodeString(String str) {
//        return null;  //To change body of created methods use File | Settings | File Templates.
//        StandardPasswordEncoder s=new StandardPasswordEncoder();
        //
//        String enCoString enCodeString= s.encode(str);
        String  enCodeString= PasswordUtil.enCoderMD5(str,getClass().getName());

        return enCodeString;

    }


}
