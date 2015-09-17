package com.catb.service.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.catb.common.Constants;
import com.catb.common.exception.AppException;
import com.catb.vo.MailContent;

public class MailSender {
	
	private static Logger logger = Logger.getLogger(MailSender.class);
	
	public static void send(String receiver, MailContent mailContent) {
		Properties properties = new Properties();

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.host", Constants.SMTP_HOST);
		properties.put("mail.smtp.port", Constants.SMTP_PORT);

		Session session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(Constants.EMAIL_SENDER, Constants.EMAIL_SENDER_PASSWORD);
					}
				});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Constants.EMAIL_SENDER, "Hòm thư tố giác tội phạm - Công an tỉnh Thái Bình", "UTF-8"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));

			message.setSubject("Phản hồi v/v: " + mailContent.getTitle(), "UTF-8");

			Multipart multipart = new MimeMultipart();
			MimeBodyPart m = new MimeBodyPart();
			m.setContent(buildMailContent(mailContent), "text/html; charset=utf-8");
			multipart.addBodyPart(m);
			message.setContent(multipart);

			Transport.send(message);
		} catch (Exception ex) {
			logger.error("Failed to send mail ", ex);
			throw new AppException(ex);
		}
	}
	
	private static String buildMailContent(MailContent mailContent) {
		StringBuilder builder = new StringBuilder();
		builder.append("<h4>Tố cáo: ").append(mailContent.getTitle()).append("</h4><br />");
		builder.append("<span style='white-space: pre-wrap;'>").append(mailContent.getContent()).append("</span>");
		builder.append("<div style='height: 40px;'></div>");
		builder.append("<h4>Phản hồi</h4><br />");
		builder.append("<span style='white-space: pre-wrap;'>").append(mailContent.getReplyContent()).append("</span>");
		
		return builder.toString();
	}
}
