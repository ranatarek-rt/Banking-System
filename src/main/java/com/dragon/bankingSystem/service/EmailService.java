package com.dragon.bankingSystem.service;


import com.dragon.bankingSystem.model.EmailDetails;

public interface EmailService {
    void sendEmail(EmailDetails emailDetails);
}
