package com.redsun.platf.web.service.impl;

import com.redsun.platf.entity.sys.SystemMail;
import com.redsun.platf.exception.MailException;
import com.redsun.platf.web.mail.MailGeneratorParam;
import com.redsun.platf.web.service.MailEvenEnum;
import com.redsun.platf.web.service.MailService;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>Title: com.walsin.platf.service.impl.MailServiceImpl</p>
 * <p>Description: Mail服務實做</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 *
 * @author Jason Huang
 * @version 1.0
 */
@Component("mailService")
public class MailServiceImpl extends AbstractService implements MailService {

    private final String _defaultMehtod = "POST";

    private final String _defaultEncoding = "utf-8";


    @Override
    public void sendMail(MailEvenEnum mailEven, String addr, String subject, Map<String, Object> model) throws MailException {
        sendMail(mailEven, addr, subject, model, null, null);
    }

    @Override
    public void sendMail(MailEvenEnum mailEven, String addr, String subject, Map<String, Object> model, String attachmentName, String attachmentPath) throws MailException {
        try {
            // 產生Mail
            String mailFileName = saveMailBodyAsFile(
                    generateMailBody(mailEven.getTemplet(),
                            model,
                            getSystemConfiguration().getMailGeneratorUrl()));

            // 紀錄Mail
            builedMailRecord(mailEven.getEnenId(),
                    addr, subject, mailFileName,
                    attachmentName, attachmentPath);

        } catch (Exception ex) {
            throw new MailException(ex.getMessage(), ex);
        }
    }

    @Override
    public void sendBatchMail(MailEvenEnum mailEven, String addr, String subject, Map<String, Object> model) throws MailException {
        sendBatchMail(mailEven, addr, subject, model, null, null);
    }

    @Override
    public void sendBatchMail(MailEvenEnum mailEven, String addr, String subject, Map<String, Object> model, String attachmentName, String attachmentPath) throws MailException {
        try {
            // 產生Mail
            String mailFileName = saveMailBodyAsFile(generateMailBody(mailEven.getTemplet(), model, getSystemConfiguration().getBatchMailGeneratorUrl()));

            // 紀錄Mail
            builedMailRecord(mailEven.getEnenId(), addr, subject, mailFileName, attachmentName, attachmentPath);

        } catch (Exception ex) {
            throw new MailException(ex.getMessage(), ex);
        }
    }

    /**
     * 建立Mail記錄檔
     *
     * @param mailId
     * @param addr
     * @param subject
     * @param mailFileName
     */
    private void builedMailRecord(Integer mailId, String addr, String subject, String mailFileName, String attachmentName, String attachmentPath) {
        SystemMail mail = new SystemMail();
        mail.setEventId(mailId);
        mail.setMailSubject(subject);
        mail.setMailToAddress(addr);
        mail.setMailBody(mailFileName);
        mail.setAttachmentName(attachmentName);
        mail.setAttachmentPath(attachmentPath);
        getDataAccessObjectFactory().getSystemMailDao().saveOrUpdate(mail);
    }

    /**
     * 產生Mail Body
     *
     * @param templateName
     * @param model
     * @return
     * @throws MailException
     */
    private byte[] generateMailBody(String templateName, Map<String, Object> model, String mailGeneratorUrl) throws MailException {
        try {
            // URL Connection設定
            URL generatorUrl = new URL(mailGeneratorUrl);
            HttpURLConnection conn = (HttpURLConnection) generatorUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(_defaultMehtod);
            conn.setRequestProperty("Content-type", "application/x-java-serialized-object");

            // 將參數 serialize 中以供存取
            MailGeneratorParam param = new MailGeneratorParam();
            param.setTemplateName(templateName);
            param.setModel(model);
            byte paramBytes[] = SerializationUtils.serialize(param);
            conn.getOutputStream().write(paramBytes);

            // 讀取回應
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), _defaultEncoding));
            String line;
            StringBuffer buf = new StringBuffer();
            while ((line = in.readLine()) != null) buf.append(line + "\r\n");

            // 錯誤訊息
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new MailException("buid error HTTP response " + conn.getResponseCode());

            return buf.toString().getBytes(_defaultEncoding);
        } catch (Exception ex) {
            throw new MailException(ex);
        }
    }

    /**
     * 建立Mail Html檔案
     *
     * @param body
     * @return
     * @throws Exception
     */
    protected String saveMailBodyAsFile(byte[] body) throws Exception {
        String fileName = "";
        FileOutputStream fileOutputStream = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss_SS");
        fileName = "MailBody_" + dateFormat.format(new Date()) + RandomUtils.nextInt(100) + ".html";

        try {
            File outPutPath = new File(getSystemConfiguration().getMailOutputPath());
            if (!outPutPath.exists()) outPutPath.mkdirs();
            fileOutputStream = new FileOutputStream(new File(getSystemConfiguration().getMailOutputPath(), fileName));
            if (null != body) fileOutputStream.write(body);
        } finally {
            if (fileOutputStream != null) fileOutputStream.close();
        }
        return fileName;
    }
}
