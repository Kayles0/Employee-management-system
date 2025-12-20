package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.GroupShortDto;
import com.kayles.employee_management_system.entity.Group;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-15T19:05:35+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class GroupShortMapperImpl implements GroupShortMapper {

    @Override
    public Group toEntity(GroupShortDto dto) {
        if ( dto == null ) {
            return null;
        }

        Group.GroupBuilder<?, ?> group = Group.builder();

        group.name( dto.getName() );

        return group.build();
    }

    @Override
    public GroupShortDto toDto(Group entity) {
        if ( entity == null ) {
            return null;
        }

        GroupShortDto.GroupShortDtoBuilder groupShortDto = GroupShortDto.builder();

        groupShortDto.name( entity.getName() );

        return groupShortDto.build();
    }
}
