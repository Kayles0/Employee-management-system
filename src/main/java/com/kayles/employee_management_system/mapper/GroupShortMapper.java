package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.GroupShortDto;
import com.kayles.employee_management_system.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupShortMapper {
    Group toEntity(GroupShortDto dto);

    GroupShortDto toDto(Group entity);
}
