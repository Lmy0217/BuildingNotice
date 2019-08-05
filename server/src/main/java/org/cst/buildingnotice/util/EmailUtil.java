package org.cst.buildingnotice.util;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.cst.buildingnotice.config.Config;

import com.sun.mail.util.MailSSLSocketFactory;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {
    
    public static void sendEmail(String toEmailAddress, String emailTitle, 
    		String emailContent) {
    	
        Properties props = new Properties();
        
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");
        
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", Config.EMAIL_SMTP_HOST);
        props.put("mail.smtp.port", Config.EMAIL_SMTP_PORT);
        
        MailSSLSocketFactory socketFactory;
        Transport transport = null;
		try {
			socketFactory = new MailSSLSocketFactory();
	        socketFactory.setTrustAllHosts(true);
	        props.put("mail.smtp.ssl.enable", "true");
	        props.put("mail.smtp.ssl.socketFactory", socketFactory);
	         
	        Session session = Session.getInstance(props);
	        Message msg = new MimeMessage(session);
	         
	        StringBuilder builder = new StringBuilder();
	        builder.append(emailContent);
	        builder.append("\n官网：" + Config.ROOT_HOST);
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = new Date();
	        builder.append("\n时间：" + dateFormat.format(date));
	         
	        msg.setSubject(emailTitle);
	        msg.setText(builder.toString());
	        msg.setSentDate(date);
	        msg.setFrom(new InternetAddress(Config.EMAIL_ACCOUNT, Config.ROOT_NAME, "UTF-8"));
	         
	        transport = session.getTransport();
	        transport.connect(Config.EMAIL_SMTP_HOST, Config.EMAIL_ACCOUNT, Config.EMAIL_PWD);
	        transport.sendMessage(msg, new Address[] { new InternetAddress(toEmailAddress) });
	        
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
	        try {
				if (transport != null) transport.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
    }
}
