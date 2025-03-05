package com.kayles.employee_management_system.Service.impl;

import com.kayles.employee_management_system.Service.PersonRegisterService;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.entity.Role;
import com.kayles.employee_management_system.enums.RoleEnum;
import com.kayles.employee_management_system.repository.PersonRepository;
import com.kayles.employee_management_system.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PersonRegisterServiceImpl implements PersonRegisterService {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    @Override
    public Person register(Person person) {
        Role rolePerson = roleRepository.findByName(RoleEnum.ROLE_USER);

        person.setRole(rolePerson);

        Person registerPerson = personRepository.save(person);

        log.info("user {} successfully registered", registerPerson);

        return registerPerson;
    }
}
