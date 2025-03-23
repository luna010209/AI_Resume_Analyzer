package com.example.resume_analyzer.authentication.token;

import com.example.resume_analyzer.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class TokenUtils {
    private final Key secretKey;
    private final JwtParser jwtParser;

    public TokenUtils(@Value("${jwt.secret}") String code){
        this.secretKey = Keys.hmacShaKeyFor(code.getBytes());
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey).build();
    }

    public String generateToken(String username){
        Instant now = Instant.now();
        Claims jwtClaims = Jwts.claims()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(60*60)));
        return Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(secretKey)
                .compact();
    }

    public boolean validate(String token){
        try{
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            if (claims.getExpiration().before(Date.from(Instant.now())))
                return false;
            return true;
        } catch (Exception e){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Claims claims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
