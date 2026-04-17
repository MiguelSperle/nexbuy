package com.miguelsperle.nexbuy.shared.infrastructure.abstractions.services;

public interface EmailService {
    void sendEmail(String to, String text, String subject);
}
