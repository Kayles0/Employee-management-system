package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.GroupDto;
import com.kayles.employee_management_system.dto.PersonShortDto;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PersonShortMapper.class)
public interface GroupMapper {
    Group toEntity(GroupDto dto);

    @Mapping(target = "persons", source = "persons")
    GroupDto toDto(Group entity);

    default List<PersonShortDto> mapPersonToDto(List<Person> persons) {
        if (persons == null) return Collections.emptyList();
        return persons.stream()
                .map(this::mapPersonToDto)
                .collect(Collectors.toList());
    }

    default PersonShortDto mapPersonToDto(Person person) {
        PersonShortMapper mapper = Mappers.getMapper(PersonShortMapper.class);
        return mapper.toDto(person);
    }

}


