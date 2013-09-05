package com.redsun.platf.web.mail;

import com.redsun.platf.entity.account.UserAccount;
import com.redsun.platf.web.framework.StandardController;
import org.apache.commons.lang.SerializationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * <p>Title: com.walsin.platf.web.framework.mail.MailGeneratorController</p>
 * <p>Description: 產生Mail Body</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public class MailGeneratorController extends StandardController {
    
    private String path;
    
    public String main(HttpServletRequest request, HttpServletResponse response, UserAccount userAccount, Map<String, Object> model) throws Exception{
        try{
            Object paramObj = SerializationUtils.deserialize(request.getInputStream());        
            
            if (!(paramObj instanceof MailGeneratorParam)) {
              logger.error("Wrong parameter type: "+paramObj.getClass());
            }else {
                MailGeneratorParam param = (MailGeneratorParam)paramObj;
                String templateName = getPath() + File.separator + param.getTemplateName();
                logger.  info("Generate mail from template "+templateName);
                
                Map<String, Object> paramModel = param.getModel();
                if(paramModel != null) model.putAll(paramModel);        
                return templateName;
            }
        } catch(Exception ex){
            logger. error("Exception when generating mail body",ex);
        }
        return null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
