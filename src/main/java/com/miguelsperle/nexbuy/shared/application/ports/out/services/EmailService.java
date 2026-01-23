package com.miguelsperle.nexbuy.shared.application.ports.out.services;

public interface EmailService {
    void sendEmail(String to, String text, String subject);
}
