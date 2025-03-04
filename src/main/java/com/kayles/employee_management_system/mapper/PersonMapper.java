package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.entity.Person;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonDto dto);

    PersonDto toDto(Person entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Person exPerson, Person newPerson);
}
