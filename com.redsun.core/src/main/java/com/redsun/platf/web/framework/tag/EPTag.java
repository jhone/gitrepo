package com.redsun.platf.web.framework.tag;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspTagException;
import java.io.IOException;

/**
 * <p>Title: com.redsun.platf.web.framework.tag.EPTag</p>
 * <p>Description: EP標籤抽象父類別</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 *
 * @author Jason Huang
 * @version 1.0
 */
public abstract class EPTag extends RequestContextAwareTag implements TagModelNames {

    private static final long serialVersionUID = -792604072667882565L;

    /**
     * 寫入頁面
     *
     * @param htmlString 字串
     * @throws JspTagException
     */
    protected void writeHTML(String htmlString) throws JspTagException {
        try {
            pageContext.getOut().write(htmlString);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            throw new JspTagException(ex.getMessage());
        }
    }

    /**
     * 取得訊息
     *
     * @param resolvable
     */
    protected String getMessage(String resolvable) {
        try {
            return StringUtils.isBlank(resolvable) ? StringUtils.EMPTY : getRequestContext().getMessage(resolvable);
        } catch (Exception ex) {
            return resolvable;
        }
    }

    /**
     * 取得Bean
     *
     * @param <T>
     * @param c
     * @return
     */
    protected <T> T getBean(ServletRequest request, Class<T> c) {

        return (T) RequestContextUtils.getWebApplicationContext(request).getBean(c);
    }

    @Override
    public void doFinally() {
        super.doFinally();
    }

}
