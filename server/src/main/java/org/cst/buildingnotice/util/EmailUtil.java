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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {
    
    public static boolean sendEmail(String toEmailAddress, String emailTitle, 
            String emailContent) {
        
        boolean flag = true;
        
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
//            builder.append("<br/><br/>此致<br/>"
//                    + "<a style=\"text-decoration:none;color:#5bc0eb;\" href=\"" 
//                    + Config.ROOT_HOST + "\"><strong>" 
//                    + Config.NAME_TEAM + "</strong></a><br/>");
            builder.append("<br/><br/>此致<br/>" + Config.NAME_TEAM + "<br/>" + Config.ROOT_HOST + "/<br/>");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            builder.append(dateFormat.format(date));
             
            msg.setSubject("【" + Config.NAME_SYSTEM + "】" + emailTitle);
            msg.setContent(builder.toString(), "text/html;charset=utf-8");
            msg.setSentDate(date);
            msg.setFrom(new InternetAddress(Config.EMAIL_ACCOUNT, Config.NAME_SYSTEM, "UTF-8"));
             
            transport = session.getTransport();
            transport.connect(Config.EMAIL_SMTP_HOST, Config.EMAIL_ACCOUNT, Config.EMAIL_PWD);
            transport.sendMessage(msg, new Address[] { new InternetAddress(toEmailAddress) });
            
        } catch (Exception e) {
            flag = false;
            
        } finally {
            try {
                if (transport != null) transport.close();
                
            } catch (MessagingException e) {
                flag = false;
            }
        }
        
        return flag;
    }
    
    public static boolean sendTemplate(String toEmailAddress, String title, 
    		String depict, String name, String link) {
    	
    	StringBuilder builder = new StringBuilder();
    	builder.append(name + "，<br/>");
    	builder.append("您收到这封邮件，是由于" + Config.NAME_SYSTEM + depict + "。");
    	builder.append("如果您并没有使用过" + Config.NAME_SYSTEM + "，或没有进行上述操作，请忽略这封邮件。您不需要退订或进行其他进一步的操作。<br/>");
    	builder.append("<br/>----------------------------------------------------------------------<br/>");
    	builder.append(title + "说明<br/>");
    	builder.append("----------------------------------------------------------------------<br/><br/>");
    	builder.append("您只需点击下面的链接即可" + title + "：<br/>");
    	builder.append("<a href=\"" + link + "\">" + link + "</a><br/>");
    	builder.append("(如果上面不是链接形式，请将该地址手工粘贴到浏览器地址栏再访问)<br/>");
    	builder.append("感谢您的访问，祝您使用愉快！");
    	
    	return sendEmail(toEmailAddress, title, builder.toString());
    }
    
    public static boolean sendVerify(String toEmailAddress, String name, String verifyLink) {
    	
    	return sendTemplate(toEmailAddress, "验证邮箱", "用户填写邮箱使用了这个邮箱地址", name, verifyLink);
    }
    
    public static boolean sendReset(String toEmailAddress, String name, String resetLink) {
    	
    	return sendTemplate(toEmailAddress, "重置密码", "用户绑定了这个邮箱并正在进行重置密码操作", name, resetLink);
    }
}
