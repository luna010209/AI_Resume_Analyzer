package com.example.resume_analyzer.authentication.verifyEmail.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class EmailListener extends ApplicationEvent {
    private String email;
    private Integer code;
    public EmailListener(String email, Integer code) {
        super(email);
        this.email=email;
        this.code = code;
    }
}
