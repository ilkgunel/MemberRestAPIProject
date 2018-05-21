package com.ilkaygunel.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

	@Autowired
	private JavaMailSender mailSender;

	public SimpleMailMessage templateForSimpleMessage() {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("Hesabınızı Aktifleştirin");
		simpleMailMessage.setText(
				"Hesabınızın aktifleştirilmesi için bu linke tıklayınız: \nhttp://localhost:8080/MemberRestAPIProject/activateMemberWebServiceEndpoint/activateMember?activationToken=%s/");

		return simpleMailMessage;
	}

	public void sendActivationMail(String emailAddress, String activationToken) throws MessagingException {
		SimpleMailMessage simpleMailMessage = templateForSimpleMessage();
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setText(String.format(simpleMailMessage.getText(), activationToken), true);
		mimeMessageHelper.setTo(emailAddress);
		mimeMessage.setSubject(simpleMailMessage.getSubject());

		/*
		 * FileSystemResource file = new FileSystemResource(new
		 * File("/home/ilkaygunel/Desktop/notlar.txt"));
		 * mimeMessageHelper.addAttachment("Notes", file);
		 */
		mailSender.send(mimeMessage);
	}

}
