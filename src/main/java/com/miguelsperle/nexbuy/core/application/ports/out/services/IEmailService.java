package com.miguelsperle.nexbuy.core.application.ports.out.services;

import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendEmail(String to, String text, String subject) throws MessagingException;
}
