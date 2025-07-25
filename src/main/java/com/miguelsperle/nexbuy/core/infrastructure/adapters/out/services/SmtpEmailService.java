package com.miguelsperle.nexbuy.core.infrastructure.adapters.out.services;

import com.miguelsperle.nexbuy.core.application.ports.out.services.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(String to, String text, String subject) throws MessagingException {
        try {
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(this.from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(subject);

            this.javaMailSender.send(mimeMessage);
        } catch (Exception exception) {
            log.error("Failed to send email to: [{}]", to, exception);
            throw exception;
        }
    }
}
