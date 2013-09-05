package com.redsun.platf.web.service.impl;

import com.redsun.platf.dao.DataAccessObjectFactory;
import com.redsun.platf.sys.SystemConfiguration;
import com.redsun.platf.util.LogUtils;
import com.redsun.platf.web.service.MailService;


/**
 * <p>Title: com.walsin.platf.service.impl.AbstractService</p>
 * <p>Description: Service抽象父類別</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public abstract class AbstractService{
   
    protected org.slf4j.Logger _logger = LogUtils.getLogger(getClass());
    
    public MailService mailService;
    
    public SystemConfiguration systemConfiguration;
    
    public DataAccessObjectFactory dataAccessObjectFactory;

    public SystemConfiguration getSystemConfiguration() {
        return systemConfiguration;
    }

    public void setSystemConfiguration(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    public DataAccessObjectFactory getDataAccessObjectFactory() {
        return dataAccessObjectFactory;
    }

    public void setDataAccessObjectFactory(
                    DataAccessObjectFactory dataAccessObjectFactory) {
        this.dataAccessObjectFactory = dataAccessObjectFactory;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Log 等級為 DEBUG.
     * @param message 要 log 的訊息
     */
    protected void debug(String message) {
        if (_logger.isDebugEnabled()) {
            _logger.debug(message);
        }
    }

    /**
     * Log 等級為 DEBUG.
     * @param message 要 log 的訊息
     * @param cause 錯誤的 exception.
     */
    protected void debug(String message, Throwable cause) {
        if (_logger.isDebugEnabled()) {
            _logger.debug(message, cause);
        }
    }

    /**
     * Log 等級為 INFO.
     * @param message 要 log 的訊息
     */
    protected void info(String message) {
        _logger.info(message);      
    }

    /**
     * Log 等級為 INFO.
     * @param message 要 log 的訊息
     * @param cause 錯誤的 exception.
     */
    protected void info(String message, Throwable cause) {
        _logger.info(message, cause);
    }

    /**
     * Log 等級為 WARN.
     * @param message 要 log 的訊息
     */
    protected void warn(String message) {
        _logger.warn(message);
    }

    /**
     * Log 等級為 WARN.
     * @param message 要 log 的訊息
     * @param cause 錯誤的 exception.
     */
    protected void warn(String message, Throwable cause) {
        _logger.warn(message, cause);
    }

    /**
     * Log 等級為 ERROR.
     * @param message 要 log 的訊息
     */
    protected void error(String message) {
        _logger.error(message);
    }

    /**
     * Log 等級為 ERROR.
     * @param message 要 log 的訊息
     * @param cause 錯誤的 exception.
     */
    protected void error(String message, Throwable cause) {
        _logger.error(message, cause);
    }
    
    /**
     * Log 等級為 FATAL.
     * @param message 要 log 的訊息
     */
    protected void fatal(String message) {
        _logger.error(message);
    }

    /**
     * Log 等級為 FATAL.
     * @param message 要 log 的訊息
     * @param cause 錯誤的 exception.
     */
    protected void fatal(String message, Throwable cause) {
        _logger.error(message, cause);
    }
}

