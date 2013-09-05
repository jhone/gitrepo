package com.redsun.platf.web.framework.tag;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-8-26</br>
 * Time: 上午10:38</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-26    joker pan    created
 * <pre/>
 */
public class ChangeLocaleTag extends EPTag {

    private Map<String, String> localeList = new HashMap<>();

    private String defaultLocale;
    private String name;
    private String onchange;
    private String sessionName ="locale_session";
    private boolean firstEmpty;


    @Override
    protected int doStartTagInternal() throws Exception {


        StringBuffer html = new StringBuffer();

//        <spring:message code="springapp.language"/>:

        String language = getMessage("springapp.language");
        html.append(language).append(":");


        html.append("<select name=").append(HTML_QUOTATION).append(this.name)
                .append(HTML_QUOTATION).append(" id=").append(HTML_QUOTATION).append(this.name)
                                .append(HTML_QUOTATION).append(" class=\"text12_grey\"");
        if (StringUtils.isNotBlank(this.onchange))
            html.append(" onchange=").append(HTML_QUOTATION).append(this.onchange).append(HTML_QUOTATION);
        html.append(">");

        if (firstEmpty) html.append("<option value=\"\"></option>");
        for (String key : localeList.keySet()) {

            //存的是id
            html.append("<option value=").append(HTML_QUOTATION)
                    .append(key).append(HTML_QUOTATION);
            if (StringUtils.equals(key, this.defaultLocale))
                html.append(" selected=").append(HTML_QUOTATION).append("selected").append(HTML_QUOTATION);
            html.append(">");
            //                html.append( entity.getId()).append("--");
            html.append(localeList.get(key)); //显示内容
            html.append("</option>");
        }
        html.append("</select>");


        writeHTML(html.toString());

        return SKIP_BODY;
    }


    private  void  setCookieSession(HttpServletRequest request){
        String cookie_name =getSessionName();
          String locale = "";

        Cookie[] cookies = request.getCookies();
          Boolean found = false;


        HttpSession session = request.getSession();
        for (Cookie c : cookies) {
              if (c.getName().equalsIgnoreCase(cookie_name)) {
                  session.setAttribute(getSessionName(), c.getValue());
                  locale = c.getValue();
      //            out.print("cookie :" + c.getValue());
                  found = true;
                  break;
              }
          }

          //session
      //    session.removeAttribute("locale_session");
          if (!found) {
              String sessionName = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
              session.setAttribute(getSessionName(), session.getAttribute(sessionName));
          } else {
              session.setAttribute(getSessionName(), locale);
          }

    }
//////////////////////////////////////////////////////

    public Map<String, String> getLocaleList() {
        return localeList;
    }

    public void setLocaleList(Map<String, String> localeList) {
        this.localeList = localeList;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public boolean isFirstEmpty() {
        return firstEmpty;
    }

    public void setFirstEmpty(boolean firstEmpty) {
        this.firstEmpty = firstEmpty;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
}
