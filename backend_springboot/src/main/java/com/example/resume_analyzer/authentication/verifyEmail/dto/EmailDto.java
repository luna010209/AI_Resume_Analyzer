package com.example.resume_analyzer.authentication.verifyEmail.dto;

import com.example.resume_analyzer.authentication.verifyEmail.entity.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EmailDto {
    private String email;
    private Integer code;
    private Date expiration;
    private boolean isSuccess;
    public static EmailDto fromEntity(Email email){
        EmailDto dto = EmailDto.builder()
                .email(email.getEmail())
                .code(email.getCode())
                .expiration(email.getExpiration())
                .isSuccess(email.isSuccess())
                .build();
        return dto;
    }
}
