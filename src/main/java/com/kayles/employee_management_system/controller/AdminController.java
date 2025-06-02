package com.kayles.employee_management_system.controller;

import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Контроллер админа")
public interface AdminController {
    @Operation(summary = "Изменение профиля")
    ResponseEntity<PersonDto> updatePersonById(PersonDto dto);

    @Operation(summary = "Удаление профиля по id")
    ResponseEntity<Void> deletePersonById(Long id);

    @Operation(summary = "Присвоение ролей пользователю по id")
    ResponseEntity<PersonDto> setRoleById(Long id, RoleDto roleDto);

    @Operation(summary = "Перезапись изображения")
    ResponseEntity<Void> recreateByIdFromFile(Long id, MultipartFile file);

    @Operation(summary = "Обновление изображения пользователя")
    ResponseEntity<Void> recreateImageForPersonById(Long userId, MultipartFile file);

    @Operation(summary = "Удаление изображения")
    ResponseEntity<Void> deleteImageById(Long id);

    @Operation(summary = "Добавления пользователя по id в группу по name")
    ResponseEntity<PersonDto> addPersonToGroup(String groupName, Long userId);

    @Operation(summary = "Удаление пользователя по id из группы по name")
    ResponseEntity<PersonDto> deletePersonFromGroup(String groupName, Long userId);

    @Operation(summary = "Обновление пользователя по id")
    ResponseEntity<PersonDto> updatePersonById(Long id, PersonDto dto);
}
