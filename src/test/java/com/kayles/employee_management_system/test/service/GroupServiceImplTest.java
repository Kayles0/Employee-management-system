package com.kayles.employee_management_system.test.service;

import com.kayles.employee_management_system.dto.GroupDto;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.mapper.GroupMapper;
import com.kayles.employee_management_system.repository.GroupRepository;
import com.kayles.employee_management_system.repository.PersonRepository;
import com.kayles.employee_management_system.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    private Group testGroup;
    private GroupDto testGroupDto;
    private Person testPerson;
    private List<Person> testPersons;

    @BeforeEach
    void setUp() {
        testPerson = Person.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .build();

        testPersons = new ArrayList<>();
        testPersons.add(testPerson);

        testGroup = Group.builder()
                .id(1L)
                .name("Development Team")
                .persons(testPersons)
                .isDeleted(false)
                .build();

        testGroupDto = GroupDto.builder()
                .id(1L)
                .name("Development Team")
                .persons(new ArrayList<>()) // Маппер преобразует отдельно
                .build();
    }

    @Test
    void readAllGroups_ShouldReturnAllGroups() {
        // Arrange
        List<Group> groups = Arrays.asList(testGroup);
        GroupDto[] expectedDtos = new GroupDto[]{testGroupDto};

        when(groupRepository.findAllWithPersons()).thenReturn(groups);
        when(groupMapper.toDto(testGroup)).thenReturn(testGroupDto);

        // Act
        GroupDto[] result = groupService.readAllGroups();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.length);
        assertArrayEquals(expectedDtos, result);
        verify(groupRepository, times(1)).findAllWithPersons();
        verify(groupMapper, times(1)).toDto(testGroup);
    }

    @Test
    void readAllGroups_WhenNoGroups_ShouldReturnEmptyArray() {
        // Arrange
        when(groupRepository.findAllWithPersons()).thenReturn(new ArrayList<>());

        // Act
        GroupDto[] result = groupService.readAllGroups();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.length);
        verify(groupRepository, times(1)).findAllWithPersons();
        verify(groupMapper, never()).toDto(any());
    }

    @Test
    void readAllGroups_ShouldCallFindAllWithPersons() {
        // Arrange
        when(groupRepository.findAllWithPersons()).thenReturn(new ArrayList<>());

        // Act
        groupService.readAllGroups();

        // Assert
        verify(groupRepository).findAllWithPersons();
        verify(groupRepository, never()).findAll();
    }

    @Test
    void createGroup_ShouldCreateAndReturnGroup() {
        // Arrange
        GroupDto inputDto = GroupDto.builder()
                .name("New Group")
                .persons(new ArrayList<>())
                .build();

        Group newGroup = Group.builder()
                .id(null)
                .name("New Group")
                .persons(new ArrayList<>())
                .isDeleted(false)
                .build();

        Group savedGroup = Group.builder()
                .id(2L)
                .name("New Group")
                .persons(new ArrayList<>())
                .isDeleted(false)
                .build();

        GroupDto expectedDto = GroupDto.builder()
                .id(2L)
                .name("New Group")
                .persons(new ArrayList<>())
                .build();

        when(groupRepository.save(any(Group.class))).thenReturn(savedGroup);
        when(groupMapper.toDto(savedGroup)).thenReturn(expectedDto);

        // Act
        GroupDto result = groupService.createGroup(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("New Group", result.getName());
        assertEquals(2L, result.getId());
        verify(groupRepository, times(1)).save(argThat(group ->
                group.getName().equals("New Group") &&
                        !group.getIsDeleted() &&
                        group.getPersons().isEmpty()
        ));
        verify(groupMapper, times(1)).toDto(savedGroup);
    }

    //TODO НАЙДЕНА ОШИБКА
    void createGroup_WithNullName_ShouldThrowException() {
        // Arrange
        GroupDto inputDto = GroupDto.builder()
                .name(null)
                .persons(new ArrayList<>())
                .build();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            groupService.createGroup(inputDto);
        });
    }

    @Test
    void createGroup_WithEmptyName_ShouldWork() {
        // Arrange
        GroupDto inputDto = GroupDto.builder()
                .name("")
                .persons(new ArrayList<>())
                .build();

        Group savedGroup = Group.builder()
                .id(1L)
                .name("")
                .persons(new ArrayList<>())
                .isDeleted(false)
                .build();

        GroupDto expectedDto = GroupDto.builder()
                .id(1L)
                .name("")
                .persons(new ArrayList<>())
                .build();

        when(groupRepository.save(any(Group.class))).thenReturn(savedGroup);
        when(groupMapper.toDto(savedGroup)).thenReturn(expectedDto);

        // Act
        GroupDto result = groupService.createGroup(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("", result.getName());
    }

    @Test
    void createGroup_ShouldSetIsDeletedToFalse() {
        // Arrange
        GroupDto inputDto = GroupDto.builder()
                .name("Test Group")
                .persons(new ArrayList<>())
                .build();

        when(groupRepository.save(any(Group.class))).thenAnswer(invocation -> {
            Group group = invocation.getArgument(0);
            group.setId(1L);
            return group;
        });
        when(groupMapper.toDto(any(Group.class))).thenReturn(GroupDto.builder()
                .id(1L)
                .name("Test Group")
                .persons(new ArrayList<>())
                .build());

        // Act
        groupService.createGroup(inputDto);

        // Assert
        verify(groupRepository).save(argThat(group ->
                group.getIsDeleted() != null && !group.getIsDeleted()
        ));
    }

    @Test
    void createGroup_ShouldInitializeEmptyPersonsList() {
        // Arrange
        GroupDto inputDto = GroupDto.builder()
                .name("Test Group")
                .persons(null) // DTO может иметь null
                .build();

        when(groupRepository.save(any(Group.class))).thenAnswer(invocation -> {
            Group group = invocation.getArgument(0);
            group.setId(1L);
            return group;
        });
        when(groupMapper.toDto(any(Group.class))).thenReturn(GroupDto.builder()
                .id(1L)
                .name("Test Group")
                .persons(new ArrayList<>())
                .build());

        // Act
        groupService.createGroup(inputDto);

        // Assert
        verify(groupRepository).save(argThat(group ->
                group.getPersons() != null && group.getPersons().isEmpty()
        ));
    }

    @Test
    void findById_WithValidId_ShouldReturnGroupDto() {
        // Arrange
        Long groupId = 1L;
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(testGroup));
        when(groupMapper.toDto(testGroup)).thenReturn(testGroupDto);

        // Act
        GroupDto result = groupService.findById(groupId);

        // Assert
        assertNotNull(result);
        assertEquals(testGroupDto, result);
        verify(groupRepository, times(1)).findById(groupId);
        verify(groupMapper, times(1)).toDto(testGroup);
    }

    @Test
    void findById_WithNonExistentId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long nonExistentId = 999L;
        when(groupRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            groupService.findById(nonExistentId);
        });

        assertEquals("Group not found", exception.getMessage());
        verify(groupRepository, times(1)).findById(nonExistentId);
        verify(groupMapper, never()).toDto(any());
    }

    @Test
    void findById_WithNullId_ShouldThrowEntityNotFoundException() {
        // Arrange
        when(groupRepository.findById(null)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            groupService.findById(null);
        });

        assertEquals("Group not found", exception.getMessage());
    }

    @Test
    void addToGroupById_ShouldAddPersonToGroup() {
        // Arrange
        Long groupId = 1L;
        Long personId = 2L;

        Person newPerson = Person.builder()
                .id(personId)
                .firstName("Jane")
                .lastName("Smith")
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .build();

        Group group = Group.builder()
                .id(groupId)
                .name("Team A")
                .persons(new ArrayList<>())
                .isDeleted(false)
                .build();

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(personRepository.findPersonByIdAndIsNotDeleted(personId)).thenReturn(Optional.of(newPerson));

        // Act
        groupService.addToGroupById(groupId, personId);

        // Assert
        assertTrue(group.getPersons().contains(newPerson));
        assertTrue(newPerson.getGroupList().contains(group));
        verify(groupRepository, times(1)).save(group);
        verify(personRepository, times(1)).save(newPerson);
    }

    @Test
    void addToGroupById_WithNonExistentGroup_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long groupId = 999L;
        Long personId = 1L;
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            groupService.addToGroupById(groupId, personId);
        });

        assertEquals("Group not found", exception.getMessage());
        verify(groupRepository, times(1)).findById(groupId);
        verify(personRepository, never()).findPersonByIdAndIsNotDeleted(any());
        verify(groupRepository, never()).save(any());
    }

    @Test
    void addToGroupById_WithNonExistentPerson_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long groupId = 1L;
        Long personId = 999L;
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(testGroup));
        when(personRepository.findPersonByIdAndIsNotDeleted(personId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            groupService.addToGroupById(groupId, personId);
        });

        assertEquals("Person not found", exception.getMessage());
        verify(personRepository, times(1)).findPersonByIdAndIsNotDeleted(personId);
        verify(groupRepository, never()).save(any());
    }

    @Test
    void addToGroupById_WithDeletedPerson_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long groupId = 1L;
        Long personId = 2L;
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(testGroup));
        when(personRepository.findPersonByIdAndIsNotDeleted(personId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            groupService.addToGroupById(groupId, personId);
        });
    }

    @Test
    void addToGroupById_WhenPersonAlreadyInGroup_ShouldNotAddAgain() {
        // Arrange
        Long groupId = 1L;
        Long personId = 1L;

        // Person уже в группе
        testGroup.getPersons().add(testPerson);
        testPerson.getGroupList().add(testGroup);

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(testGroup));
        when(personRepository.findPersonByIdAndIsNotDeleted(personId)).thenReturn(Optional.of(testPerson));

        // Act
        groupService.addToGroupById(groupId, personId);

        // Assert
        // Проверяем, что save не вызывался, так как человек уже в группе
        verify(groupRepository, never()).save(any());
        verify(personRepository, never()).save(any());
    }

    @Test
    void addToGroupById_ShouldUseFindPersonByIdAndIsNotDeleted() {
        // Arrange
        Long groupId = 1L;
        Long personId = 2L;

        Person person = Person.builder()
                .id(personId)
                .firstName("Test")
                .lastName("User")
                .groupList(new ArrayList<>())
                .build();

        Group group = Group.builder()
                .id(groupId)
                .name("Test Group")
                .persons(new ArrayList<>())
                .build();

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(personRepository.findPersonByIdAndIsNotDeleted(personId)).thenReturn(Optional.of(person));

        // Act
        groupService.addToGroupById(groupId, personId);

        // Assert
        verify(personRepository).findPersonByIdAndIsNotDeleted(personId);
        verify(personRepository, never()).findById(personId);
    }

    @Test
    void addToGroupById_ShouldAddGroupToPersonGroupList() {
        // Arrange
        Long groupId = 1L;
        Long personId = 2L;

        Person person = Person.builder()
                .id(personId)
                .firstName("Alice")
                .lastName("Johnson")
                .groupList(new ArrayList<>()) // Пустой список групп
                .build();

        Group group = Group.builder()
                .id(groupId)
                .name("Marketing")
                .persons(new ArrayList<>())
                .build();

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(personRepository.findPersonByIdAndIsNotDeleted(personId)).thenReturn(Optional.of(person));

        // Act
        groupService.addToGroupById(groupId, personId);

        // Assert
        assertTrue(person.getGroupList().contains(group));
        verify(personRepository).save(person);
    }

    @Test
    void addToGroupById_WithNullGroupId_ShouldThrowException() {
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            groupService.addToGroupById(null, 1L);
        });
    }

    @Test
    void addToGroupById_WithNullPersonId_ShouldThrowException() {
        // Arrange
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        when(personRepository.findPersonByIdAndIsNotDeleted(null)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            groupService.addToGroupById(1L, null);
        });
    }

    @Test
    void createGroup_ShouldIgnorePersonsFromDto() {
        // Arrange
        List<Person> personsInDto = Arrays.asList(testPerson);
        GroupDto inputDto = GroupDto.builder()
                .name("Test Group")
                .persons(personsInDto.stream()
                        .map(p -> com.kayles.employee_management_system.dto.PersonShortDto.builder()
                                .id(p.getId())
                                .firstName(p.getFirstName())
                                .lastName(p.getLastName())
                                .build())
                        .toList())
                .build();

        when(groupRepository.save(any(Group.class))).thenAnswer(invocation -> {
            Group group = invocation.getArgument(0);
            group.setId(1L);
            return group;
        });
        when(groupMapper.toDto(any(Group.class))).thenReturn(GroupDto.builder()
                .id(1L)
                .name("Test Group")
                .persons(new ArrayList<>())
                .build());

        // Act
        groupService.createGroup(inputDto);

        // Assert
        // Группа должна создаваться с пустым списком persons, даже если в DTO есть persons
        verify(groupRepository).save(argThat(group ->
                group.getPersons() != null && group.getPersons().isEmpty()
        ));
    }
}