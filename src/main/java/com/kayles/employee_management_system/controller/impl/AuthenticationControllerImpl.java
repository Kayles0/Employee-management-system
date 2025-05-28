package com.kayles.employee_management_system.controller.impl;

import com.kayles.employee_management_system.Service.security.AuthenticationService;
import com.kayles.employee_management_system.controller.AuthenticationController;
import com.kayles.employee_management_system.dto.security.AuthenticationRequest;
import com.kayles.employee_management_system.dto.security.AuthenticationResponse;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {
    private final AuthenticationService authenticationService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody JwtPerson request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
