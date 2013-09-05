package com.redsun.platf.dao.tag.easyui.model.entity;
// default package

import com.redsun.platf.entity.IdEntity;
import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * TType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_s_opintemplate")
public class TSOpinTemplate extends IdEntity implements java.io.Serializable {
	private String descript;
	@Column(name = "descript", length = 100)
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}