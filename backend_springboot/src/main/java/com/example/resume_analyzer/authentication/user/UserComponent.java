package com.example.resume_analyzer.authentication.user;

import com.example.resume_analyzer.authentication.user.entity.UserEntity;
import com.example.resume_analyzer.authentication.user.repo.UserRepo;
import com.example.resume_analyzer.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserComponent {
    private final UserRepo userRepo;
    public UserEntity userLogin(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepo.findByUsername(username).orElseThrow(
                ()-> new CustomException(HttpStatus.NOT_FOUND, "No user login")
        );
        return user;
    }
}
