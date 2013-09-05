package com.redsun.platf.web.mail;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Title: com.walsin.platf.web.framework.mail.MailGeneratorParam</p>
 * <p>Description: Mail傳遞物件</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public class MailGeneratorParam implements Serializable{
    
    private static final long serialVersionUID = 758289808060196486L;

    private String templateName;
    
    private Map<String, Object> model;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
