package com.redsun.platf.exception;

/**
 * <p>Title: com.walsin.platf.service.impl.MailException</p>
 * <p>Description: Mail Exception</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public class MailException extends Exception{

    private static final long serialVersionUID = -8648633526474557077L;

    public MailException() {
        super();
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailException(String message) {
        super(message);
    }

    public MailException(Throwable cause) {
        super(cause);
    }
}
