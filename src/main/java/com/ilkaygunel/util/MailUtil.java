package com.ilkaygunel.util;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtil {

    public void sendActivationMail(String emailAddress,String activationToken){
        String userName ="ilkgunel93@gmail.com";
        String password = "pmjjefscyjoocnmc";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator()
                {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication()
                    {

                        return new PasswordAuthentication(userName, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("localhost@localhost8084.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailAddress));
            message.setSubject("Hesabınızı Aktifleştirin");
            message.setText("Hesabınızın aktifleştirilmesi için bu linke tıklayınız: http://localhost:8080/MemberRestAPIProject/activateMemberWebServiceEndpoint/activateMember?activationToken="+activationToken);
            Transport.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
