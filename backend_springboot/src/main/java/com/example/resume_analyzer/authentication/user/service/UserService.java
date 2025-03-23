package com.example.resume_analyzer.authentication.user.service;

import com.example.resume_analyzer.authentication.user.dto.UserDto;
import com.example.resume_analyzer.authentication.user.dto.UserRequest;
import com.example.resume_analyzer.authentication.user.dto.UserResponse;
import com.example.resume_analyzer.authentication.user.entity.UserEntity;
import com.example.resume_analyzer.authentication.user.repo.UserRepo;
import com.example.resume_analyzer.authentication.verifyEmail.entity.Email;
import com.example.resume_analyzer.authentication.verifyEmail.repo.EmailRepo;
import com.example.resume_analyzer.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final EmailRepo emailRepo;
    private final PasswordEncoder encoder;
    public UserResponse createUser(UserRequest request){
        if (userRepo.existsByUsername(request.getUsername()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Username already exists");
        else if (userRepo.existsByEmail(request.getEmail()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Email already exists");
        Email email= emailRepo.findByEmail(request.getEmail()).orElseThrow(
                ()-> new CustomException(HttpStatus.BAD_REQUEST, "Your email is not verified yet")
        );
        if (!email.isSuccess())
            throw new CustomException(HttpStatus.BAD_REQUEST, "Your email is not verified yet");
        if (!request.getPassword().equals(request.getPwConfirm()))
            throw new CustomException(HttpStatus.CONFLICT, "Your password do not match");
        UserEntity user= UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .name(request.getName())
                .password(encoder.encode(request.getPassword()))
                .authority("ROLE_USER,ROLE_ADMIN")
                .build();
        return UserResponse.fromEntity(userRepo.save(user));
    }

}
