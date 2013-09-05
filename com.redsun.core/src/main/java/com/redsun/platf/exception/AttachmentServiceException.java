package com.redsun.platf.exception;


/**
 * <p>Title: com.walsin.platf.AttachmentServiceException</p>
 * <p>Description: EP系統附件記錄服務例外事件</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public class AttachmentServiceException extends Exception{

    private static final long serialVersionUID = -5063560011705792786L;

    public AttachmentServiceException() {
        super();
    }

    public AttachmentServiceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AttachmentServiceException(String detailMessage) {
        super(detailMessage);
    }

    public AttachmentServiceException(Throwable throwable) {
        super(throwable);
    }
}
