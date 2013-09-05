package com.redsun.platf.web;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: cs07151
 * Date: 2012/8/15
 * Time: 下午 4:21
 *
 * 防止找不到DispatcherServlet context filter
 *
 */
public class BindSpringContextFilter implements Filter {


    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE) == null) {
            ServletContext servletContext = filterConfig.getServletContext();
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
        }

        filterChain.doFilter(request, response);
    }

    public void destroy() {
        this.filterConfig = null;
    }

}
