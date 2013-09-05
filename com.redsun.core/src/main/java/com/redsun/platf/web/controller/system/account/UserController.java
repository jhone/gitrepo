package com.redsun.platf.web.controller.system.account;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.account.AccountRole;
import com.redsun.platf.entity.account.UserAccount;
import com.redsun.platf.entity.sys.Department;
import com.redsun.platf.service.account.AccountManager;
import com.redsun.platf.util.PasswordUtil;
import com.redsun.platf.util.string.StringUtils;
import com.redsun.platf.web.controller.AbstractStandardController;
import com.redsun.platf.web.validator.UserAccountValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 增加spring valdator 支持
 *
 * @see com.redsun.platf.web.validator.UserAccountValidator
 */


@Controller
@RequestMapping("/system/account")
@Transactional

public class UserController
        extends AbstractStandardController<UserAccount> {

    Log logger = LogFactory.getLog(this.getClass());

    @Resource
    AccountManager userManager;

    @Override
    public String getUrl() {
        return "system/account";
    }

    @Override
    public IPagedDao<UserAccount, Long> getDao() {
        return userManager.getUserDao();  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    protected void prepareModel() throws Exception {

    }


    //    @InitBinder
//        public void initBinder(WebDataBinder binder){
    @Override
    protected void initValidator(WebDataBinder binder, HttpServletRequest request) {
        binder.setValidator(new UserAccountValidator());
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {
        System.out.println(model);
        model.addAttribute("aa", "111");
//        System.out.println(model);
//        System.out.println(userManager.getUserDao());
        System.out.println("user getDao");
        System.out.println(getDao());

        List<?> l = userManager.getUserDao().getAll();
        System.out.println(l);


//		model.addAttribute("userList", userManager.getUserDao().getAll());
        return "account/list-user";
    }


    /**
     * 查询所有
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView queryAll() throws
            Exception {


        Map<String, Object> map = new HashMap<String, Object>();

//        List<UserAccount> results = userManager.getUserDao().getAll();
        List<UserAccount> results = getDao().getAll();
//        System.out.println("結果：");
//        System.out.println(results.size());

        for (UserAccount u : results) {

            System.out.println("name:" + u.getLoginName());
            List<AccountRole> rs = u.getRoleList();
            for (Iterator<AccountRole> iterator = rs.iterator(); iterator.hasNext(); ) {
                AccountRole r = (AccountRole) iterator.next();
                System.out.println(r);
            }

        }
        map.put("results", results);

//            String json = mapper.writeValueAsString(results);
        ModelAndView mav = new ModelAndView();

        mav.addObject("success", map);

        return mav;
    }


//    /**
//     * 將部門顯示給用戶選擇,顯示增加畫面
//     *
//     * @return
//     * @throws Exception
//     * @ModelAttribute Map<String, List<Department>> listDept,
//     * ModelMap model, BindingResult result
//     */
//    @RequestMapping(value = "/addUI", method = RequestMethod.GET)
////   直接放在視圖裡，不用json @ResponseBody
//    public ModelAndView addUI() throws Exception {
//        // spring会利用jackson自动将返回值封装成JSON对象
//
//        List<Department> deptList = userManager.getDepartmentDao().getAll();
//        logger.info("current dept:" + deptList);
//
//        Map<String, List<Department>> model = new HashMap<String, List<Department>>();
//
//        model.put("listDept", deptList);
//
//        return new ModelAndView("account/add", model);
//    }

    private void reigster(HttpServletRequest request, HttpServletResponse response,
                          @Validated UserAccount entity, BindingResult result) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<String, String>();

        if (StringUtils.isEmptyOrNull(entity.getLoginName())) {
            errors.put("userName", "用户名不能为空!");
        } else if (getDao().findBy("loginName", entity.getLoginName()).size() > 0) {
            errors.put("userName", "该用户已注册!");
        }

//           if (password == null || "".equals(password)) {
//               errors.put("password","密码不能为空!");
//           } else if (password != null && password.length() < 3) {
//               errors.put("password","密码长度不能低于3位!");
//           }
//
//           if (password2 == null || "".equals(password2)) {
//               errors.put("password2", "确认密码不能为空!");
//           } else if (password2 != null && !password2.equals(password)) {
//               errors.put("password2", "两次输入的密码不一致!");
//           }
//
//           if (email == null || "".equals(email)) {
//               errors.put("email", "email不能为空!");
//
//           } else if (email != null && !email.matches("[0-9a-zA-Z_-]+@[0-9a-zA-Z_-]+\\.[0-9a-zA-Z_-]+(\\.[0-9a-zA-Z_-])*")) {
//               errors.put("email", "email格式不正确!");
//           }
//
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/registerUI").forward(request, response);
            return;
        }

//           User user = new User();
//           user.setUserName(userName);
//           user.setPassword(password);
//           user.setEmail(email);
//           user.setActivated(false);

//           userDao.addUser(user);

        getDao().save(entity);
        // 注册成功后,发送帐户激活链接
        EmailUtils.sendAccountActivateEmail(entity);

        // 注册成功直接将当前用户保存到session中
        request.getSession().setAttribute("user", entity);
        request.getRequestDispatcher("/WEB-INF/pages/registerSuccess.jsp").forward(request, response);

    }

    public void activeUser(HttpServletRequest request, HttpServletResponse response) {
        String idValue = request.getParameter("id");
        int id = -1;
        try {
            id = Integer.parseInt(idValue);
        } catch (NumberFormatException e) {
            throw new RuntimeException("无效的用户！");
        }

        UserAccount user = getDao().findUniqueBy("id", id);

//              UserDao userDao = UserDaoImpl.getInstance();
//              User user = userDao.findUserById(id);// 得到要激活的帐户
//              user.setActivated(GenerateLinkUtils.verifyCheckcode(user, request));// 校验验证码是否和注册时发送的一致，以此设置是否激活该帐户
//              userDao.updateUser(user);
        getDao().update(user);

        request.getSession().setAttribute("user", user);

//              request.getRequestDispatcher("/accountActivateUI").forward(request, response);
    }


    @Override
    protected boolean beforeSave() {
        /*將password 進行encode */
        if (!this.isUpdate()) {
            String oriPassword = getModel().getPassword();
            getModel().setPassword(encodePassword(oriPassword));
            System.out.println(oriPassword + "->" + getModel().getPassword());
            return true;
        } else {
            return false;
        }

    }

    //蜜碼加密處理
    public String encodePassword(String password) {

        return PasswordUtil.enCoderSHA(password);
    }


}
