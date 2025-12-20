package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.RoleDto;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Image;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.entity.Role;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-15T19:05:35+0300",
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
        person.isDeleted( dto.getIsDeleted() );
        person.login( dto.getLogin() );
        person.firstName( dto.getFirstName() );
        person.lastName( dto.getLastName() );
        person.gender( dto.getGender() );
        person.email( dto.getEmail() );
        person.role( roleDtoToRole( dto.getRole() ) );
        person.status( dto.getStatus() );
        person.department( dto.getDepartment() );

        return person.build();
    }

    @Override
    public PersonDto toDto(Person entity) {
        if ( entity == null ) {
            return null;
        }

        PersonDto.PersonDtoBuilder personDto = PersonDto.builder();

        personDto.imageId( entityImageId( entity ) );
        personDto.groups( mapGroupToDto( entity.getGroupList() ) );
        personDto.id( entity.getId() );
        personDto.login( entity.getLogin() );
        personDto.gender( entity.getGender() );
        personDto.firstName( entity.getFirstName() );
        personDto.lastName( entity.getLastName() );
        personDto.email( entity.getEmail() );
        personDto.role( roleToRoleDto( entity.getRole() ) );
        personDto.status( entity.getStatus() );
        personDto.department( entity.getDepartment() );
        personDto.isDeleted( entity.getIsDeleted() );

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

    protected Role roleDtoToRole(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        Role.RoleBuilder<?, ?> role = Role.builder();

        role.id( roleDto.getId() );
        role.name( roleDto.getName() );

        return role.build();
    }

    private Long entityImageId(Person person) {
        if ( person == null ) {
            return null;
        }
        Image image = person.getImage();
        if ( image == null ) {
            return null;
        }
        Long id = image.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected RoleDto roleToRoleDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto.RoleDtoBuilder roleDto = RoleDto.builder();

        roleDto.id( role.getId() );
        roleDto.name( role.getName() );

        return roleDto.build();
    }
}
