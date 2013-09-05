package com.redsun.platf.dao.tag.easyui.model.entity;

import com.redsun.platf.entity.IdEntity;

import javax.persistence.*;
import java.util.Date;


/**
 * TLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_s_fileno")
public class TSFileno extends IdEntity implements java.io.Serializable {
	private String filenoBefore;
	private int filenoNum;
	private Date filenoYear;
	private String filenoType;
	
	@Column(name = "filenobefore", length = 32)
	public String getFilenoBefore() {
		return filenoBefore;
	}

	public void setFilenoBefore(String filenoBefore) {
		this.filenoBefore = filenoBefore;
	}
	@Column(name = "filenonum")
	public int getFilenoNum() {
		return filenoNum;
	}

	public void setFilenoNum(int filenoNum) {
		this.filenoNum = filenoNum;
	}
	 
	@Column(name = "filenotype", length = 32)
	public String getFilenoType() {
		return filenoType;
	}

	public void setFilenoType(String filenoType) {
		this.filenoType = filenoType;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "filenoYear", length = 13)
	public Date getFilenoYear() {
		return filenoYear;
	}

	public void setFilenoYear(Date filenoYear) {
		this.filenoYear = filenoYear;
	}

}