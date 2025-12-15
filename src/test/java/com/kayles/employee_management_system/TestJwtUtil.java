package com.kayles.employee_management_system;

import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.enums.RoleEnum;
import com.kayles.employee_management_system.service.security.JwtService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestJwtUtil {

    private final JwtService jwtService;

    public TestJwtUtil(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String generateAdminToken() {
        JwtPerson jwtPerson = JwtPerson.builder()
                .login("admin")
                .id(1L)
                .authorities(List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                ))
                .build();

        return "Bearer " + jwtService.generateToken(jwtPerson);
    }

    public String generateUserToken() {
        JwtPerson jwtPerson = JwtPerson.builder()
                .login("user")
                .id(2L)
                .authorities(List.of(
                        new SimpleGrantedAuthority("ROLE_USER")
                        ))
                .build();

        return "Bearer " + jwtService.generateToken(jwtPerson);
    }

    public String generateTokenForUser(String username, String login, Long id, List<? extends GrantedAuthority> authorities) {
        JwtPerson jwtPerson = JwtPerson.builder()
                .login(login)
                .id(id)
                .authorities(authorities)
                .build();

        return "Bearer " + jwtService.generateToken(jwtPerson);
    }
}