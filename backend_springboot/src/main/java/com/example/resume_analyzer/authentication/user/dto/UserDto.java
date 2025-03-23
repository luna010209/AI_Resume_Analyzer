package com.example.resume_analyzer.authentication.user.dto;

import com.example.resume_analyzer.authentication.user.entity.UserEntity;
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

    public static UserDto fromEntity (UserEntity user){
        UserDto dto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .authority(user.getAuthority()).build();
        return dto;
    }
}
