package com.kayles.employee_management_system.Service.impl;

import com.kayles.employee_management_system.Service.ImageService;
import com.kayles.employee_management_system.Service.PersonService;
import com.kayles.employee_management_system.Service.security.JwtAuthorizationService;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.RoleDto;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Image;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.entity.Role;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.mapper.PersonMapper;
import com.kayles.employee_management_system.repository.GroupRepository;
import com.kayles.employee_management_system.repository.ImageRepository;
import com.kayles.employee_management_system.repository.PersonRepository;
import com.kayles.employee_management_system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final RoleRepository roleRepository;
    private final JwtAuthorizationService jwtAuthorizationService;
    private final ImageRepository imageRepository;
    private final GroupRepository groupRepository;

    @Override
    public PersonDto read(Long id) {
        logger.info("Read person id: {}", id);
        Person person = personRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Person not found"));
        if (person.getIsDeleted()) {
            throw new EntityNotFoundException("Person is deleted");
        }
        return personMapper.toDto(person);
    }

    @Override
    public PersonDto[] getAllPersons() {
        logger.info("Read all persons");
        List<Person> persons = personRepository.findAllNotDeleted()
                .orElseThrow(() -> new EntityNotFoundException("Persons not found"));
        return persons.stream().map(personMapper::toDto).toArray(PersonDto[]::new);
    }

    @Override
    public PersonDto findMe() {
        logger.info("Find me person");
        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        Person person = personRepository.findById(jwtPerson.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return personMapper.toDto(person);
    }

    @Override
    public PersonDto update(PersonDto dto) {
        logger.info("Update person id: {}", dto.getId());

        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        dto.setId(jwtPerson.getId());

        Person newPerson = personMapper.toEntity(dto);
        Person exPerson = personRepository.findById(jwtPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        personMapper.update(exPerson, newPerson);
        Person savedPerson = personRepository.save(exPerson);

        return personMapper.toDto(savedPerson);
    }

    @Override
    public PersonDto updateById(PersonDto dto, Long id) {
        logger.info("Update person id: {}", id);

        dto.setId(id);

        Person newPerson = personMapper.toEntity(dto);
        Person exPerson = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        personMapper.update(exPerson, newPerson);
        Person savedPerson = personRepository.save(exPerson);

        return personMapper.toDto(savedPerson);
    }

    @Override
    public void delete(Long id) {
        logger.info("Delete person id: {}", id);
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
        return personRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public PersonDto updatePersonRoleById(Long id, RoleDto dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findByName(dto.getName())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        person.setRole(role);
        personRepository.save(person);

        return personMapper.toDto(person);
    }

    @Override
    public void setImageById(Long id) {
        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        Person person = personRepository.findPersonByIdAndIsNotDeleted(jwtPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Image image = imageRepository.findByIdAndIsNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException("Image not found"));
        person.setImage(image);
        personRepository.save(person);
    }

    @Override
    public void updateImage(MultipartFile file) {
        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        Person person = personRepository.findPersonByIdAndIsNotDeleted(jwtPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        try {
            Image image = Image.builder()
                    .image(file.getBytes())
                    .isDeleted(false)
                    .build();
            image = imageRepository.save(image);
            person.setImage(image);
            personRepository.save(person);
        } catch (IOException e) {
            throw new EntityNotFoundException("Error updating image file");
        }
    }

    @Override
    public void updateImage(Long id, MultipartFile file) {
        Person person = personRepository.findPersonByIdAndIsNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        try {
            Image image = Image.builder()
                    .image(file.getBytes())
                    .isDeleted(false)
                    .build();
            image = imageRepository.save(image);
            person.setImage(image);
            personRepository.save(person);
        } catch (IOException e) {
            throw new EntityNotFoundException("Error updating image file");
        }
    }

    @Override
    public void deleteImage() {
        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        Person person = personRepository.findPersonByIdAndIsNotDeleted(jwtPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        person.setImage(null);
        personRepository.save(person);
    }

    @Override
    public void addGroupByName(String groupName) {
        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        Person person = personRepository.findById(jwtPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        if  (!person.getGroupList().contains(group)) {
            person.getGroupList().add(group);
            personRepository.save(person);
            group.getPersons().add(person);
            groupRepository.save(group);
        }
    }

    @Override
    public void deleteFromGroupByName(String groupName) {
        JwtPerson jwtPerson = jwtAuthorizationService.extractJwtPerson();
        Person person = personRepository.findById(jwtPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        person.getGroupList().remove(group);
        personMapper.toDto(personRepository.save(person));
        group.getPersons().remove(person);
        groupRepository.save(group);
    }

    @Override
    public void addGroupByName(String groupName, Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        if (!person.getGroupList().contains(group)) {
            person.getGroupList().add(group);
            personRepository.save(person);
            group.getPersons().add(person);
            groupRepository.save(group);
        }
    }

    @Override
    public void deleteFromGroupByName(String groupName, Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        person.getGroupList().remove(group);
        PersonDto personDto = personMapper.toDto(personRepository.save(person));
        group.getPersons().remove(person);
        groupRepository.save(group);
    }

}

