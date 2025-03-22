package com.example.resume_analyzer.authentication.user.service;

import com.example.resume_analyzer.authentication.user.dto.UserRequest;
import com.example.resume_analyzer.authentication.user.dto.UserResponse;
import com.example.resume_analyzer.authentication.user.repo.UserRepo;
import com.example.resume_analyzer.authentication.verifyEmail.repo.EmailRepo;
import com.example.resume_analyzer.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final EmailRepo emailRepo;
    public UserResponse createUser(UserRequest request){
        if (userRepo.existsByUsername(request.getUsername()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Username already exists");
        else if (userRepo.existsByEmail(request.getEmail()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Email already exists");
        else if (emailRepo)
    }

}
