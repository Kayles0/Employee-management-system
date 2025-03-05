package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.RoleDto;
import com.kayles.employee_management_system.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto dto);

    RoleDto toDto(Role entity);
}
