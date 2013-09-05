package com.redsun.platf.web.validator;

import com.redsun.platf.entity.account.UserAccount;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-7-10</br>
 * Time: 下午4:21</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name : spring validator 的用戶驗證                                                                     </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-7-10    joker pan    created
 * <pre/>
 *
 *
 * com.redsun.platf.security.service.web.UserController
 *
 * 或 hibernateValidator
 *    @Size (min=3, max=20, message="用户名长度只能在3-20之间")
 *    private String userName;
 *
 *    @Size (min=6, max=20, message="密码长度只能在6-20之间")
 *    private String password;
 *
 *    @Pattern (regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message="邮件格式错误")
 *    private String email;
 *
 */


public class UserAccountValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserAccount.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "userName", "user.userName.required", "用户名不能为空");
        ValidationUtils.rejectIfEmpty(errors, "password", "user.password.required", "密码不能为空");
        ValidationUtils.rejectIfEmpty(errors, "email", "user.email.required", "邮箱不能为空");
        UserAccount user = (UserAccount) target;
        int length = user.getLoginName().length();
        if (length > 20) {
            errors.rejectValue("userName", "user.userName.too_long", "用户名不能超过{20}个字符");
        }
        length = user.getPassword().length();
        if (length < 6) {
            errors.rejectValue("password", "user.password.too_short", "密码太短，不能少于{6}个字符");
        } else if (length > 20) {
            errors.rejectValue("password", "user.password.too_long", "密码太长，不能长于{20}个字符");
        }
        int index = user.getEmail().indexOf("@");
        if (index == -1) {
            errors.rejectValue("email", "user.email.invalid_email", "邮箱格式错误");
        }
    }

}
