package com.miguelsperle.nexbuy.core.domain.abstractions.services;

import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendEmail(String to, String text, String subject) throws MessagingException;
}
