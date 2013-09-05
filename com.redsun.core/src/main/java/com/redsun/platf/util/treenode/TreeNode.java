package com.redsun.platf.util.treenode;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * tree node
 */
public class TreeNode implements Serializable {

	public static final String NODE_STATE_CLOSED = "closed";
    public static final String NODE_STATE_LEAF = "leaf";
	public static final String NODE_STATE_OPEN = "open";

	private Map<String, Object> attr;
	private Collection<TreeNode> children;
	private Map<String, Object> data;
	private String iconCls;
	private String id;
	private String state = TreeNode.NODE_STATE_CLOSED;
//	private String title;
//	private String type;
    private String text;
	private String type;

	public TreeNode() {
		super();
	}

	public TreeNode(String text) {
		super();
		this.text = text;
	}
	
	public TreeNode(String text, Collection<TreeNode> children) {
		super();
		setText(text);
		setChildren(children);
	}

	public TreeNode(String id, String text) {
		super();
		setId(id);
		setText(text);
	}
	
	public TreeNode(String id, String text, Collection<TreeNode> children) {
		super();
		setId(id);
		setText(text);
		setChildren(children);
	}

	public Map<String, Object> getAttr() {
		return attr;
	}

	public Collection<TreeNode> getChildren() {
		return children;
	}

	public Map<String, Object> getData() {
	    return data;
	}

	/**
	 * Get the Tree Node Title
	 */
//	public String getDatay() {
//		return title;
//	}

	public String geticonCls() {
		return iconCls;
	}

	public String getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public String getText() {
		return text;
	}

	public String getType() {
	    return type;
	}

	public void setAttr(Map<String, Object> attr) {
		this.attr = attr;
	}

	/**
	 * Set the Tree Node Childrens
	 * 
	 * @param children
	 */
	public void setChildren(Collection<TreeNode> children) {
		this.children = children;
	}

	public void setData(Map<String, Object> data) {
	    this.data = data;
	}

	/**
	 * Set the Tree Node Icon
	 * 
	 * @param icon
	 */
	public void setIconCls(String icon) {
		if (this.data == null) {
		    data = new HashMap<String, Object>();
		}

		if (this.data.containsKey("iconCls")) {
			this.data.remove("iconCls");
		}
		this.data.put("iconCls", iconCls);
		this.iconCls = iconCls;
	}

	/**
	 * Set the Tree Node Id
	 * 
	 * @param id icon
	 */
	public void setId(String id) {

		this.id = id;
		if (this.attr == null) {
			attr = new HashMap<String, Object>();
		}

		if (this.attr.containsKey("id")) {
			this.attr.remove("id");
		}
		this.attr.put("id", id);
	}

	/**
	 * Set the Tree Node State open, closed or leaf
	 * 
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Set the Tree Node Title
	 * 
	 * @param text
	 */
	public void setText(String text) {
		if (this.data == null) {
		    data = new HashMap<String, Object>();
		}

		if (this.data.containsKey("text")) {
			this.data.remove("text");
		}
		this.data.put("text", text);
		this.text = text;
	}

	/**
	 * Set the Tree Node Type
	 * 
	 * @param type
	 */
	public void setType(String type) {
	    this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TreeNode [id=").append(id).append(", text=").append(
				text).append(", iconCls=").append(iconCls).append(", state=")
				.append(state).append(", attr=").append(attr).append(
						", children=").append(children).append("]");
		return builder.toString();
	}
	
	
}