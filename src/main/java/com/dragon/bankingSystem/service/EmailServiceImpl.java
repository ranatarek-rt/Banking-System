package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.model.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{


    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendEmail;

    @Override
    public void sendEmail(EmailDetails emailDetails) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sendEmail);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMessageBody());
            mailSender.send(mailMessage);
            System.out.println("the mail is sent successfully to the user");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
