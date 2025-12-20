package com.kayles.employee_management_system.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayles.employee_management_system.controller.impl.AdminControllerImpl;
import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.RoleDto;
import com.kayles.employee_management_system.enums.RoleEnum;
import com.kayles.employee_management_system.service.ImageService;
import com.kayles.employee_management_system.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerImplTest {

    @Mock
    private PersonService personService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private AdminControllerImpl adminController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Настраиваем SecurityContext с авторизацией админа
        setupAdminSecurityContext();

        // Настраиваем MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }

    private void setupAdminSecurityContext() {
        // Создаем аутентификацию с правами админа
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "admin", // principal
                        null, // credentials
                        List.of(
                                new SimpleGrantedAuthority("ROLE_ADMIN"),
                                new SimpleGrantedAuthority("ROLE_USER")
                        )
                );

        // Устанавливаем в SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testUpdatePersonById_WithAdminRole_ShouldUpdateSuccessfully() throws Exception {
        // Given
        PersonDto personDto = PersonDto.builder()
                .id(1L)
                .login("updatedUser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .build();

        PersonDto updatedPerson = PersonDto.builder()
                .id(1L)
                .login("updatedUser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .build();

        // Мокаем сервис - ТОЛЬКО здесь, где это нужно для теста
        when(personService.update(any(PersonDto.class))).thenReturn(updatedPerson);

        // When & Then
        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("updatedUser"))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("User"));

        // Verify
        verify(personService, times(1)).update(any(PersonDto.class));
    }

    //TODO найденая ошибка
    @Test
    void testSetRoleById_WithInvalidRole_ShouldReturnBadRequest() throws Exception {
        // Given
        Long personId = 1L;
        RoleDto invalidRoleDto = RoleDto.builder()
                .build();

        // When & Then
        mockMvc.perform(put("/admin/updateRole/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRoleDto)))
                .andExpect(status().isBadRequest());

        // Verify что сервис не вызывался
        verify(personService, never()).updatePersonRoleById(anyLong(), any());
    }

    @Test
    void testDeletePersonById_ShouldDeleteSuccessfully() throws Exception {
        // Given
        Long personId = 1L;

        // Мокаем сервис - ТОЛЬКО здесь
        doNothing().when(personService).delete(personId);

        // When & Then
        mockMvc.perform(delete("/admin/deletePersonById/{id}", personId))
                .andExpect(status().isOk());

        verify(personService, times(1)).delete(personId);
    }

    @Test
    void testRecreateByIdFromFile_WithImage_ShouldUpdateSuccessfully() throws Exception {
        // Given
        Long imageId = 1L;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        ImageDto imageDto = ImageDto.builder()
                .id(imageId)
                .image("test image content".getBytes())
                .build();

        // Метод возвращает ImageDto
        when(imageService.recreate(eq(imageId), any())).thenReturn(imageDto);

        // When & Then
        mockMvc.perform(multipart("/admin/image/recreate/{id}", imageId)
                        .file(file))
                .andExpect(status().isOk());

        verify(imageService, times(1)).recreate(eq(imageId), any());
    }

    @Test
    void testUpdatePersonById_WithPathId_ShouldUpdateSuccessfully() throws Exception {
        // Given
        Long personId = 1L;
        PersonDto personDto = PersonDto.builder()
                .login("updatedUser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .build();

        PersonDto updatedPerson = PersonDto.builder()
                .id(personId)
                .login("updatedUser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .build();

        // Мокаем сервис - ТОЛЬКО здесь
        when(personService.updateById(any(PersonDto.class), eq(personId)))
                .thenReturn(updatedPerson);

        // When & Then
        mockMvc.perform(put("/admin/updatePerson/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personId))
                .andExpect(jsonPath("$.login").value("updatedUser"));

        verify(personService, times(1)).updateById(any(PersonDto.class), eq(personId));
    }
}