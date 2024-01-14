package com.example.multifactorauthservice.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultMailSender implements MailSender {

    private final JavaMailSender emailSender;

    @Override
    public void sendCode(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("otp-sender@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        this.emailSender.send(message);

        System.out.println("Message sent successfully");
    }

}
