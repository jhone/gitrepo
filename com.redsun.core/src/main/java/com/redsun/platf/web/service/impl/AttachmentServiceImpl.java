package com.redsun.platf.web.service.impl;

import com.redsun.platf.dao.sys.AttachmentDao;
import com.redsun.platf.entity.tag.Attachable;
import com.redsun.platf.entity.tag.Attachment;
import com.redsun.platf.entity.tag.AttachmentDocument;
import com.redsun.platf.exception.AttachmentServiceException;
import com.redsun.platf.web.service.AttachmentService;
import org.apache.commons.codec.net.BCodec;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Title: com.walsin.platf.service.impl.AttachmentServiceImpl</p>
 * <p>Description: EP系統檔案附件服務實作</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
@Service("attachmentService")
public class AttachmentServiceImpl extends AbstractService implements AttachmentService {

    private static final int ATTACHMENT_MAX_SIZE = 5 * 1000 * 1000;
    
    private static final int SIGNATURE_MAX_SIZE  = 1 * 1000 * 1000;
    
    @Override
    public List<Attachment> viewAttachments(AttachmentDocument document) {
        return null;
    }
    
    @Override
    public String downloadAttachment(HttpServletRequest request, HttpServletResponse response, String absoluteFileName, String suggestFileName) throws AttachmentServiceException{
        return doDownload(request, response, absoluteFileName, suggestFileName);
    }
    
    @Override
    public String downloadAttachment(HttpServletRequest request, HttpServletResponse response, Long id) throws AttachmentServiceException{
        Attachment attachment = getDataAccessObjectFactory().getAttachmentDao().findById(id);
        return doDownload(request, response, getSystemConfiguration().getAttachmentPath() + File.separator + attachment.getTargetFileName(), attachment.getOriginalFileName());
    }

    @Override
    public void deleteAttachment(Long id) throws AttachmentServiceException {
        Attachment attachment = getDataAccessObjectFactory().getAttachmentDao().findById(id);
        File file = new File(getSystemConfiguration().getAttachmentPath() + File.separator + attachment.getTargetFileName());
        if(file.exists()) file.delete();
        getDataAccessObjectFactory().getAttachmentDao().delete(attachment);
    }

    @Override
    public void uploadAttachment(HttpServletRequest request, HttpServletResponse response, Long id, Integer documentType, Integer sourceType, String fileDescription) throws AttachmentServiceException {
        Attachment attachment = new Attachment();
        attachment.setSourceId(id.toString());
        attachment.setSourceType(sourceType);
        attachment.setDocumentType(documentType);
        attachment.setDescription(StringUtils.defaultString(fileDescription));
        attachment.setTargetFileName(getTargetFileName());
        attachment.setOriginalFileName(doUpload(request, response, getSystemConfiguration().getAttachmentPath(), attachment.getTargetFileName(), ATTACHMENT_MAX_SIZE));
        getDataAccessObjectFactory().getAttachmentDao().save(attachment);
    }
    
    @Override
    public void uploadAttachment(HttpServletRequest request, HttpServletResponse response,
                                 Attachable attachable, String fileDescription) throws AttachmentServiceException {
        Attachment attachment = new Attachment();
        attachment.setSourceId(attachable.getId().toString());
        attachment.setSourceType(attachable.getAttachmentDocument().getSourceType());
        attachment.setDocumentType(attachable.getAttachmentDocument().getDocumentType());
        attachment.setDescription(StringUtils.defaultString(fileDescription));
        attachment.setTargetFileName(getTargetFileName());
        attachment.setOriginalFileName(doUpload(request, response, getSystemConfiguration().getAttachmentPath(), attachment.getTargetFileName(), ATTACHMENT_MAX_SIZE));
        getDataAccessObjectFactory().getAttachmentDao().save(attachment);
    }
    
    @Override
    public void uploadSignature(HttpServletRequest request, HttpServletResponse response, String id, AttachmentDocument attachmentDocument, String fileDescription) throws AttachmentServiceException {
        String oldFilePath = null;

//        Attachment attachment = getDataAccessObjectFactory().getAttachmentDao().findSignature(id);

        Attachment attachment = (Attachment) getDataAccessObjectFactory().getAttachmentDao().findById(id);
        if(attachment == null) {
            attachment = new Attachment();
        } else {
            oldFilePath = attachment.getTargetFileName();
        }
        
        // 上傳簽名檔
        String targetFileName = getTargetFileName();
        String originalFileName = doUploadSignature(request, response, getSystemConfiguration().getSignaturePath(), targetFileName, SIGNATURE_MAX_SIZE);
        
        attachment.setSourceId(id);
        attachment.setSourceType(attachmentDocument.getSourceType());
        attachment.setDocumentType(attachmentDocument.getDocumentType());
        attachment.setDescription(fileDescription);
        attachment.setTargetFileName(targetFileName);
        attachment.setOriginalFileName(originalFileName);
        getDataAccessObjectFactory().getAttachmentDao().saveOrUpdate(attachment);
        
        // 刪除舊有的簽名檔
        if(StringUtils.isNotBlank(oldFilePath)) {
            File oldFile = new File(getSystemConfiguration().getSignaturePath() + File.separator + oldFilePath);
            if(oldFile.exists()) oldFile.delete();
        }
    }
    
    @Override
    public String downloadSignature(HttpServletRequest request, HttpServletResponse response, Long id) throws AttachmentServiceException {
        Attachment attachment = getDataAccessObjectFactory().getAttachmentDao().findById(id);
        return doDownload(request, response, getSystemConfiguration().getSignaturePath() + File.separator + attachment.getTargetFileName(), attachment.getOriginalFileName());
    }
    

    /**
     * 檔案上傳
     * @param request
     * @param response
     * @throws AttachmentServiceException
     */
    private String doUploadSignature(HttpServletRequest request, HttpServletResponse response, String path, String targetFileName, int maxSize) throws AttachmentServiceException{
        InputStream is = null;
        OutputStream os = null;
        String fileName = null;
        Calendar today = Calendar.getInstance();
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            for (Iterator<String> i = multipartRequest.getFileNames(); i.hasNext();) {
                CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(i.next());
                fileName = file.getOriginalFilename();
                
                BufferedImage image = ImageIO.read(file.getInputStream());
                
                /**
                if(image.getWidth() > 200 || image.getHeight() > 40) {
                    throw new AttachmentServiceException("圖片大小限制(寬200px,長40px)");
                }
                */
                
                if(file.getBytes().length > maxSize) {
                    throw new AttachmentServiceException("檔案大小限制(" + (maxSize/1000/1000) + "MB)");
                }
               
                String targetPath = today.get(Calendar.YEAR) + File.separator + (today.get(Calendar.MONDAY) + 1);
                File targetDir = new File(path + File.separator + targetPath);
                if(!targetDir.exists()) targetDir.mkdirs();
                
                File imgeFile = new File(path + File.separator + targetFileName);
                ImageIO.write(image, "JPG", imgeFile);
           }
          
        } catch(Exception ex){
            throw new AttachmentServiceException(ex.getMessage(), ex);
        } finally{
            try{
                if(is != null) is.close();
                if(os != null) os.close();
            } catch(IOException ex){
                error(ex.getMessage() ,ex);
                throw new AttachmentServiceException(ex);
            }
        }
        return fileName;
    }
    
    /**
     * 檔案上傳
     * @param request
     * @param response
     * @throws AttachmentServiceException
     */
    private String doUpload(HttpServletRequest request, HttpServletResponse response, String path, String targetFileName, int maxSize) throws AttachmentServiceException{
        InputStream is = null;
        OutputStream os = null;
        String fileName = null;
        Calendar today = Calendar.getInstance();
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            for (Iterator<String> i = multipartRequest.getFileNames(); i.hasNext();) {
                CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(i.next());
                fileName = file.getOriginalFilename();
                
                if(file.getBytes().length > maxSize) {
                    throw new AttachmentServiceException("檔案大小限制:" + (maxSize/1000/1000) + "MB");
                }
               
                // 檔案目錄(yyyy/mm)
                String targetPath = today.get(Calendar.YEAR) + File.separator + (today.get(Calendar.MONDAY) + 1);
                File targetDir = new File(path + File.separator + targetPath);
                if(!targetDir.exists()) targetDir.mkdirs();
               
                os = new DataOutputStream(new FileOutputStream(new File(path + File.separator + targetFileName)));
                is = file.getInputStream();
               
                int len;
                int sum = 0;
                byte[] buffer = new byte[8192];
                while ((len = is.read(buffer)) != -1) {
                   os.write(buffer, 0, len);
                   sum += len;
                } 
           }
          
        } catch(Exception ex){
            throw new AttachmentServiceException(ex.getMessage(), ex);
        } finally{
            try{
                if(is != null) is.close();
                if(os != null) os.close();
            } catch(IOException ex){
                error(ex.getMessage() ,ex);
                throw new AttachmentServiceException(ex);
            }
        }
        return fileName;
    }
    
    /**
     * 檔案下載
     * @param request HttpServletRequest物件
     * @param response HttpServletResponse物件
     * @param absoluteFileName 檔案路徑
     * @param suggestFileName 原始檔案名稱
     * @throws AttachmentServiceException 附件例外事件
     */
    private String doDownload(HttpServletRequest request, HttpServletResponse response, String absoluteFileName, String suggestFileName) throws AttachmentServiceException {
        InputStream in = null;
        OutputStream out = null;
        try {
            // 檢查實際檔案路徑      
            File file = new File(absoluteFileName);
            if (!file.exists()) throw new AttachmentServiceException("file not found : " + absoluteFileName);

            response.setContentType("application/octet-stream");
            if (StringUtils.contains(request.getHeader("User-Agent"), "MSIE")) {
                // 為了 IE 做奇怪的設定
                response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(suggestFileName, "utf-8") + "\"");
            } else {
                // 其它瀏覽器遵照 HTTP 標準
                response.setHeader("Content-Disposition", "attachment; filename=\"" + new BCodec().encode(suggestFileName, "utf-8") + "\"");
            }
            
            // 檔案下載
            in = new FileInputStream(file);
            out = response.getOutputStream();
            int bytesRead = 0;
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];
            while ((bytesRead = in.read(buffer, 0, bufferSize)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } catch(Exception ex){
            error(ex.getMessage() ,ex);
            throw new AttachmentServiceException(ex);
        } finally{
            try{
                if(in != null) in.close();
                if(out != null) out.close();
            } catch(IOException ex){
                error(ex.getMessage() ,ex);
                throw new AttachmentServiceException(ex);
            }
        }
        return null;
    }
    
    /**
     * 檔案名稱
     * @return
     */
    private String getTargetFileName(){
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.YEAR) + File.separator + (today.get(Calendar.MONDAY) + 1) + File.separator + new SimpleDateFormat("yyyyMMddhhmmss").format(today.getTime()) + StringUtils.leftPad(String.valueOf(RandomUtils.nextInt(100)), 3, '0');
    }
    /**
     * 修改文件列表
     */
    public void updateAttachments(Integer soruceId,Integer[] attachmentIds,Integer docmentType,Integer sourceType) throws AttachmentServiceException{
       List<Attachment> attachments=  ((AttachmentDao)getDataAccessObjectFactory().getAttachmentDao()).listAttachments(soruceId, docmentType,sourceType);
      //刪除PoExpense
        for( Attachment attachment : attachments){
            if(ArrayUtils.indexOf(attachmentIds, attachment.getId()) == -1){
                getDataAccessObjectFactory().getAttachmentDao().delete(attachment);
            }
        }
    }
    
}
