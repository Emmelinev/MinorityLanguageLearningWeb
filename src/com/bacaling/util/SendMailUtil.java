package com.bacaling.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import java.util.Date;
import java.util.Properties;

public class SendMailUtil {
    public static String myEmailAccount = "emmelinev@163.com";
    public static String myEmailPassword = "Aailin4115AI";
    public static String myEmailSMTPHost = "smtp.163.com";

    public static String receiveMailAccount = "emmelinev@foxmail.com";

    public static void sendMail() throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true"); 
        
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        MimeMessage message = createSimpleMessage(session, myEmailAccount, receiveMailAccount);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        
//        Session session2 = Session.getDefaultInstance(props);
//        session2.setDebug(true);
//        MimeMessage message2 = createMultiMessage(session2, myEmailAccount, receiveMailAccount);
//        Transport transport2 = session2.getTransport();
//        transport2.connect(myEmailAccount, myEmailPassword);
//        transport2.sendMessage(message2, message2.getAllRecipients());
//         
//        transport2.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createSimpleMessage(Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "BACALING - MINORITY LANGUAGES LEARNING", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "user", "UTF-8"));
        message.setSubject("Welcome", "UTF-8");
        message.setContent("<h1>We are waiting for you :)</h1>", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
    
    /**
     * 创建一封复杂邮件（文本+图片+附件）
     */
    public static MimeMessage createMultiMessage(Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "BACALING", "UTF-8"));
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMail, "Dear user", "UTF-8"));

        message.setSubject("Daily Learning Reminder", "UTF-8");

        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("E:/cDownloads/pony.jpg"));
        image.setDataHandler(dh);  
        image.setContentID("image_fairy_tail");

        MimeBodyPart text = new MimeBodyPart();
        text.setContent("<h1>We are waiting for you :)</h1>", "text/html;charset=UTF-8");

        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");

        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("E:/cDownloads/pony.jpg"));
        attachment.setDataHandler(dh2);
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment); 
        mm.setSubType("mixed");
        message.setContent(mm);
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }


}