package com.kayles.employee_management_system.Service;

import com.kayles.employee_management_system.dto.PersonDto;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {
    PersonDto read(Long id);

    void update(PersonDto personDto);

    void delete(Long id);

    void delete();

    PersonDto findByLogin(String login);

    void updatePersonRole(Long id, String role);
}
