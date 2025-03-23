package com.example.resume_analyzer.authentication.token;

import com.example.resume_analyzer.authentication.token.dto.TokenRequest;
import com.example.resume_analyzer.authentication.token.dto.TokenResponse;
import com.example.resume_analyzer.authentication.user.UserComponent;
import com.example.resume_analyzer.authentication.user.dto.UserDto;
import com.example.resume_analyzer.authentication.user.entity.UserEntity;
import com.example.resume_analyzer.authentication.user.service.UserService;
import com.example.resume_analyzer.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final UserComponent userComponent;

    public TokenResponse generateToken(TokenRequest request){
        UserDto user = (UserDto) userService.loadUserByUsername(request.getUsername());
        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new CustomException(HttpStatus.CONFLICT, "Wrong password");
        
    }
}
