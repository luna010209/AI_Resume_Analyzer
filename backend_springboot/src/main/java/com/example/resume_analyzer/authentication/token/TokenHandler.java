package com.example.resume_analyzer.authentication.token;

import com.example.resume_analyzer.authentication.user.dto.UserDto;
import com.example.resume_analyzer.authentication.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenHandler extends OncePerRequestFilter {
    private final UserService userService;
    private final TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header==null){
            filterChain.doFilter(request, response);
            return;
        }

        String[] headerSplit = header.split(" ");
        if (!(headerSplit.length==2 && headerSplit[0].equals("Bearer"))){
            filterChain.doFilter(request, response);
            return;
        }

        String token = headerSplit[1];
        String username = tokenUtils.claims(token).getSubject();
        UserDto user = (UserDto) userService.loadUserByUsername(username);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}
