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
    date = "2025-03-05T01:44:59+0300",
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

        person.id( dto.getId() );
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

        personDto.id( entity.getId() );
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

    @Override
    public void update(Person exPerson, Person newPerson) {
        if ( newPerson == null ) {
            return;
        }

        if ( newPerson.getIsDeleted() != null ) {
            exPerson.setIsDeleted( newPerson.getIsDeleted() );
        }
        if ( newPerson.getLogin() != null ) {
            exPerson.setLogin( newPerson.getLogin() );
        }
        if ( newPerson.getFirstName() != null ) {
            exPerson.setFirstName( newPerson.getFirstName() );
        }
        if ( newPerson.getLastName() != null ) {
            exPerson.setLastName( newPerson.getLastName() );
        }
        if ( newPerson.getGender() != null ) {
            exPerson.setGender( newPerson.getGender() );
        }
        if ( newPerson.getEmail() != null ) {
            exPerson.setEmail( newPerson.getEmail() );
        }
        if ( newPerson.getStatus() != null ) {
            exPerson.setStatus( newPerson.getStatus() );
        }
        if ( newPerson.getDepartment() != null ) {
            exPerson.setDepartment( newPerson.getDepartment() );
        }
        if ( exPerson.getGroupList() != null ) {
            List<Group> list = newPerson.getGroupList();
            if ( list != null ) {
                exPerson.getGroupList().clear();
                exPerson.getGroupList().addAll( list );
            }
        }
        else {
            List<Group> list = newPerson.getGroupList();
            if ( list != null ) {
                exPerson.setGroupList( new ArrayList<Group>( list ) );
            }
        }
        if ( newPerson.getImage() != null ) {
            exPerson.setImage( newPerson.getImage() );
        }
    }
}
