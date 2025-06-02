package com.kayles.employee_management_system.controller.impl;

import com.kayles.employee_management_system.Service.GroupService;
import com.kayles.employee_management_system.Service.PersonService;
import com.kayles.employee_management_system.controller.GroupController;
import com.kayles.employee_management_system.dto.GroupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupControllerImpl implements GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupControllerImpl.class);

    private final GroupService groupService;
    private final PersonService personService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<GroupDto[]> groupList() {
        return ResponseEntity.ok(groupService.readAllGroups());
    }

    @Override
    @PutMapping()
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        logger.info("Create Group");
        return ResponseEntity.ok(groupService.createGroup(groupDto));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> findGroupById(@PathVariable Long id) {
        logger.info("Find Group by ID {}", id);
        return ResponseEntity.ok(groupService.findById(id));
    }

    @Override
    @GetMapping("/addMe/{groupId}")
    public ResponseEntity<Void> addMeToGroupById(@PathVariable Long groupId) {
        Long userId = personService.findMe().getId();
        logger.info("Add me (id: {}) to Group by ID {}", userId, groupId);
        groupService.addToGroupById(groupId, userId);
        return ResponseEntity.ok().build();
    }
}

