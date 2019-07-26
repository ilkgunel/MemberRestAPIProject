package com.ilkaygunel.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ilkaygunel.application.ResourceBundleMessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceBundleMessageManager resourceBundleMessageManager;

    private SimpleMailMessage templateForSimpleMessage(String mailType, String locale) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject(resourceBundleMessageManager.getValueOfProperty("subject." + mailType, locale));
        simpleMailMessage.setText(
                resourceBundleMessageManager.getValueOfProperty("mailText." + mailType, locale) +
                resourceBundleMessageManager.getValueOfProperty("link." + mailType, locale)
        );

        return simpleMailMessage;
    }

    public void sendActivationMail(String emailAddress, String activationToken) throws MessagingException {
        SimpleMailMessage simpleMailMessage = templateForSimpleMessage("activateAccount", "tr");
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

    public void sendPasswordResetMail(String emailAddress, String passwordResetToken, String memberLanguageCode) throws MessagingException {
        SimpleMailMessage simpleMailMessage = templateForSimpleMessage("resetPassword", memberLanguageCode);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setText(String.format(simpleMailMessage.getText(), passwordResetToken), true);
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
