package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Person;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T19:51:29+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public Person toEntity(PersonDto dto) {
        if ( dto == null ) {
            return null;
        }

        Person.PersonBuilder<?, ?> person = Person.builder();

        person.id( (long) dto.getId() );
        person.login( dto.getLogin() );
        person.firstName( dto.getFirstName() );
        person.lastName( dto.getLastName() );
        person.gender( dto.getGender() );
        person.email( dto.getEmail() );
        person.role( dto.getRole() );
        person.status( dto.getStatus() );
        person.department( dto.getDepartment() );
        List<Group> list = dto.getGroupList();
        if ( list != null ) {
            person.groupList( new ArrayList<Group>( list ) );
        }
        person.image( dto.getImage() );

        return person.build();
    }

    @Override
    public PersonDto toDto(Person entity) {
        if ( entity == null ) {
            return null;
        }

        PersonDto.PersonDtoBuilder personDto = PersonDto.builder();

        if ( entity.getId() != null ) {
            personDto.id( entity.getId().intValue() );
        }
        personDto.login( entity.getLogin() );
        personDto.gender( entity.getGender() );
        personDto.firstName( entity.getFirstName() );
        personDto.lastName( entity.getLastName() );
        personDto.email( entity.getEmail() );
        personDto.role( entity.getRole() );
        personDto.status( entity.getStatus() );
        personDto.department( entity.getDepartment() );
        personDto.image( entity.getImage() );
        List<Group> list = entity.getGroupList();
        if ( list != null ) {
            personDto.groupList( new ArrayList<Group>( list ) );
        }

        return personDto.build();
    }
}
