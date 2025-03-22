package com.example.resume_analyzer.authentication.user.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authorities = authority.split(",");
        return Arrays.stream(authorities).map(SimpleGrantedAuthority::new).toList();
    }
}
