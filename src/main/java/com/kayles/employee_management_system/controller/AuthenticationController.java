package com.kayles.employee_management_system.controller;

import com.kayles.employee_management_system.dto.security.AuthenticationRequest;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;

@Tag(name = "Контроллер аутентификации")
public interface AuthenticationController {
    @Operation(summary = "Регистрация пользователя")
    ResponseEntity<AuthenticatorResponse> register(JwtPerson request);

    @Operation(summary = "Авторизация пользователя")
    ResponseEntity<AuthenticatorResponse> login(AuthenticationRequest request);
}
