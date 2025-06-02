package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.GroupShortDto;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Person;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = GroupShortMapper.class)
public interface PersonMapper {
    @Mapping(target = "image", ignore = true)
    Person toEntity(PersonDto dto);

    @Mapping(target = "imageId", source = "image.id")
    @Mapping(target = "groups", source = "groupList")
    PersonDto toDto(Person entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    // MappingTarger необяз
    void update(@MappingTarget Person exPerson, Person newPerson);

    default List<GroupShortDto> mapGroupToDto(List<Group> groupList) {
        if (groupList == null) return List.of();
        return groupList.stream()
                .map(this::mapGroupToDto)
                .collect(Collectors.toList());
    }

    default GroupShortDto mapGroupToDto(Group group) {
        GroupShortMapper mapper = Mappers.getMapper(GroupShortMapper.class);
        return mapper.toDto(group);
    }
}
