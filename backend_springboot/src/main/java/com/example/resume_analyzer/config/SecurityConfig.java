package com.example.resume_analyzer.config;

import com.example.resume_analyzer.authentication.token.TokenHandler;
import com.example.resume_analyzer.authentication.token.TokenUtils;
import com.example.resume_analyzer.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                        auth.anyRequest().permitAll()
                ).addFilterBefore(
                        new TokenHandler(userService, tokenUtils),
                        AuthenticationFilter.class
                );
        return http.build();
    }
}
