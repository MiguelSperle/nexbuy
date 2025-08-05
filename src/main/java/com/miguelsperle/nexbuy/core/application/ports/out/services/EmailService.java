package com.miguelsperle.nexbuy.core.application.ports.out.services;

public interface EmailService {
    void sendEmail(String to, String text, String subject);
}
