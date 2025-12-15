package com.kayles.employee_management_system.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayles.employee_management_system.service.security.AuthenticationService;
import com.kayles.employee_management_system.controller.impl.AuthenticationControllerImpl;
import com.kayles.employee_management_system.dto.security.AuthenticationResponse;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.enums.GenderEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        AuthenticationControllerImpl controller = new AuthenticationControllerImpl(authenticationService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void test() throws Exception {
        JwtPerson jwt = JwtPerson.builder()
                .login("newUser")
                .password("123")
                .gender(GenderEnum.MAN)
                .build();

        AuthenticationResponse response = AuthenticationResponse.builder()
                .login("newUser")
                .token("jwt.token.here")
                .build();

        when(authenticationService.register(any(JwtPerson.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwt))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("newUser"))
                .andExpect(jsonPath("$.token").value("jwt.token.here"));
    }


}
