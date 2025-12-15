package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.PersonShortDto;
import com.kayles.employee_management_system.entity.Image;
import com.kayles.employee_management_system.entity.Person;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-15T09:04:00+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class PersonShortMapperImpl implements PersonShortMapper {

    @Override
    public Person toEntity(PersonShortDto dto) {
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
        person.status( dto.getStatus() );
        person.department( dto.getDepartment() );

        return person.build();
    }

    @Override
    public PersonShortDto toDto(Person entity) {
        if ( entity == null ) {
            return null;
        }

        PersonShortDto.PersonShortDtoBuilder personShortDto = PersonShortDto.builder();

        personShortDto.imageId( entityImageId( entity ) );
        personShortDto.id( entity.getId() );
        personShortDto.login( entity.getLogin() );
        personShortDto.gender( entity.getGender() );
        personShortDto.firstName( entity.getFirstName() );
        personShortDto.lastName( entity.getLastName() );
        personShortDto.email( entity.getEmail() );
        personShortDto.status( entity.getStatus() );
        personShortDto.department( entity.getDepartment() );
        personShortDto.isDeleted( entity.getIsDeleted() );

        return personShortDto.build();
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
}
