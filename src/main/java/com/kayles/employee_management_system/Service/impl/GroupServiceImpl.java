package com.kayles.employee_management_system.Service.impl;

import com.kayles.employee_management_system.Service.GroupService;
import com.kayles.employee_management_system.dto.GroupDto;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.mapper.GroupMapper;
import com.kayles.employee_management_system.repository.GroupRepository;
import com.kayles.employee_management_system.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final PersonRepository personRepository;

    @Override
    public GroupDto[] readAllGroups() {
        List<Group> groups = groupRepository.findAllWithPersons();
        return groups.stream()
                .map(groupMapper::toDto)
                .toArray(GroupDto[]::new);
    }

    @Override
    public GroupDto createGroup(GroupDto groupDto) {
        Group group = Group.builder()
                .name(groupDto.getName())
                .isDeleted(false)
                .persons(new ArrayList<>())
                .build();
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }



    @Override
    public GroupDto findById(Long id) {
        return groupMapper.toDto(groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found")));
    }

    @Override
    public void addToGroupById(Long id, Long personId) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        Person person = personRepository.findPersonByIdAndIsNotDeleted(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        if (!group.getPersons().contains(person)) {
            group.getPersons().add(person);
            groupRepository.save(group);
            person.getGroupList().add(group);
            personRepository.save(person);
        }
    }

}
