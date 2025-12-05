package com.example.pigeonhub.email;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void send(EmailRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name()
            );

            Map<String, Object> properties = new HashMap<>();
            properties.put("username", request.username());
            properties.put("code", request.code());
            properties.put("sender", request.sender());

            Context context = new Context();
            context.setVariables(properties);

            mimeMessageHelper.setTo(request.to());
            mimeMessageHelper.setSubject(request.subject());

            EmailTemplateName templateName;
            try {
                templateName = EmailTemplateName.valueOf(request.templateName());
            } catch (IllegalArgumentException e) {
                log.warn("Invalid template name '{}', using default template.", request.templateName());
                templateName = EmailTemplateName.DEFAULT_TEMPLATE;
            }

            String template = templateEngine.process(templateName.getName(), context);
            mimeMessageHelper.setText(template, true);

            mailSender.send(mimeMessage);
            log.info("Pigeon arrived at: {}", request.to());
            
        } catch (MessagingException e) {
            log.error("Pigeon could not reach: {}", request.to(), e);
        }
    }
}
