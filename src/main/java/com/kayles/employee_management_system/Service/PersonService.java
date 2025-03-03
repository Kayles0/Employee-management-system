package com.kayles.employee_management_system.Service;

import com.kayles.employee_management_system.dto.PersonDto;

public interface PersonService {
    PersonDto read(Long id);

    void delete(Long id);

    void delete();

    PersonDto findByLogin(String login);

    void updatePersonRole(Long id, String role);
}
