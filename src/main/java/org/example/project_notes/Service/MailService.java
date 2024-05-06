package org.example.project_notes.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.project_notes.enumType.ErrorCode;
import org.example.project_notes.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@PropertySource("application.properties")
@RequiredArgsConstructor
@Log4j2
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Async
    public void sendPasswordResetEmail(String toAddress) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
            helper.setFrom(senderEmail);
            helper.setTo(toAddress);
            helper.setSubject("Напоминание о заметке");
            String content = "Вы оставили заметку в " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
        }
    }
}