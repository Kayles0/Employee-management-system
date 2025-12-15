package com.kayles.employee_management_system.test.controller;

import com.kayles.employee_management_system.controller.impl.GroupControllerImpl;
import com.kayles.employee_management_system.dto.GroupDto;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.PersonShortDto;
import com.kayles.employee_management_system.service.GroupService;
import com.kayles.employee_management_system.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupControllerImplTest {

    @Mock
    private GroupService groupService;

    @Mock
    private PersonService personService;

    @InjectMocks
    private GroupControllerImpl groupController;

    private GroupDto testGroupDto;
    private PersonShortDto testPersonDto;

    @BeforeEach
    void setUp() {
        testPersonDto = PersonShortDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        testGroupDto = GroupDto.builder()
                .id(1L)
                .name("Development Team")
                .persons(Collections.singletonList(testPersonDto))
                .build();
    }

    @Test
    void groupList_ShouldReturnAllGroups() {
        // Arrange
        GroupDto[] expectedGroups = new GroupDto[]{testGroupDto};
        when(groupService.readAllGroups()).thenReturn(expectedGroups);

        // Act
        ResponseEntity<GroupDto[]> response = groupController.groupList();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(expectedGroups, response.getBody());
        verify(groupService, times(1)).readAllGroups();
    }

    @Test
    void createGroup_ShouldCreateAndReturnGroup() {
        // Arrange
        when(groupService.createGroup(any(GroupDto.class))).thenReturn(testGroupDto);

        // Act
        ResponseEntity<GroupDto> response = groupController.createGroup(testGroupDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testGroupDto, response.getBody());
        verify(groupService, times(1)).createGroup(testGroupDto);
    }

    @Test
    void createGroup_WithNullDto_ShouldPassNullToService() {
        // Arrange
        when(groupService.createGroup(null)).thenReturn(null);

        // Act
        ResponseEntity<GroupDto> response = groupController.createGroup(null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(groupService, times(1)).createGroup(null);
    }

    @Test
    void findGroupById_ShouldReturnGroup() {
        // Arrange
        Long groupId = 1L;
        when(groupService.findById(groupId)).thenReturn(testGroupDto);

        // Act
        ResponseEntity<GroupDto> response = groupController.findGroupById(groupId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testGroupDto, response.getBody());
        verify(groupService, times(1)).findById(groupId);
    }

    @Test
    void findGroupById_WithInvalidId_ShouldReturnNull() {
        // Arrange
        Long invalidId = 999L;
        when(groupService.findById(invalidId)).thenReturn(null);

        // Act
        ResponseEntity<GroupDto> response = groupController.findGroupById(invalidId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(groupService, times(1)).findById(invalidId);
    }

    @Test
    void addMeToGroupById_ShouldAddCurrentUser() {
        // Arrange
        Long groupId = 1L;
        Long userId = 100L;

        PersonDto currentUser = PersonDto.builder()
                .id(userId)
                .firstName("Current")
                .lastName("User")
                .build();

        when(personService.findMe()).thenReturn(currentUser);
        doNothing().when(groupService).addToGroupById(groupId, userId);

        // Act
        ResponseEntity<Void> response = groupController.addMeToGroupById(groupId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(personService, times(1)).findMe();
        verify(groupService, times(1)).addToGroupById(groupId, userId);
    }

    @Test
    void addMeToGroupById_WhenUserNotFound_ShouldThrowException() {
        // Arrange
        Long groupId = 1L;
        when(personService.findMe()).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            groupController.addMeToGroupById(groupId);
        });
    }
}