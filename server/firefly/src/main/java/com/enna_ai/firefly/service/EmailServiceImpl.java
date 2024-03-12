package com.enna_ai.firefly.service;

import com.enna_ai.firefly.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendAutomatedEmail(Email email) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        mimeMessageHelper.setTo(email.getRecipient());
        mimeMessageHelper.setFrom(this.username);
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setText(email.getBody());

        new Thread(() -> {
            System.out.println("Sending automated email...");
            javaMailSender.send(mimeMessage);
            System.out.println("Successfully sent email!");
        }).start();
    }
}
