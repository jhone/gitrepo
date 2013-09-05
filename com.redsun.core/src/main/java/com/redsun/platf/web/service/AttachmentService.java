package com.redsun.platf.web.service;

import com.redsun.platf.entity.tag.Attachable;
import com.redsun.platf.entity.tag.Attachment;
import com.redsun.platf.entity.tag.AttachmentDocument;
import com.redsun.platf.exception.AttachmentServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>Title: com.walsin.platf.service.AttachmentService</p>
 * <p>Description: EP系統檔案附件服務介面</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public interface AttachmentService {
    
    /**
     * 取得附檔紀錄
     * @param document 文件類別
     * @return 
     */
    public List<Attachment> viewAttachments(AttachmentDocument document);
    
    /**
     * 下載文件
     * @param request HttpServletRequest物件
     * @param response HttpServletResponse物件
     * @param id 檔案ID
     * @return
     * @throws com.redsun.platf.exception.AttachmentServiceException
     */
    public String downloadAttachment(HttpServletRequest request, HttpServletResponse response, Long id) throws AttachmentServiceException;
    
    /**
     * 下載文件
     * @param request HttpServletRequest物件
     * @param response HttpServletResponse物件
     * @param absoluteFileName 檔案路徑名稱
     * @param suggestFileName  原始檔案名稱
     * @return
     * @throws AttachmentServiceException
     */
    public String downloadAttachment(HttpServletRequest request, HttpServletResponse response,
                                     String absoluteFileName, String suggestFileName) throws AttachmentServiceException;
    
    /**
     * 上傳文件
     * @param request HttpServletRequest物件
     * @param response HttpServletResponse物件
     * @param attachable 文件型態物件
     * @param fileDescription 文件說明
     * @throws AttachmentServiceException 
     */
    public void uploadAttachment(HttpServletRequest request, HttpServletResponse response,
                                Attachable attachable, String fileDescription) throws AttachmentServiceException;
    
    /**
     * 上傳文件
     * @param request HttpServletRequest物件
     * @param response HttpServletResponse物件
     * @param id 來源文件ID
     * @param documentType 文件型態
     * @param sourceType 文件類別
     * @param fileDescription 文件說明
     * @throws AttachmentServiceException
     */
    public void uploadAttachment(HttpServletRequest request, HttpServletResponse response,
                                 Long id, Integer documentType,
                                 Integer sourceType, String fileDescription) throws AttachmentServiceException;
    
    /**
     * 刪除文件
     * @param id 文件ID
     * @throws AttachmentServiceException
     */
    public void deleteAttachment(Long id) throws AttachmentServiceException;
    
    /**
     * 上傳簽名檔
     * @param request HttpServletRequest物件
     * @param response HttpServletResponse物件
     * @param id 來源文件ID
     * @param attachmentDocument 文件型態
     * @param fileDescription 文件類別
     */
    public void uploadSignature(HttpServletRequest request, HttpServletResponse response,
                                String id, AttachmentDocument attachmentDocument,
                                String fileDescription) throws AttachmentServiceException;

    /**
     * 下載簽名檔
     * @param request HttpServletRequest物件
     * @param response HttpServletResponse物件
     * @param id 檔案ID
     * @return
     * @throws AttachmentServiceException
     */
    public String downloadSignature(HttpServletRequest request, HttpServletResponse response, Long id) throws AttachmentServiceException;
    
    /**
     * 修改文件列表
     * @param soruceId 來源文件ID
     * @param attachmentIds 附件ID
     * @param docmentType 附件型態
     * @param sourceType 文件型態
     * @throws AttachmentServiceException
     */
    public void updateAttachments(Integer soruceId, Integer[] attachmentIds, Integer docmentType, Integer sourceType) throws AttachmentServiceException;
    
    
}
