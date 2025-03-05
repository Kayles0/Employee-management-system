package com.kayles.employee_management_system.controller;

import com.kayles.employee_management_system.dto.ImageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Контроллер изображения")
public interface ImageController {
    @Operation(summary = "Создание изображения")
    ResponseEntity<Void> createImage(ImageDto dto);

    @Operation(summary = "Удаление изображения")
    ResponseEntity<Void> delete(Long id);
}
