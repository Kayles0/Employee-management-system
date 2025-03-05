package com.kayles.employee_management_system.Service.security;

import com.kayles.employee_management_system.Service.PersonService;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.mapper.JwtPersonMapper;
import com.kayles.employee_management_system.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonDetailService implements UserDetailsService {
    private final PersonService personService;
    private final JwtPersonMapper jwtPersonMapper;

    @Override
    public JwtPerson loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = personService.findByLogin(login);
        if (Objects.isNull(person)) {
            throw  new EntityNotFoundException("Person with login " + login + " not found");
        }
        return buildUserDetails(person);
    }

    private JwtPerson buildUserDetails(Person person) {
        return jwtPersonMapper.toJwtPerson(person);
    }
}
