package com.example.pigeonhub.email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplateName {
    
    EMAIL_VERIFICATION("email_verification"),

    DEFAULT_TEMPLATE("default_template");

    private final String name;
}
