package com.mailprocess;

import java.util.Properties;
import java.util.Random;

import com.auth.entity.User;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


public class EmailProcess {
	public String generateVarificationKey() {

		Random random = new Random();
	    int number = 100000 + random.nextInt(900000); // ensures a 6-digit number
	    return String.valueOf(number);
    }
	
	public boolean sendEmail( String email, String subject, String emailContent ) {
		boolean isEmailSend = false;
		String fromEmail = "noreply.complaintmanagement@gmail.com";
		String password = "yswfcimdwuptezwy";
		String toEmail = email;
		try {
			Properties propertie = new Properties();
			propertie.setProperty("mail.smtp.host", "smtp.gmail.com");
			propertie.setProperty("mail.smtp.port", "465");
			propertie.setProperty("mail.smtp.auth", "true");
			propertie.setProperty("mail.smtp.ssl.enable", "true"); 

			Session session = Session.getInstance(propertie, new Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(fromEmail, password);
			    }
			});

			//session.setDebug(true);//added here for testing purpose
			
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); 
			message.setSubject(subject);
			message.setContent(emailContent, "text/html; charset=utf-8");
			
			//last send message
			Transport.send(message);
			isEmailSend=true;
			
		}catch(Exception e) {
			System.out.println("error in email".concat(toEmail) );
			e.printStackTrace();
		}
		
		return isEmailSend;
	}
}
