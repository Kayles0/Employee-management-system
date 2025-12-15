package com.kayles.employee_management_system.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayles.employee_management_system.controller.impl.ImageControllerImpl;
import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerImplTest {

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageControllerImpl imageController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateImageFromDto_ShouldReturnImageDto() throws Exception {
        // Given
        ImageDto imageDto = ImageDto.builder()
                .id(1L)
                .image("test image".getBytes())
                .build();

        when(imageService.createFromDto(any(ImageDto.class))).thenReturn(imageDto);

        // When & Then
        mockMvc.perform(post("/image")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imageDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(imageService, times(1)).createFromDto(any(ImageDto.class));
    }

    @Test
    void testCreateImageFromFile_ShouldReturnImageDto() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image".getBytes()
        );

        ImageDto imageDto = ImageDto.builder()
                .id(1L)
                .image("test image".getBytes())
                .build();

        when(imageService.createFromFile(any())).thenReturn(imageDto);

        // When & Then
        mockMvc.perform(multipart("/image/file")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(imageService, times(1)).createFromFile(any());
    }

    @Test
    void testReadImageById_ShouldReturnImageBytes() throws Exception {
        // Given
        Long imageId = 1L;
        byte[] imageBytes = "test image bytes".getBytes();

        ImageDto imageDto = ImageDto.builder()
                .id(imageId)
                .image(imageBytes)
                .build();

        when(imageService.read(imageId)).thenReturn(imageDto);

        // When & Then
        mockMvc.perform(get("/image/{id}", imageId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(content().bytes(imageBytes));

        verify(imageService, times(1)).read(imageId);
    }

    //todo not good что можно передать ничего, что будет занимать место в БД
    void testCreateImageFromDto_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given - пустой DTO
        ImageDto emptyDto = ImageDto.builder().build();

        // When & Then
        mockMvc.perform(post("/image")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyDto)))
                .andExpect(status().isBadRequest());

        verify(imageService, never()).createFromDto(any());
    }

    //TODO нет обработчика ошибок если изображения не существует
    void testReadImageById_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        // Given
        Long nonExistingId = 999L;
        when(imageService.read(nonExistingId)).thenThrow(new RuntimeException("Image not found"));

        // When & Then
        mockMvc.perform(get("/image/{id}", nonExistingId))
                .andExpect(status().isNotFound());

        verify(imageService, times(1)).read(nonExistingId);
    }
}