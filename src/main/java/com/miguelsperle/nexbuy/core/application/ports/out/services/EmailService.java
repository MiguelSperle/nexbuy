package com.miguelsperle.nexbuy.core.application.ports.out.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String text, String subject) throws MessagingException;
}
