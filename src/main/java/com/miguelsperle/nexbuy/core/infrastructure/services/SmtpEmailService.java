package com.miguelsperle.nexbuy.core.infrastructure.services;

import com.miguelsperle.nexbuy.core.domain.abstractions.services.IEmailService;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.EmailSendFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpEmailService implements IEmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    @Retryable(
            retryFor = {EmailSendFailedException.class},
            maxAttempts = 4,
            backoff = @Backoff( // Exponential Backoff Configuration
                    delay = 2000, // initial delay
                    multiplier = 2, // multiply by two
                    maxDelay = 10000 // max delay of 10 seconds
            )
    )
    public void sendEmail(String to, String text, String subject) {
        try {
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(this.from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(subject);

            this.javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException exception) {
            log.error("Handling email sending failure - To: [{}], ExceptionMessage: [{}]", to, exception.getMessage(), exception);
            throw EmailSendFailedException.with("Email sending failed", exception);
        }
    }

    @Recover
    public void recover(EmailSendFailedException emailSendFailedException, String to, String text, String subject) {
        log.error("All retry attempts failed - To [{}], ExceptionMessage: [{}]", to, emailSendFailedException.getMessage(), emailSendFailedException);
    }
}
