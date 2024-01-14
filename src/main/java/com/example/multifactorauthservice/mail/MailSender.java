package com.example.multifactorauthservice.mail;

public interface MailSender {

    void sendCode(String toEmail, String subject, String body);

}
