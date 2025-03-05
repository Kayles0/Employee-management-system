package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.entity.Person;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T15:56:41+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class JwtPersonMapperImpl implements JwtPersonMapper {

    @Override
    public JwtPerson toJwtPerson(Person person) {
        if ( person == null ) {
            return null;
        }

        JwtPerson.JwtPersonBuilder jwtPerson = JwtPerson.builder();

        jwtPerson.id( person.getId() );
        jwtPerson.login( person.getLogin() );
        jwtPerson.password( person.getPassword() );
        if ( person.getIsDeleted() != null ) {
            jwtPerson.isDeleted( person.getIsDeleted() );
        }
        List<SimpleGrantedAuthority> list = person.getAuthorities();
        if ( list != null ) {
            jwtPerson.authorities( new ArrayList<GrantedAuthority>( list ) );
        }

        return jwtPerson.build();
    }

    @Override
    public Person toPerson(JwtPerson jwtPerson) {
        if ( jwtPerson == null ) {
            return null;
        }

        Person.PersonBuilder<?, ?> person = Person.builder();

        person.id( jwtPerson.getId() );
        person.login( jwtPerson.getLogin() );
        person.password( jwtPerson.getPassword() );

        return person.build();
    }
}
