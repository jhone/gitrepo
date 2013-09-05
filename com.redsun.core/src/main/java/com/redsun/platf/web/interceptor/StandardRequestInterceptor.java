package com.redsun.platf.web.interceptor;

import com.redsun.platf.entity.account.UserAccount;
import com.redsun.platf.entity.sys.SystemLanguage;
import com.redsun.platf.entity.sys.SystemTheme;
import com.redsun.platf.service.account.AccountManager;
import com.redsun.platf.service.sys.ConfigLoaderService;
import com.redsun.platf.sys.EPApplicationAttributes;
import com.redsun.platf.util.LogUtils;
import com.redsun.platf.web.framework.RequestThreadResourceManager;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 標准request 的攔截器 全局配置
 *
 * @author dick pan
 *         //  <mvc:interceptors>
 *         //      <bean
 *         //          class="cn.li.controller.StandardRequestInterceptor" />
 *         //      <bean
 *         //          class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor">
 *         //          <property name="paramName" value="language" />
 *         //       </bean>
 *         //  </mvc:interceptors>
 */
@Component
public class StandardRequestInterceptor implements HandlerInterceptor {

    private Logger log = LogUtils.getLogger(this.getClass());

    /**
     * 帳號管理*
     */
    @Resource
    protected AccountManager userManager;

    @Resource(name = "applicationConfigLoader")
    ConfigLoaderService configLoader;

    private List<SystemTheme> themes;
    private List<SystemLanguage> languages;
//	private List<Authority> allAuthorityList;   //auth list

    public void init() {
//        log.debug("StandardRequestInterceptor inited!");

    }

    public StandardRequestInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if (themes == null)
            themes = configLoader.getThemes();
        if (languages == null)
            languages = configLoader.getLanguages();

        setConfigToRequest(request, EPApplicationAttributes.THEME_NAME, themes);

        setConfigToRequest(request, EPApplicationAttributes.LOCALE_NAME, languages);
//      v201207  store object to requestThreadResources
        RequestThreadResourceManager.setResource(EPApplicationAttributes.THEME_NAME, themes);
        RequestThreadResourceManager.setResource(EPApplicationAttributes.LOCALE_NAME, languages);
        //        WebUtils.setSessionAttribute(request,EPApplicationAttributes.APPLICATION_CONFIG,RequestThreadResourceManager.class);

        log.debug("[webRequestInterceptor] load languages:{},themes:{}" , languages, themes);

        if (!findCurrentUser()) {
            handleNotAuthorized(request, response, handler);//無權403ERROR
            return false;
//            return true;
        } else {
            return true;
        }


    }

    /**
     * save to request session
     * @return  user != null
     */
    private boolean findCurrentUser() {
        /*根據authortication 取得user*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null)     return false;

        String name = ((User) authentication.getPrincipal()).getUsername();
        UserAccount user = userManager.getUserDao().findUniqueBy("loginName", name);

        log.debug("current user {}", user);
        //save to request session
        RequestThreadResourceManager.setResource(UserAccount.class, user);

        return user != null;

    }

    @SuppressWarnings("rawtypes")
    private void setConfigToRequest(HttpServletRequest request,
                                    String attribute, List configValues) {
        List languageConfig = (List) request
                .getSession().getAttribute(attribute);
        if (languageConfig == null) {
            request.getSession().setAttribute(
                    attribute, configValues);
            log.debug("[webRequestInterceptor]config load " + attribute + configValues);
        } else
            log.debug("[webRequestInterceptor]config reload only");


    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.debug("[webRequestInterceptor]postHandle themes{}:", request.getSession().getAttribute(
                EPApplicationAttributes.THEME_NAME));


    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        log.debug("[webRequestInterceptor]afterCompletion themes:" + request.getSession().getAttribute(
//                EPApplicationAttributes.THEME_NAME));

    }

    /**
     * Handle a request that is not authorized according to this interceptor.
     * Default implementation sends HTTP status code 403 ("forbidden").
     * <p>This method can be overridden to write a custom message, forward or
     * redirect to some error page or login page, or throw a ServletException.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @throws javax.servlet.ServletException if there is an internal error
     * @throws java.io.IOException            in case of an I/O error when writing the response
     */
    protected void handleNotAuthorized(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}
