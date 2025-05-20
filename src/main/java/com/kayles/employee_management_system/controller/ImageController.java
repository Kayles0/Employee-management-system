package com.kayles.employee_management_system.controller;

import com.kayles.employee_management_system.dto.ImageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Контроллер изображения")
public interface ImageController {
    @Operation(summary = "Создание изображения из dto")
    ResponseEntity<ImageDto> createImageFromDto(ImageDto dto);

    @Operation(summary = "Создание изображения из файла (blob)")
    ResponseEntity<ImageDto> createImageFromFile(MultipartFile file);

    @Operation(summary = "Чтение изображения по id")
    ResponseEntity<byte[]> readImageById(Long id);
}
