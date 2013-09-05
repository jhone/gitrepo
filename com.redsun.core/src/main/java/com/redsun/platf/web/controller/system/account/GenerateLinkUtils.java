package com.redsun.platf.web.controller.system.account;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * UserAccount: joker pan</br>
 * Date: 13-8-30</br>
 * Time: 下午4:58</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-30    joker pan    created
 * <pre/>
 */

import com.redsun.platf.entity.account.UserAccount;

import javax.servlet.ServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 生成帐户激活、重新设置密码的链接
 */
public class GenerateLinkUtils {

    private static final String CHECK_CODE = "checkCode";

    /**
     * 生成帐户激活链接
     */
    public static String generateActivateLink(UserAccount UserAccount) {
        return "http://localhost:8080/AccountActivate/activateAccount?id="
                + UserAccount.getId() + "&" + CHECK_CODE + "=" + generateCheckcode(UserAccount);
    }

    /**
     * 生成重设密码的链接
     */
    public static String generateResetPwdLink(UserAccount UserAccount) {
        return "http://localhost:8080/AccountActivate/resetPasswordUI?UserAccountName="
                + UserAccount.getUserAccountName() + "&" + CHECK_CODE + "=" + generateCheckcode(UserAccount);
    }

    /**
     * 生成验证帐户的MD5校验码
     * @param UserAccount  要激活的帐户
     * @return 将用户名和密码组合后，通过md5加密后的16进制格式的字符串
     */
    public static String generateCheckcode(UserAccount UserAccount) {
        String UserAccountName = UserAccount.getUserAccountName();
        String randomCode = UserAccount.getRandomCode();
        return md5(UserAccountName + ":" + randomCode);
    }

    /**
     * 验证校验码是否和注册时发送的验证码一致
     * @param UserAccount 要激活的帐户
     * @param checkcode 注册时发送的校验码
     * @return 如果一致返回true，否则返回false
     */
    public static boolean verifyCheckcode(UserAccount UserAccount,ServletRequest request) {
        String checkCode = request.getParameter(CHECK_CODE);
        return generateCheckcode(UserAccount).equals(checkCode);
    }

    private static String md5(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
            md.update(string.getBytes());
            byte[] md5Bytes = md.digest();
            return bytes2Hex(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String bytes2Hex(byte[] byteArray)
    {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++)
        {
            if(byteArray[i] >= 0 && byteArray[i] < 16)
            {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
        }
        return strBuf.toString();
    }
}