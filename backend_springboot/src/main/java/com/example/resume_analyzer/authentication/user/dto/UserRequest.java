package com.example.resume_analyzer.authentication.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserRequest {
    private String username;
    private String password;
    private String pwConfirm;
    private String email;
    private String name;
}
