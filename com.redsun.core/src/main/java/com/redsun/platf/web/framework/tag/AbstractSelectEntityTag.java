package com.redsun.platf.web.framework.tag;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.IdEntity;
import com.redsun.platf.util.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-8-26</br>
 * Time: 上午9:27</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-26    joker pan    created
 * 1.1     13-8-27    joker pan    增加不可选取parentId =id
 *
 * <pre/>
 */

@SuppressWarnings("unchecked")
public abstract class AbstractSelectEntityTag<T extends IdEntity> extends EPTag {

    private static final long serialVersionUID = 3434288290775614402L;

    private String name;

    private String value;

    private Boolean editable;

    private Boolean firstEmpty;

    private String onchange;

    private String removeId;

    private Boolean auth;


    @Override
    protected int doStartTagInternal() throws Exception {
        StringBuffer html = new StringBuffer();

        if (StringUtils.isEmpty(name) || !getEditable().booleanValue()) {
            if (StringUtils.isNotEmpty(this.value)) {
//                logger.debug("find id");
//                T entity = (T) getDao(pageContext.getRequest()).findBy("id", this.value);
//                html.append(showText(entity));
                html.append(showId(this.getValue()));
            }
        } else {
//            List<T> list = null;
//            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
//            if (ObjectUtils.defaultNull(this.auth, Boolean.FALSE).booleanValue()) {
//                UserAccount userAccount = (UserAccount) request.getSession().getAttribute(WalsinSessionAttributes.EP_USER_INFO);
//                companyList = userAccount.getCompanies();
//            } else {
            logger.debug("list id");
            List<T> list = getDao(pageContext.getRequest()).getAll();
//            }


            html.append("<select name=").append(HTML_QUOTATION).append(this.name).append(HTML_QUOTATION).append(" class=\"text12_grey\"");
            if (StringUtils.isNotBlank(this.onchange))
                html.append(" onchange=").append(HTML_QUOTATION).append(this.onchange).append(HTML_QUOTATION);
            html.append(">");
            if (getFirstEmpty().booleanValue()) html.append("<option value=\"\"></option>");

            List<Long>  parents=null;
            if (StringUtils.isNotBlank(this.removeId))
                parents= forceRecycleParentId( Long.parseLong(this.removeId));

            for (T e : list) {
//
                if (StringUtils.isNotBlank(this.removeId)) {
                        //排除當前id
                    if (  e.getId().equals(Long.parseLong(this.removeId)) )
                        continue;

                    //排除parent_id=當前id

                    int i=  Collections.binarySearch(parents,e.getId());
                    if (i>=0) continue;




                }


                //value是id
                html.append("<option value=").append(HTML_QUOTATION).append(e.getId()).append(HTML_QUOTATION);
                if (StringUtils.equals(e.getId().toString(), this.value))
                    html.append(" selected=").append(HTML_QUOTATION).append("selected").append(HTML_QUOTATION);
                html.append(">");
                //text显示内容
                html.append(showText(e));
                html.append("</option>");
            }
            html.append("</select>");
        }

        writeHTML(html.toString());

        return SKIP_BODY;
    }

    @Override
    public void doFinally() {
        super.doFinally();
        this.name = null;
        this.value = null;
        this.editable = null;
        this.auth = null;
    }


    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getEditable() {
        return ObjectUtils.defaultNull(editable, Boolean.TRUE);
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getFirstEmpty() {
        return ObjectUtils.defaultNull(firstEmpty, Boolean.FALSE);
    }

    public void setFirstEmpty(Boolean firstEmpty) {
        this.firstEmpty = firstEmpty;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getRemoveId() {
        return removeId;
    }

    public void setRemoveId(String removeId) {
        this.removeId = removeId;
    }

    String showId(String value) {
        T entity = (T) getDao(pageContext.getRequest()).findBy("id", value);

        return showText(entity);
    }

    /**
     * 显示内容
     *
     * @param entity
     * @return
     */
    public abstract String showText(T entity);

    /**
     * 如果有parent_id 过滤掉ID防止循环引用
     * id  parent_id
     * 11    null   //can't set parent_id=22 , findBy('parentId',id)
     * 22    11
     * <p/>
     * 11不能再设定parent_id=22，过滤掉22
     *
     * @param parentId
     * @return
     */
    public abstract List<Long> forceRecycleParentId(Long parentId);


    @SuppressWarnings("unchecked")
//    public abstract IPagedDao getDao(ServletRequest request);
    public abstract IPagedDao<T, Long> getDao(ServletRequest request);

}