/**
 * @author Nayan Kumar G
 * purpose : To create mail and send the mail
 * date    :28-02-2020
 */
package com.bridgelabz.fundoonotes.utility;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailUtil {

	/**
	 * 
	 * @param emailTo send the mail to user
	 * @param subject subject of mail
	 * @param body message of mail
	 */
	public static void sendAttachmentEmail(String emailTo , String subject , String body)
	{
		String mailFrom = System.getenv("email");
		String password = System.getenv("password");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, password);
			}
		};
		
		Session session = Session.getInstance(properties, auth);
		send(session, mailFrom, emailTo, subject, body);
	}

	/**
	 * method to send the mail to user
	 */
	private static void send(Session session, String mailFrom, String emailTo, String subject, String body) {
		try
		{
		MimeMessage message=new MimeMessage(session);
		message.setFrom(new InternetAddress(mailFrom, "Nayan"));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailTo));
		message.setSubject(subject);
		message.setText(body);
		Transport.send(message);
	}catch (MessagingException | UnsupportedEncodingException e) {
        log.error("Error in sending mail");
     }
	
	}
	
	public static String createLink(String url , String token)
	{
		return url+""+token;
	}
	

}
