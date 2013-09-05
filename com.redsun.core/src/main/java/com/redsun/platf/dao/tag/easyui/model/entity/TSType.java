package com.redsun.platf.dao.tag.easyui.model.entity;
// default package

import com.redsun.platf.entity.IdEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 通用类型字典表
 */
//@Entity
//@Table(name = "t_s_type")
public class TSType extends IdEntity implements java.io.Serializable {

	private TSTypegroup TSTypegroup;//类型分组
	private TSType TSType;//父类型
	private String typename;//类型名称
	private String typecode;//类型编码

    private List<TSType> TSTypes /*=new ArrayList<TSType>()*/;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "typegroupid")
	public TSTypegroup getTSTypegroup() {
		return this.TSTypegroup;
	}

	public void setTSTypegroup(TSTypegroup TSTypegroup) {
		this.TSTypegroup = TSTypegroup;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "typepid")
	public TSType getTSType() {
		return this.TSType;
	}

	public void setTSType(TSType TSType) {
		this.TSType = TSType;
	}

//	@Column(name = "typename", length = 50)
	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Column(name = "typecode", length = 50)
	public String getTypecode() {
		return this.typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	



//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY/*, mappedBy = "TSType"*/)
//    @OneToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.ALL})
//    @JoinColumn(name = "tstypes")

	public List<TSType> getTSTypes() {
		return this.TSTypes;
	}

	public void setTSTypes(List<TSType> TSTypes) {
		this.TSTypes = TSTypes;
	}

}