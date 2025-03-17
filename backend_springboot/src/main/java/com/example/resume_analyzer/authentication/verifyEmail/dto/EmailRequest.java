package com.example.resume_analyzer.authentication.verifyEmail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EmailRequest {
    private String email;
    private Integer code;
}
