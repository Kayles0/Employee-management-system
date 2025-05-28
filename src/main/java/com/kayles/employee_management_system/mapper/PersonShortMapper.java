package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.PersonShortDto;
import com.kayles.employee_management_system.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonShortMapper {
    @Mapping(target = "image", ignore = true)
    Person toEntity(PersonShortDto dto);

    @Mapping(target = "imageId", source = "image.id")
    PersonShortDto toDto(Person entity);

}
