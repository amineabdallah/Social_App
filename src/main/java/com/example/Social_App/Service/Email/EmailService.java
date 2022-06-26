package com.example.Social_App.Service.Email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(final String to, final String email) {
        try {
            final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            final MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("socialmedia@socialmedia.com");
            javaMailSender.send(mimeMessage);

        } catch (final MessagingException e) {
            log.error("failed to send email");
            throw new IllegalStateException("failed to send email");
        }

    }
}
