package com.ilkaygunel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public SimpleMailMessage templateForSimpleMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Hesabınızı Aktifleştirin");
        simpleMailMessage.setText("Hesabınızın aktifleştirilmesi için bu linke tıklayınız: \nhttp://localhost:8080/MemberRestAPIProject/activateMemberWebServiceEndpoint/activateMember?activationToken=%s/");
        //simpleMailMessage.setText("Hesabınızın aktifleştirilmesi için bu linke tıklayınız: http://google.com/");

        return simpleMailMessage;
    }

    public void sendActivationMail(String emailAddress, String activationToken) {
        SimpleMailMessage simpleMailMessage = templateForSimpleMessage();
        simpleMailMessage.setText(String.format(simpleMailMessage.getText(), activationToken));
        simpleMailMessage.setTo(emailAddress);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setText(String.format(simpleMailMessage.getText(), activationToken),true);
            mimeMessageHelper.setTo(emailAddress);
            mimeMessage.setSubject(simpleMailMessage.getSubject());
        } catch (MessagingException messagingException) {

        }
        mailSender.send(mimeMessage);
    }

}
