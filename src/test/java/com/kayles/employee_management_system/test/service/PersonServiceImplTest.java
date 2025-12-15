package com.kayles.employee_management_system.test.service;

import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.RoleDto;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.entity.Image;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.entity.Role;
import com.kayles.employee_management_system.enums.DepartmentEnum;
import com.kayles.employee_management_system.enums.GenderEnum;
import com.kayles.employee_management_system.enums.RoleEnum;
import com.kayles.employee_management_system.enums.StatusEnum;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.mapper.PersonMapper;
import com.kayles.employee_management_system.repository.GroupRepository;
import com.kayles.employee_management_system.repository.ImageRepository;
import com.kayles.employee_management_system.repository.PersonRepository;
import com.kayles.employee_management_system.repository.RoleRepository;
import com.kayles.employee_management_system.service.impl.PersonServiceImpl;
import com.kayles.employee_management_system.service.security.JwtAuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtAuthorizationService jwtAuthorizationService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person testPerson;
    private PersonDto testPersonDto;
    private JwtPerson testJwtPerson;
    private Role testRole;
    private Image testImage;
    private Group testGroup;

    @BeforeEach
    void setUp() {
        testRole = Role.builder()
                .id(1L)
                .name(RoleEnum.ROLE_ADMIN)
                .build();

        testImage = Image.builder()
                .id(1L)
                .image(new byte[]{1, 2, 3})
                .isDeleted(false)
                .build();

        testGroup = Group.builder()
                .id(1L)
                .name("Development Team")
                .persons(new ArrayList<>())
                .isDeleted(false)
                .build();

        testPerson = Person.builder()
                .id(1L)
                .login("johndoe")
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .gender(GenderEnum.MAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.SOFTWARE_DEVELOPMENT_DEPARTMENT)
                .role(testRole)
                .image(testImage)
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .password("password")
                .build();

        testPersonDto = PersonDto.builder()
                .id(1L)
                .login("johndoe")
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .gender(GenderEnum.MAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.SOFTWARE_DEVELOPMENT_DEPARTMENT)
                .role(RoleDto.builder().name(RoleEnum.ROLE_ADMIN).build())
                .imageId(1L)
                .groups(new ArrayList<>())
                .isDeleted(false)
                .build();

        testJwtPerson = JwtPerson.builder()
                .id(1L)
                .login("johndoe")
                .authorities(List.of(new SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.name())))
                .build();
    }

    @Test
    void read_WithValidId_ShouldReturnPersonDto() {
        // Arrange
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.of(testPerson));
        when(personMapper.toDto(testPerson)).thenReturn(testPersonDto);

        // Act
        PersonDto result = personService.read(personId);

        // Assert
        assertNotNull(result);
        assertEquals(testPersonDto, result);
        verify(personRepository, times(1)).findById(personId);
        verify(personMapper, times(1)).toDto(testPerson);
    }

    @Test
    void read_WithNonExistentId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long nonExistentId = 999L;
        when(personRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.read(nonExistentId);
        });

        assertEquals("Person not found", exception.getMessage());
    }

    @Test
    void read_WithDeletedPerson_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long personId = 1L;
        testPerson.setIsDeleted(true);
        when(personRepository.findById(personId)).thenReturn(Optional.of(testPerson));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.read(personId);
        });

        assertEquals("Person is deleted", exception.getMessage());
    }

    @Test
    void getAllPersons_ShouldReturnAllNotDeletedPersons() {
        // Arrange
        List<Person> persons = Arrays.asList(testPerson);
        PersonDto[] expectedDtos = new PersonDto[]{testPersonDto};

        when(personRepository.findAllNotDeleted()).thenReturn(Optional.of(persons));
        when(personMapper.toDto(testPerson)).thenReturn(testPersonDto);

        // Act
        PersonDto[] result = personService.getAllPersons();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.length);
        assertArrayEquals(expectedDtos, result);
        verify(personRepository, times(1)).findAllNotDeleted();
        verify(personMapper, times(1)).toDto(testPerson);
    }

    @Test
    void getAllPersons_WhenNoPersons_ShouldThrowEntityNotFoundException() {
        // Arrange
        when(personRepository.findAllNotDeleted()).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.getAllPersons();
        });

        assertEquals("Persons not found", exception.getMessage());
    }

    @Test
    void findMe_ShouldReturnCurrentUserDto() {
        // Arrange
        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findById(testJwtPerson.getId())).thenReturn(Optional.of(testPerson));
        when(personMapper.toDto(testPerson)).thenReturn(testPersonDto);

        // Act
        PersonDto result = personService.findMe();

        // Assert
        assertNotNull(result);
        assertEquals(testPersonDto, result);
        verify(jwtAuthorizationService, times(1)).extractJwtPerson();
        verify(personRepository, times(1)).findById(testJwtPerson.getId());
    }

    @Test
    void findMe_WhenUserNotFound_ShouldThrowEntityNotFoundException() {
        // Arrange
        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findById(testJwtPerson.getId())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.findMe();
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void update_ShouldUpdateCurrentUser() {
        // Arrange
        PersonDto updateDto = PersonDto.builder()
                .id(null) // ID будет установлен из JWT
                .login("updatedlogin")
                .firstName("Updated")
                .lastName("Name")
                .email("updated@example.com")
                .gender(GenderEnum.WOMAN)
                .status(StatusEnum.ON_VACATION)
                .department(DepartmentEnum.QUALITY_ASSURANCE_DEPARTMENT)
                .role(RoleDto.builder().name(RoleEnum.ROLE_USER).build())
                .imageId(2L)
                .groups(new ArrayList<>())
                .isDeleted(false)
                .build();

        Person updatedPerson = Person.builder()
                .id(1L)
                .login("updatedlogin")
                .firstName("Updated")
                .lastName("Name")
                .email("updated@example.com")
                .gender(GenderEnum.WOMAN)
                .status(StatusEnum.ON_VACATION)
                .department(DepartmentEnum.QUALITY_ASSURANCE_DEPARTMENT)
                .role(testRole)
                .image(testImage)
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .password("password")
                .build();

        PersonDto expectedDto = PersonDto.builder()
                .id(1L)
                .login("updatedlogin")
                .firstName("Updated")
                .lastName("Name")
                .email("updated@example.com")
                .gender(GenderEnum.WOMAN)
                .status(StatusEnum.ON_VACATION)
                .department(DepartmentEnum.QUALITY_ASSURANCE_DEPARTMENT)
                .role(RoleDto.builder().name(RoleEnum.ROLE_ADMIN).build())
                .imageId(1L)
                .groups(new ArrayList<>())
                .isDeleted(false)
                .build();

        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findById(testJwtPerson.getId())).thenReturn(Optional.of(testPerson));
        when(personMapper.toEntity(updateDto)).thenReturn(updatedPerson);
        when(personRepository.save(testPerson)).thenReturn(updatedPerson);
        when(personMapper.toDto(updatedPerson)).thenReturn(expectedDto);

        // Act
        PersonDto result = personService.update(updateDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId()); // ID должен быть из JWT
        verify(personMapper).update(eq(testPerson), eq(updatedPerson));
        verify(personRepository).save(testPerson);
    }

    @Test
    void update_ShouldSetIdFromJwt() {
        // Arrange
        PersonDto updateDto = PersonDto.builder()
                .id(999L) // Этот ID должен быть перезаписан
                .login("test")
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .gender(GenderEnum.MAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.SOFTWARE_DEVELOPMENT_DEPARTMENT)
                .role(RoleDto.builder().name(RoleEnum.ROLE_USER).build())
                .imageId(1L)
                .groups(new ArrayList<>())
                .isDeleted(false)
                .build();

        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findById(testJwtPerson.getId())).thenReturn(Optional.of(testPerson));
        when(personMapper.toEntity(updateDto)).thenReturn(testPerson);
        when(personRepository.save(testPerson)).thenReturn(testPerson);
        when(personMapper.toDto(testPerson)).thenReturn(testPersonDto);

        // Act
        PersonDto result = personService.update(updateDto);

        // Assert
        assertEquals(1L, result.getId()); // Должен быть ID из JWT, а не 999L
    }

    @Test
    void updateById_ShouldUpdateSpecificUser() {
        // Arrange
        Long userId = 2L;
        Person anotherPerson = Person.builder()
                .id(userId)
                .login("anotheruser")
                .firstName("Another")
                .lastName("User")
                .email("another@example.com")
                .gender(GenderEnum.MAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.SOFTWARE_DEVELOPMENT_DEPARTMENT)
                .role(testRole)
                .image(testImage)
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .password("password")
                .build();

        PersonDto updateDto = PersonDto.builder()
                .id(userId)
                .login("updateduser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .gender(GenderEnum.WOMAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.DEVOPS_DEPARTMENT)
                .role(RoleDto.builder().name(RoleEnum.ROLE_USER).build())
                .imageId(1L)
                .groups(new ArrayList<>())
                .isDeleted(false)
                .build();

        Person updatedPerson = Person.builder()
                .id(userId)
                .login("updateduser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .gender(GenderEnum.WOMAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.DEVOPS_DEPARTMENT)
                .role(testRole)
                .image(testImage)
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .password("password")
                .build();

        PersonDto expectedDto = PersonDto.builder()
                .id(userId)
                .login("updateduser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .gender(GenderEnum.WOMAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.DEVOPS_DEPARTMENT)
                .role(RoleDto.builder().name(RoleEnum.ROLE_ADMIN).build())
                .imageId(1L)
                .groups(new ArrayList<>())
                .isDeleted(false)
                .build();

        when(personRepository.findById(userId)).thenReturn(Optional.of(anotherPerson));
        when(personMapper.toEntity(updateDto)).thenReturn(updatedPerson);
        when(personRepository.save(anotherPerson)).thenReturn(updatedPerson);
        when(personMapper.toDto(updatedPerson)).thenReturn(expectedDto);

        // Act
        PersonDto result = personService.updateById(updateDto, userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(personMapper).update(eq(anotherPerson), eq(updatedPerson));
    }

    @Test
    void delete_WithId_ShouldSoftDeletePerson() {
        // Arrange
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.of(testPerson));

        // Act
        personService.delete(personId);

        // Assert
        assertTrue(testPerson.getIsDeleted());
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(testPerson);
    }

    @Test
    void delete_CurrentUser_ShouldSoftDeleteCurrentUser() {
        // Arrange
        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findById(testJwtPerson.getId())).thenReturn(Optional.of(testPerson));

        // Act
        personService.delete();

        // Assert
        assertTrue(testPerson.getIsDeleted());
        verify(jwtAuthorizationService, times(1)).extractJwtPerson();
        verify(personRepository, times(1)).findById(testJwtPerson.getId());
        verify(personRepository, times(1)).save(testPerson);
    }

    @Test
    void findByLogin_ShouldReturnPerson() {
        // Arrange
        String login = "johndoe";
        when(personRepository.findByLogin(login)).thenReturn(Optional.of(testPerson));

        // Act
        Person result = personService.findByLogin(login);

        // Assert
        assertNotNull(result);
        assertEquals(testPerson, result);
        verify(personRepository, times(1)).findByLogin(login);
    }

    @Test
    void findByLogin_WithNonExistentLogin_ShouldThrowEntityNotFoundException() {
        // Arrange
        String nonExistentLogin = "nonexistent";
        when(personRepository.findByLogin(nonExistentLogin)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.findByLogin(nonExistentLogin);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updatePersonRoleById_ShouldUpdatePersonRole() {
        // Arrange
        Long personId = 1L;
        RoleDto roleDto = RoleDto.builder().name(RoleEnum.ROLE_USER).build();
        Role newRole = Role.builder()
                .id(2L)
                .name(RoleEnum.ROLE_USER)
                .build();

        Person updatedPerson = Person.builder()
                .id(personId)
                .login("johndoe")
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .gender(GenderEnum.MAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.SOFTWARE_DEVELOPMENT_DEPARTMENT)
                .role(newRole)
                .image(testImage)
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .password("password")
                .build();

        PersonDto expectedDto = PersonDto.builder()
                .id(personId)
                .login("johndoe")
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .gender(GenderEnum.MAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.SOFTWARE_DEVELOPMENT_DEPARTMENT)
                .role(RoleDto.builder().name(RoleEnum.ROLE_USER).build())
                .imageId(1L)
                .groups(new ArrayList<>())
                .isDeleted(false)
                .build();

        when(personRepository.findById(personId)).thenReturn(Optional.of(testPerson));
        when(roleRepository.findByName(RoleEnum.ROLE_USER)).thenReturn(Optional.of(newRole));
        when(personRepository.save(testPerson)).thenReturn(updatedPerson);
        when(personMapper.toDto(updatedPerson)).thenReturn(expectedDto);

        // Act
        PersonDto result = personService.updatePersonRoleById(personId, roleDto);

        // Assert
        assertNotNull(result);
        assertEquals("ROLE_USER", result.getRole().getName().name());
        assertEquals(newRole, testPerson.getRole());
        verify(personRepository, times(1)).save(testPerson);
    }

    @Test
    void setImageById_ShouldSetImageForCurrentUser() {
        // Arrange
        Long imageId = 2L;
        Image newImage = Image.builder()
                .id(imageId)
                .image(new byte[]{4, 5, 6})
                .isDeleted(false)
                .build();

        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findPersonByIdAndIsNotDeleted(testJwtPerson.getId()))
                .thenReturn(Optional.of(testPerson));
        when(imageRepository.findByIdAndIsNotDeleted(imageId)).thenReturn(Optional.of(newImage));

        // Act
        personService.setImageById(imageId);

        // Assert
        assertEquals(newImage, testPerson.getImage());
        verify(personRepository, times(1)).save(testPerson);
    }

    @Test
    void updateImage_ForCurrentUser_ShouldCreateAndSetNewImage() throws IOException {
        // Arrange
        byte[] newImageBytes = new byte[]{7, 8, 9};
        MultipartFile file = new MockMultipartFile(
                "image",
                "new.jpg",
                "image/jpeg",
                newImageBytes
        );

        Image newImage = Image.builder()
                .id(2L)
                .image(newImageBytes)
                .isDeleted(false)
                .build();

        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findPersonByIdAndIsNotDeleted(testJwtPerson.getId()))
                .thenReturn(Optional.of(testPerson));
        when(imageRepository.save(any(Image.class))).thenReturn(newImage);

        // Act
        personService.updateImage(file);

        // Assert
        assertEquals(newImage, testPerson.getImage());
        verify(imageRepository, times(1)).save(any(Image.class));
        verify(personRepository, times(1)).save(testPerson);
    }

    @Test
    void updateImage_ForCurrentUser_WithIOException_ShouldThrowEntityNotFoundException() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findPersonByIdAndIsNotDeleted(testJwtPerson.getId()))
                .thenReturn(Optional.of(testPerson));
        when(file.getBytes()).thenThrow(new IOException("File error"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.updateImage(file);
        });

        assertEquals("Error updating image file", exception.getMessage());
        verify(imageRepository, never()).save(any());
        verify(personRepository, never()).save(any());
    }

    @Test
    void updateImage_WithId_ShouldUpdateSpecificUserImage() throws IOException {
        // Arrange
        Long userId = 2L;
        byte[] newImageBytes = new byte[]{10, 11, 12};
        MultipartFile file = new MockMultipartFile(
                "image",
                "update.jpg",
                "image/jpeg",
                newImageBytes
        );

        Person anotherPerson = Person.builder()
                .id(userId)
                .login("another")
                .firstName("Another")
                .lastName("User")
                .email("another@example.com")
                .gender(GenderEnum.MAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.SOFTWARE_DEVELOPMENT_DEPARTMENT)
                .role(testRole)
                .image(null) // У пользователя нет изображения
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .password("password")
                .build();

        Image newImage = Image.builder()
                .id(3L)
                .image(newImageBytes)
                .isDeleted(false)
                .build();

        when(personRepository.findPersonByIdAndIsNotDeleted(userId)).thenReturn(Optional.of(anotherPerson));
        when(imageRepository.save(any(Image.class))).thenReturn(newImage);

        // Act
        personService.updateImage(userId, file);

        // Assert
        assertEquals(newImage, anotherPerson.getImage());
        verify(imageRepository, times(1)).save(any(Image.class));
        verify(personRepository, times(1)).save(anotherPerson);
    }

    @Test
    void deleteImage_ShouldRemoveImageFromCurrentUser() {
        // Arrange
        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findPersonByIdAndIsNotDeleted(testJwtPerson.getId()))
                .thenReturn(Optional.of(testPerson));

        // Act
        personService.deleteImage();

        // Assert
        assertNull(testPerson.getImage());
        verify(personRepository, times(1)).save(testPerson);
    }

    @Test
    void addGroupByName_ForCurrentUser_ShouldAddGroup() {
        // Arrange
        String groupName = "Development Team";
        testGroup.getPersons().add(testPerson); // Группа уже содержит пользователя

        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findById(testJwtPerson.getId())).thenReturn(Optional.of(testPerson));
        when(groupRepository.findByName(groupName)).thenReturn(Optional.of(testGroup));

        // Act
        personService.addGroupByName(groupName);

        // Assert
        assertTrue(testPerson.getGroupList().contains(testGroup));
        assertTrue(testGroup.getPersons().contains(testPerson));
        verify(personRepository, times(1)).save(testPerson);
        verify(groupRepository, times(1)).save(testGroup);
    }

    @Test
    void addGroupByName_ForCurrentUser_WhenAlreadyInGroup_ShouldNotAddAgain() {
        // Arrange
        String groupName = "Development Team";
        testPerson.getGroupList().add(testGroup); // Пользователь уже в группе
        testGroup.getPersons().add(testPerson); // Группа уже содержит пользователя

        when(jwtAuthorizationService.extractJwtPerson()).thenReturn(testJwtPerson);
        when(personRepository.findById(testJwtPerson.getId())).thenReturn(Optional.of(testPerson));
        when(groupRepository.findByName(groupName)).thenReturn(Optional.of(testGroup));

        // Act
        personService.addGroupByName(groupName);

        // Assert
        // Не должно быть дополнительных сохранений
        verify(personRepository, never()).save(any());
        verify(groupRepository, never()).save(any());
    }

    @Test
    void addGroupByName_WithId_ShouldAddGroupToSpecificUser() {
        // Arrange
        String groupName = "Development Team";
        Long userId = 2L;
        Person anotherPerson = Person.builder()
                .id(userId)
                .login("another")
                .firstName("Another")
                .lastName("User")
                .email("another@example.com")
                .gender(GenderEnum.MAN)
                .status(StatusEnum.ACTIVE)
                .department(DepartmentEnum.SOFTWARE_DEVELOPMENT_DEPARTMENT)
                .role(testRole)
                .image(null)
                .groupList(new ArrayList<>())
                .isDeleted(false)
                .password("password")
                .build();

        when(personRepository.findById(userId)).thenReturn(Optional.of(anotherPerson));
        when(groupRepository.findByName(groupName)).thenReturn(Optional.of(testGroup));

        // Act
        personService.addGroupByName(groupName, userId);

        // Assert
        assertTrue(anotherPerson.getGroupList().contains(testGroup));
        assertTrue(testGroup.getPersons().contains(anotherPerson));
        verify(personRepository, times(1)).save(anotherPerson);
        verify(groupRepository, times(1)).save(testGroup);
    }
}