package com.redsun.platf.entity.tag;

import com.redsun.platf.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;

/**
 * <p>Title: com.walsin.platf.orm.entity.Attachment</p>
 * <p>Description: EP系統附件紀錄主檔</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 *
 * @author Jason Huang
 * @version 1.0
 */
@Entity
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class Attachment extends BaseEntity {

    private static final long serialVersionUID = 4381036044797465347L;

//    /** PK */
//    private Integer id;

    /**
     * 文件類別
     */
    private Integer documentType;

    /**
     * Attachment 來源，1=表頭,2=表身, 3=其他
     */
    private Integer sourceType;

    /**
     * 來源ID
     */
    private String sourceId;

    /**
     * 原來附加檔案名稱
     */
    private String originalFileName;

    /**
     * 目的地附加檔案名稱
     */
    private String targetFileName;

    /**
     * 附加檔案說明
     */
    private String description;


    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
