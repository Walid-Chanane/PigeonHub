package com.example.pigeonhub.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(

    @NotBlank
    String sender,
    
    @Email
    @NotBlank
    String from,
    
    @Email
    @NotBlank
    String to,
    
    @NotBlank
    String username,

    @NotBlank
    String templateName,

    @NotBlank
    String code,

    @NotBlank
    String subject
) {}
