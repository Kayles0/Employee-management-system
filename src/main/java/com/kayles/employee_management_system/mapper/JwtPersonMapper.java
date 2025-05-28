package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtPersonMapper {

    JwtPerson toJwtPerson(Person person);

    Person toPerson(JwtPerson jwtPerson);
}
