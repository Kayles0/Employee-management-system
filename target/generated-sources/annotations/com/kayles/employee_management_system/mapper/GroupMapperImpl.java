package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.GroupDto;
import com.kayles.employee_management_system.dto.PersonShortDto;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Person;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-15T18:58:39+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Autowired
    private PersonShortMapper personShortMapper;

    @Override
    public Group toEntity(GroupDto dto) {
        if ( dto == null ) {
            return null;
        }

        Group.GroupBuilder<?, ?> group = Group.builder();

        group.id( dto.getId() );
        group.name( dto.getName() );
        group.persons( personShortDtoListToPersonList( dto.getPersons() ) );

        return group.build();
    }

    @Override
    public GroupDto toDto(Group entity) {
        if ( entity == null ) {
            return null;
        }

        GroupDto.GroupDtoBuilder groupDto = GroupDto.builder();

        groupDto.persons( mapPersonToDto( entity.getPersons() ) );
        groupDto.id( entity.getId() );
        groupDto.name( entity.getName() );

        return groupDto.build();
    }

    protected List<Person> personShortDtoListToPersonList(List<PersonShortDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Person> list1 = new ArrayList<Person>( list.size() );
        for ( PersonShortDto personShortDto : list ) {
            list1.add( personShortMapper.toEntity( personShortDto ) );
        }

        return list1;
    }
}
