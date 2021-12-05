package com.searchengine.notifications;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.saerchengine.TestMain;
import com.searchengine.searchhistory.ConnectionFactory;

import  javax.mail.Authenticator;

public class EmailNotification {
	static Logger log = Logger.getLogger(TestMain.class);
	static Scanner sc = new Scanner(System.in);
	public static void mail(String recepient,String UserName,String Password) throws Exception {
		//authentication info
		
		log.info("Preparing to sent mail");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		 String username = "shivugowda1999@gmail.com";
		 String password = "Shivu@Bcone.com";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
			
		});
		Message message = prepareMessage(session,username,recepient,UserName,Password);
		Transport.send(message);
		
		log.info("Mail sent successfully");
	}
		//Start our mail message
		private static Message prepareMessage(Session session,String username,String recepient,String User,String Password ) {
			Connection connection= null;
			connection=	ConnectionFactory.create();
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
				message.setSubject("Login Credential");
				message.setText("WELCOME TO SEARCH ENGINE "+"\n"+"\n"+"For your reference here is your login information"+"\n"+"UserName"+":"+User+"\n"+"Password"+":"+Password);
				return message;

			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}
}