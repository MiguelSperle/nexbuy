package com.miguelsperle.nexbuy.core.infrastructure.services;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.FailedToSendEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpEmailService implements IEmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String to, String text, String subject) {
        try {
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(this.from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(subject);

            this.javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            throw new FailedToSendEmailException("Failed to send email", messagingException);
        }
    }
}
