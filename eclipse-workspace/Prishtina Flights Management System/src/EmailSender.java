//ketu ka me i shkru package kur te defionohen sakt, tash package eshte default

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender {
    public static boolean sendMail(String from,String emailPassword,String message,String to[])
    {
        String host="smtp.gmail.com";
        Properties props=System.getProperties();
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.user",from);
        props.put("mail.smtp.password",emailPassword);
        props.put("mail.smtp.port",25);
        props.put("mail.smtp.auth","true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.socketFactory.port", 25);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
        props.setProperty("mail.smtp.port", "465"); 
        props.setProperty("mail.smtp.socketFactory.port", "465"); 
        
        
        Session session=Session.getDefaultInstance(props,null);
        //session.setDebug(true);
        MimeMessage mimeMessage=new MimeMessage(session);
        try
        {
            mimeMessage.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress=new InternetAddress[to.length];
            for(int i=0;i<to.length;i++)
            {
                toAddress[i]=new InternetAddress(to[i]);
            }
            for(int i=0;i<toAddress.length;i++)
            {
                mimeMessage.addRecipient(RecipientType.TO,toAddress[i]);
            }
            mimeMessage.setSubject("Test");
            mimeMessage.setText(message);
            Transport transport=session.getTransport("smtps");
            transport.connect(host,from,emailPassword);
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
            transport.close();
            return true;
        }
        catch(MessagingException me)
        {
            me.printStackTrace();
        }
        return false;
    }
}
