package com.redsun.platf.entity.sys;

import com.redsun.platf.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author dick pan 
 * @version 1.0
 * @since   1.0
 * 
 * <p><H3>Change history</H3></p>
 * <p>2011/3/4   : Created </p>
 *  only config in xml file
 *  <p>2011/11/4 : change to entity
 *  
 */

@Entity
// 表名与类名不相同时重新定义表名.
@Table(name = "SYS_LOCALE")
// 默认的缓存策略.READ_ONLY时会提示java.lang.UnsupportedOperationException: Can't write to a readonly object
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class SystemLanguage extends BaseEntity {

    private String language;

    @Column(length = 50,nullable=false,unique = true)
    private String description;

    public SystemLanguage() {
    }

    public SystemLanguage(String laguage, String description) {
	this.language = laguage;
	this.description = description;

    }
    
    @Column(length = 10,nullable=false,unique = true)
    public String getLanguage() {
	return language;
    }

    public void setLanguage(String language) {
	this.language = language;
    }
    

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public String toString() {
	return "Language [description=" + description + ", language="
		+ language + "]";
    }

}
