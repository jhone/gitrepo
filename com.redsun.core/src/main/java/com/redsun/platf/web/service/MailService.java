package com.redsun.platf.web.service;

import com.redsun.platf.exception.MailException;

import java.util.Map;

/**
 * <p>Title: com.walsin.platf.service.MailService</p>
 * <p>Description: Mail服務介面</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public interface MailService {
    
    /**
     * 發送Mail
     * @param mailEven Mail模組
     * @param addr 地址(多個地址請使用;分隔)
     * @param subject 標題
     * @param model Mail資料模組
     * @throws MailException Mail例外
     */
    public void sendMail(MailEvenEnum mailEven, String addr,
                         String subject, Map<String, Object> model) throws MailException;
    

    /**
     * 發送Mail(含附加檔)
     * @param mailEven Mail模組
     * @param addr 地址(多個地址請使用;分隔)
     * @param subject 標題
     * @param model Mail資料模組
     * @param attachmentName 附加檔案名稱
     * @param attachmentPath 附加檔案路徑
     * @throws MailException Mail例外
     */
    public void sendMail(MailEvenEnum mailEven, String addr, String subject, Map<String, Object> model, String attachmentName, String attachmentPath) throws MailException;
    

    /**
     * 背景專用-發送Mail
     * @param mailEven Mail模組
     * @param addr 地址(多個地址請使用;分隔)
     * @param subject 標題
     * @param model Mail資料模組
     * @throws MailException Mail例外
     */
    public void sendBatchMail(MailEvenEnum mailEven, String addr, String subject, Map<String, Object> model) throws MailException;
    

    /**
     * 背景專用-發送Mail(含附加檔)
     * @param mailEven Mail模組
     * @param addr 地址(多個地址請使用;分隔)
     * @param subject 標題
     * @param model Mail資料模組
     * @param attachmentName 附加檔案名稱
     * @param attachmentPath 附加檔案路徑
     * @throws MailException Mail例外
     */
    public void sendBatchMail(MailEvenEnum mailEven, String addr, String subject, Map<String, Object> model, String attachmentName, String attachmentPath) throws MailException;
    
}
