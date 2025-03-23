package com.example.resume_analyzer.authentication.user.dto;

import com.example.resume_analyzer.authentication.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String name;

    public static UserResponse fromEntity(UserEntity user){
        UserResponse response = UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .build();
        return response;
    }
}
