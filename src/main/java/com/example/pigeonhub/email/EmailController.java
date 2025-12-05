package com.example.pigeonhub.email;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("email")
@RequiredArgsConstructor
public class EmailController {
    
    private final EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailRequest request) {
        emailService.send(request);
    }
    
}
