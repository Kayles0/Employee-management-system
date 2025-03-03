package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.entity.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    Person toEntity(PersonDto dto);

    PersonDto toDto(Person entity);
}
