package com.example.resume_analyzer.authentication.token;

import com.example.resume_analyzer.authentication.token.dto.TokenRequest;
import com.example.resume_analyzer.authentication.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("token")
public class TokenController {
    private final TokenService tokenService;
    @PostMapping
    public TokenResponse generateToken(@RequestBody TokenRequest request){
        return tokenService.generateToken(request);
    }
}
