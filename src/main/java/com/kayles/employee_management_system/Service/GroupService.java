package com.kayles.employee_management_system.Service;

import com.kayles.employee_management_system.dto.GroupDto;
import org.springframework.stereotype.Service;

@Service
public interface GroupService {
    GroupDto[] readAllGroups();

    GroupDto createGroup(GroupDto groupDto);

    GroupDto findById(Long id);

    void addToGroupById(Long id, Long personId);
}
