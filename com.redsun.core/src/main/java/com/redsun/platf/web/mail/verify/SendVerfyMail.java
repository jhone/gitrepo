package com.redsun.platf.web.mail.verify;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-8-30</br>
 * Time: 下午4:23</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-30    joker pan    created
 * <pre/>
 */
public class SendVerfyMail {
    String username = "dengzhaoqun@163.com";

    private SimpleMailMessage getMessage() {
        Properties p = new Properties();
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.host", "smtp.163.com");
        p.put("mail.smtp.port", "25");
        p.put("mail.smtp.auth", "true");
        String password = "20083111tian";
        MyAuthor auth = new MyAuthor(username, password);
        Session session = Session.getDefaultInstance(p, auth);
        SimpleMailMessage message = new SimpleMailMessage(session);
        return message;
    }

    //    驗證
    public void sendVerify(String stu_email, String stu_nameMd5, String randMd5)
            throws MessagingException {
        SimpleMailMessage message = getMessage();
        message.setFrom(new InternetAddress(username));
        message.setTo(new InternetAddress(stu_email));
//            message.setRecipient(RecipientType.TO,new InternetAddress(stu_email));
        message.setSentDate(new Date());
        message.setSubject("MailVerify");
        String m = "<a href=\"http://127.0.01:8080/Mail/mailVerify?stu_nameMd5=" + stu_nameMd5 + "&randMd5=" + randMd5 + "\">" +
                "http:/127.0.01:8080/Mail/mailVerify?stu_nameMd5=" + stu_nameMd5 + "&randMd5=" + randMd5 + "</a>";
        message.setContent(m, "text/html;charset=gb2312");
        MailSender sender=new MailSender() {
            @Override
            public void send(SimpleMailMessage simpleMessage) throws MailException {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void send(SimpleMailMessage[] simpleMessages) throws MailException {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        } ;

          sender.send(message);
//        Transport.send(message);
    }

    //重設
    public void sendReset(String stu_email, String stu_nameMd5, String randMd5)
            throws MessagingException {
        MailMessage message = getMessage();
        message.setFrom(new InternetAddress(username));
        message.setRecipient(RecipientType.TO, new InternetAddress(stu_email));
        message.setSentDate(new Date());
        message.setSubject("MailVerify");
        String m = "<a href=\"http://127.0.01:8080/Mail/mailReset?stu_nameMd5=" + stu_nameMd5 + "&randMd5=" + randMd5 + "\">" +
                "http://127.0.01:8080/Mail/mailReset?stu_nameMd5=" + stu_nameMd5 + "&randMd5=" + randMd5 + "</a>";
        message.setContent(m, "text/html;charset=gb2312");
        Transport.send(message);
    }
}

