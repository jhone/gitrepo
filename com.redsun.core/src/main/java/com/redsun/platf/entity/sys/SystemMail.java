package com.redsun.platf.entity.sys;

import com.redsun.platf.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import java.util.Date;

/**
 * <p>Title: com.walsin.platf.orm.entity.sys.SystemMail</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */

@Entity

//@Table(name = "SYS_MAIL")

//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemMail extends BaseEntity {

    private static final long serialVersionUID = 2464237239289429270L;
    

       
    /** 信件類別 */
    private Integer eventId;
       
    /** 寄送Mail地址 */
    private String mailToAddress;
       
    /** Mail主旨 */
    private String mailSubject;
       
    /** Mail內容 */
    private String mailBody;
       
    /** 發送日期 */
    private Date processDate;
    
    /** 回覆MAIL */
    private String replyTo;

    /** 附加檔案路徑 */
    private String attachmentPath;
    
    /** 附加檔案名稱 */
    private String attachmentName;
    



    public String getMailToAddress() {
        return mailToAddress;
    }

    public void setMailToAddress(String mailToAddress) {
        this.mailToAddress = mailToAddress;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
