package com.kayles.employee_management_system.Service;

import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.RoleDto;
import com.kayles.employee_management_system.entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PersonService {
    PersonDto findMe();

    PersonDto[] getAllPersons();

    PersonDto read(Long id);

    PersonDto update(PersonDto personDto);

    void delete(Long id);

    void delete();

    Person findByLogin(String login);

    PersonDto updatePersonRoleById(Long id, RoleDto roleDto);

    void deleteImage();

    void setImageById(Long id);

    void addGroupByName(String groupName);

    void addGroupByName(String groupName, Long id);

    void deleteFromGroupByName(String groupName);

    void deleteFromGroupByName(String groupName, Long id);
}
