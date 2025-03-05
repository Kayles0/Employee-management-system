package com.kayles.employee_management_system.Service.impl;

import com.kayles.employee_management_system.Service.PersonService;
import com.kayles.employee_management_system.Service.security.JwtAuthorizationService;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.mapper.PersonMapper;
import com.kayles.employee_management_system.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final JwtAuthorizationService jwtAuthorizationService;

    @Override
    public PersonDto read(Long id) {
        logger.info("Read person id: " + id);
        Person person = personRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User not found"));
        return personMapper.toDto(person);
    }

    @Override
    public void update(PersonDto dto) {
        logger.info("Update person id: " + dto.getId());
        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        dto.setId(jwtPerson.getId());
        Person newPerson = personMapper.toEntity(dto);
        Person exPerson = personRepository.findById(jwtPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        personMapper.update(exPerson, newPerson);
        personRepository.save(exPerson);
    }

    @Override
    public void delete(Long id) {
        logger.info("Delete person id: " + id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        person.setIsDeleted(true);
        personRepository.save(person);
    }

    @Override
    public void delete() {
        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        Person person = personRepository.findById(jwtPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        person.setIsDeleted(true);
        personRepository.save(person);
    }

    @Override
    public Person findByLogin(String login) {
        return personRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public void updatePersonRole(Long id, String role) {
        Person person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

    }
}
