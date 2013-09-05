package com.redsun.platf.dao.base;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: joker pan
 * Date: 13-6-4
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 * <pre>
 * -----------------------------------------------------------------------------
 * Program ID   : com.redsun.platf.dao.base
 * Program Name :
 * ----------------------------------------------------------------------------- <pre/>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------
 * 1.0     13-6-4    joker pan       created
 *
 * </pre>
 */
public class DBTable  implements Serializable {

	public String tableName;
	public String entityName;
	public String tableTitle;
	public Class class1;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getTableTitle() {
		return tableTitle;
	}
	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
	public Class getClass1() {
		return class1;
	}
	public void setClass1(Class class1) {
		this.class1 = class1;
	}
}