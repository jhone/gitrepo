package com.redsun.platf.dao.tag.easyui;

import com.redsun.platf.dao.sys.SystemTxnDao;
import com.redsun.platf.dao.tag.ListtoMenu;
import com.redsun.platf.dao.tag.easyui.model.entity.TSFunction;
import com.redsun.platf.entity.sys.SystemTxn;
import org.hibernate.SQLQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * 类描述：上传标签
 *
 * @version 1.0
 * @author: jeecg
 * @date： 日期：2012-12-7 时间：上午10:17:45
 */

@Transactional
public class MenuTag extends TagSupport {
    protected String style = "easyui";//菜单样式
    protected List<TSFunction> parentFun;//一级菜单
    protected List<TSFunction> childFun;//二级菜单
    protected List<SystemTxn> txn;//菜单

    public void setParentFun(List<TSFunction> parentFun) {
        this.parentFun = parentFun;
    }

    public void setChildFun(List<TSFunction> childFun) {
        this.childFun = childFun;
    }

    public void setTxn(List<SystemTxn> txn) {
        this.txn = txn;
    }


    public int doStartTag() throws JspTagException {
        return EVAL_PAGE;
    }

    public int doEndTag() throws JspTagException {
        try {
            JspWriter out = this.pageContext.getOut();
            out.print(end().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }


    public StringBuffer end() {
        String randid ="nav"+(new Date()).toString();
        StringBuffer sb = new StringBuffer();
        if (style.equals("easyui")) {
            sb.append("<div id='"+randid +"' class=\"easyui-accordion\" fit=\"false\" border=\"false\">");
            StringBuffer menuString = new StringBuffer();
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

            SystemTxnDao dao =getDao(request);
            String s = ListtoMenu.getEasyuiMenu(dao, ListtoMenu.getRootTxn(dao), menuString, 0L);
//            System.out.println("result:");
//            System.out.println(s);
            sb.append(s);
            sb.append("</div>");
        }
        if (style.equals("bootstrap")) {
            sb.append(ListtoMenu.getBootMenu(parentFun, childFun));
        }
        if (style.equals("json")) {
            sb.append("<script type=\"text/javascript\">");
            sb.append("var _menus=" + ListtoMenu.getMenu(parentFun, childFun));
            sb.append("</script>");
        }

        return sb;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    @SuppressWarnings("unchecked")
    private SystemTxnDao getDao(ServletRequest request) {
        return getBean(request, SystemTxnDao.class);
    }



    private List<SystemTxn> getRootTxn(ServletRequest request) {

        String hql;

        hql = "select {c.*} from sys_txn as c where c.parent_id=:p or (c.parent_id is null) ";

        SQLQuery query = getDao(request).createSQLQuery(hql);
        query.addEntity("c", SystemTxn.class);  //必须为add
        query.setParameter("p", 0l);

        return query.list();
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

}
