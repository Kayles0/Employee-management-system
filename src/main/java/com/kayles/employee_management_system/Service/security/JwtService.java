package com.kayles.employee_management_system.Service.security;


import com.kayles.employee_management_system.dto.security.JwtPerson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private static final Long TOKEN_VALIDITY = (long) (1000 * 60 * 60);

    private static final String PROFILE_ROLES = "authorities";
    private static final String PROFILE_LOGIN = "login";
    private static final String PROFILE_ID = "id";

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractLogin(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private SecretKey getSignKey(){
        byte[] keyBytes = Base64.getDecoder()
                .decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(JwtPerson jwtPerson) {
        return Jwts.builder()
                .subject(jwtPerson.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(getSignKey())
                .claim(PROFILE_ID, jwtPerson.getId())
                .claim(PROFILE_LOGIN, jwtPerson.getLogin())
                .claim(PROFILE_ROLES, jwtPerson.getAuthorities())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String login = extractLogin(token);
        return login.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
