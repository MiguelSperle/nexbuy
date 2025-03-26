package com.miguelsperle.nexbuy.core.domain.abstractions.services;

public interface IEmailService {
    void sendEmail(String to, String text, String subject);
}
