package com.example.pigeonhub.email;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    
    private final Resend resend;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void send(EmailRequest request) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", request.username());
        properties.put("code", request.code());
        properties.put("sender", request.sender());

        Context context = new Context();
        context.setVariables(properties);

        EmailTemplateName templateName;
        try {
            templateName = EmailTemplateName.valueOf(request.templateName());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid template name '{}', using default template.", request.templateName());
            templateName = EmailTemplateName.DEFAULT_TEMPLATE;
        }

        String template = templateEngine.process(templateName.getName(), context);

        CreateEmailOptions emailOptions = new CreateEmailOptions.Builder()
                .from(request.from())
                .to(request.to())
                .subject(request.subject())
                .html(template)
                .build();
        
        try {
            resend.emails().send(emailOptions);
        } catch (ResendException e) {
            log.error("Failed to send from {} | to {}", request.from(), request.to(), e);
        }
    }
}
