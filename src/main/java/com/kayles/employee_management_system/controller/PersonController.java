package com.kayles.employee_management_system.controller;

import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.dto.PersonDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Контроллер пользователя")
public interface PersonController {
    @Operation(summary = "Получение всех пользователей")
    ResponseEntity<PersonDto[]> personList();

    @Operation(summary = "Получение пользователя")
    ResponseEntity<PersonDto> readById(Long id);

    @Operation(summary = "Обновление пользователя")
    ResponseEntity<PersonDto> updateMe(PersonDto personDto);

    @Operation(summary = "Удаление своего профиля")
    ResponseEntity<Void> deleteMe();

    @Operation(summary = "Получение данных своего профиля")
    ResponseEntity<PersonDto> getMe();

    @Operation(summary = "Обновление картинки")
    ResponseEntity<ImageDto> updateImage(@RequestParam("file") MultipartFile file);

    @Operation(summary = "Удалени своего изображения")
    ResponseEntity<Void> deleteImage();
}
