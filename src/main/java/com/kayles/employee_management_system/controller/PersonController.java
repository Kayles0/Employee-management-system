package com.kayles.employee_management_system.controller;

import com.kayles.employee_management_system.dto.PersonDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Контроллер пользователя")
public interface PersonController {
    @Operation(summary = "Получение пользователя")
    ResponseEntity<PersonDto> read(Long id);

    @Operation(summary = "Обновление пользователя")
    ResponseEntity<Void> update(PersonDto personDto);

    @Operation(summary = "Удаление пользователя")
    ResponseEntity<Void> delete();
}
