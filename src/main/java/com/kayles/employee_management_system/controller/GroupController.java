package com.kayles.employee_management_system.controller;

import com.kayles.employee_management_system.dto.GroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Контроллер групп")
public interface GroupController {
    @Operation(summary = "Получение всех групп")
    ResponseEntity<GroupDto[]> groupList();

    @Operation(summary = "Создание новой группы")
    ResponseEntity<GroupDto> createGroup(GroupDto groupDto);

    @Operation(summary = "Получение группы по id")
    ResponseEntity<GroupDto> findGroupById(Long id);

    @Operation(summary = "Добавить свой аккаунт в группу по id")
    ResponseEntity<Void> addMeToGroupById(Long groupId);

}
