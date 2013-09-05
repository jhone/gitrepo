package com.redsun.platf.web.framework;

import com.redsun.platf.dao.DataAccessObjectFactory;
import com.redsun.platf.service.account.AccountManager;
import com.redsun.platf.sys.EPApplicationAttributes;
import com.redsun.platf.sys.SystemConfiguration;
import com.redsun.platf.util.LogUtils;
import com.redsun.platf.web.webservice.ServiceFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * Title: com.walsin.platf.web.framework.StandardController
 * </p>
 * <p>
 * Description: 標準控制父類別
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: FreeLance
 * </p>
 *
 * @author Jason Huang
 * @version 1.0
 */

//@SessionAttributes("appConfig")
// 必須在session httprequest 裡getSession().setAttribute("pet",object<? of Person>)
@Component
public class StandardController  /*extends ActionHandleController*/ {

    protected Logger logger = LogUtils.getLogger(this.getClass());

    /**
     * 環境系統參數
     */
    @Resource
    protected SystemConfiguration systemConfiguration;

    /**
     * DAO集合物件
     */
    @Resource
    private DataAccessObjectFactory daoFactory;

    /**
     * Service集合物件
     */
    @Resource
    protected ServiceFactory serviceFactory;

    /**
     * 帳號管理*
     */
    @Resource
    protected AccountManager userManager;

    @Resource
    ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /*
      * init 僅加載一次 set appconfig to context
      */
    @PostConstruct
    public void initConfig() {

//		getServletContext().setAttribute(
//				EPApplicationAttributes.APPLICATION_CONFIG, appConfig);
//
//		System.out.println("............"+appConfig.getThemes().size());
//		log.info("[redsun] init configurator ok!");
//        serviceFactory

//        System.out.println("init serviceFactory");
//        System.out.println(daoFactory);
//        System.out.println(daoFactory.getSystemTxnDao());
    }


    /**
     * 取得Bean物件
     *
     * @param beanName
     * @return
     */
    protected Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    /**
     * 取得語系
     *
     * @param request HttpServletRequest
     * @return 語系
     */
    public Locale getLocale(HttpServletRequest request) {
        return RequestContextUtils.getLocale(request);
    }

    /**
     * 取得訊息
     *  same with jspUtils
     * @param request
     * @param code
     * @return
     */
    public String getMessage(HttpServletRequest request, String code) {
        // param =null;
        return getApplicationContext().getMessage(code, null,
                getLocale(request));
    }

    /**
     * 取得訊息,帶參數
     * @param request
     * @param code
     * @param params 參數
     * @return
     */
    public String getMessage(HttpServletRequest request, String code, Object[] params) {
        // param =null;
        return getApplicationContext().getMessage(code, params,
                getLocale(request));
    }

    /**
     *  取得訊息,如果没取到显示默认讯息
     * @param request
     * @param code
     * @param defaultMessage
     * @return
     */
    public String getMessage(HttpServletRequest request, String code, String defaultMessage) {
        return getApplicationContext().getMessage(code, null, defaultMessage,
                getLocale(request));
    }

    /**
     *  取得訊息,如果没取到显示默认讯息，帶參數
     * @param request
     * @param code
     * @param params  參數
     * @param defaultMessage
     * @return
     */
    public String getMessage(HttpServletRequest request, String code, Object[] params, String defaultMessage) {
        return getApplicationContext().getMessage(code, params,
                defaultMessage,getLocale(request));
    }

    /**
     * 取得多國語系Message
     *
     * @param code 代碼
     * @return Message
     */
    protected String getMessage(String code) {
        try {

            return getApplicationContext()
                    .getMessage(code, null, Locale.TAIWAN);
        } catch (NoSuchMessageException ex) {
            return code;
        }
    }

    /**
     * 取得多國語系Message
     *
     * @param code 代碼
     * @param obj  參數
     * @return Message
     */
    protected String getMessage(String code, Object[] obj) {
        try {
            return getApplicationContext().getMessage(code, obj, Locale.TAIWAN);
        } catch (NoSuchMessageException ex) {
            return code;
        }
    }

    /**
     * 顯示語言訊息
     *
     * @param model
     * @param code
     */
//	protected void putLocale(Map<String, Object> model, String code) {
//		List<Language> ls = ApplicationConfigLoader.getInstance()
//				.getLanguages();
//
//		for (Language language : ls) {
//			if (code.equalsIgnoreCase(language.getLanguage()))
//				model.put(EPApplicationAttributes.LOCALE_NAME, code);
//
//		}
//
//	}

    /**
     * 顯示皮膚訊息
     *
     * @param model
     * @param code
     */
//	protected void putTheme(Map<String, Object> model, String code) {
//		model.put(EPApplicationAttributes.LOCALE_NAME, getMessage(code));
//	}

    /**
     * 顯示錯誤訊息
     *
     * @param model
     * @param code
     */
    protected void putErrorMessage(Map<String, Object> model, String code) {
        model.put(EPApplicationAttributes.ERROR_MESSAGE, getMessage(code));
    }

    /**
     * 顯示警告訊息
     *
     * @param model
     * @param code
     */
    protected void putWarnMessage(Map<String, Object> model, String code) {
        model.put(EPApplicationAttributes.WARN_MESSAGE, getMessage(code));
    }

    /**
     * 顯示提示訊息
     *
     * @param model
     * @param code
     */
    protected void putInfoMessage(Map<String, Object> model, String code) {
        model.put(EPApplicationAttributes.INFO_MESSAGE, getMessage(code));
    }

    /**
     * 顯示錯誤訊息
     *
     * @param model
     * @param code
     * @param values
     */
    protected void putErrorMessage(Map<String, Object> model, String code,
                                   Object[] values) {
        model.put(EPApplicationAttributes.ERROR_MESSAGE,
                getMessage(code, values));
    }

    /**
     * 顯示警告訊息
     *
     * @param model
     * @param code
     * @param values
     */
    protected void putWarnMessage(Map<String, Object> model, String code,
                                  Object[] values) {
        model.put(EPApplicationAttributes.WARN_MESSAGE,
                getMessage(code, values));
    }

    /**
     * 顯示提示訊息
     *
     * @param model
     * @param code
     * @param values
     */
    protected void putInfoMessage(Map<String, Object> model, String code,
                                  Object[] values) {
        model.put(EPApplicationAttributes.INFO_MESSAGE,
                getMessage(code, values));
    }

    /**
     * 顯示提示訊息(包含link)
     *
     * @param model
     * @param code
     */
    protected void putLinkMessage(Map<String, Object> model, String code,
                                  String link) {
        model.put(EPApplicationAttributes.LINK_MESSAGE, getMessage(code) + link);
    }

    /**
     * 顯示提示訊息(包含link)
     *
     * @param model
     * @param code
     * @param values
     */
    protected void putLinkMessage(Map<String, Object> model, String code,
                                  Object[] values, String link) {
        model.put(EPApplicationAttributes.LINK_MESSAGE,
                getMessage(code, values) + link);
    }

    public DataAccessObjectFactory getDaoFactory() {
        return daoFactory;
    }


    public SystemConfiguration getSystemConfiguration() {
        return systemConfiguration;
    }


    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

//

//    @Override
//    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Map<String, Object> model = new HashMap<String, Object>();
// 	    try{
// 	        // 編碼檢核
// 	        // encodeingCheck(request, model);
//
// 	        // 取得Method Name
// 	        String methodName = methodNameResolver.getHandlerMethodName(request);
//
// 	        // 執行Method,返回View Name
// 	        String viewName = invokeNamedMethod(methodName, request, response, model);
//
// 	        // 已於Method回應
// 	        if(viewName == null) return null;
//
// 	        // 取得實際對應之View Name
// 	        String realViewName = handleViewName(viewName);
//
// 	        // 回傳處理結果給 Dispatcher
// 	        return new ModelAndView(realViewName, model);
//
// 	    } catch (NoSuchMethodException nsmex) {
//             throw nsmex;
//         } catch (InvocationTargetException itex) {
//             return handleException(request, response, model, itex.getTargetException());
//         } catch (Exception ex) {
//             return handleException(request, response, model, ex);
//         } finally{
//             doFinally(request);
//         }
//
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }

    /*@Override
    public void doFinally(HttpServletRequest request) {
	}

	@Override
	public void doCatch(Throwable ex) {
		// 系統例外紀錄
		// getServiceFactory().getSystemService().recordException(new
		// SystemException(getClass(), ExceptionType.SYSTEM_EXCEPTION,
		// ExceptionFunction.IMMEDIATE, ex.getMessage(), ex));

	}*/
}
