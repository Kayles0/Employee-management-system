package com.kayles.employee_management_system.controller;

import com.kayles.employee_management_system.dto.security.AuthenticationRequest;
import com.kayles.employee_management_system.dto.security.AuthenticationResponse;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Контроллер аутентификации")
public interface AuthenticationController {
    @Operation(summary = "Регистрация пользователя")
    ResponseEntity<AuthenticationResponse> register(JwtPerson request);

    @Operation(summary = "Авторизация пользователя")
    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request);
}
