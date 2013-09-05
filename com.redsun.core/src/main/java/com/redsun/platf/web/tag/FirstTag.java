package com.redsun.platf.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/7/24
 * Time: 下午 3:56
 * To change this template use File | Settings | File Templates.
 *
 * javax.servlet.jsp.tagext.
 * package which provide default implementation
 *
 * TagSupport
   BodyTagSupport

 * setPageContext(PageContext pc)
 * setParent(Tag parent)
 * getParent()
 * doStartTag()
 * doEndTag()
 * release()
 *
 */
public class  FirstTag implements Tag, Serializable {

	private PageContext pc = null;
	private Tag parent = null;
	private String name = null;

	public void setPageContext(PageContext p) {
		pc = p;    //save the reference to PageContext object in the private variable
	}

	public void setParent(Tag t) {
		parent = t;
	}

    /**
     * save the reference to parent Tag object in the private variable
     * @return
     */
	public Tag getParent() {
		return parent;
	}

	public void setName(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}

    /**
     * doStartMethod() will be called with the start of the tag
     * @return
     * @throws javax.servlet.jsp.JspException
     */
	public int doStartTag() throws JspException {
		try {

		if(name != null) {
			pc.getOut().write("Hello " + name + "!");
		} else {
			pc.getOut().write("You didn't enter your name");
			pc.getOut().write(", what are you afraid of ?");
		}

		} catch(IOException e) {
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;   // there is no body content in this tag
	}

    /**
     * called when end of the tag is reached.
     * @return
     * @throws javax.servlet.jsp.JspException
     */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;    //rest of the page is read by the socket
	}

    /**
     * This method is called by the JSP page when all
     * the methods of the tag class have been called
     * and it is time to free resources.
     */
	public void release() {
		pc = null;
		parent = null;
		name = null;
	}

}