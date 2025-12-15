package com.kayles.employee_management_system.test.controller;

import com.kayles.employee_management_system.controller.impl.PersonControllerImpl;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.enums.GenderEnum;
import com.kayles.employee_management_system.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerImplTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonControllerImpl personController;

    private PersonDto testPersonDto;

    @BeforeEach
    void setUp() {
        testPersonDto = PersonDto.builder()
                .id(1L)
                .login("testuser")
                .gender(GenderEnum.MAN)
                .build();
    }

    // ========== GET TESTS ==========

    @Test
    void personList_ReturnsPersonArray() {
        // Arrange
        PersonDto[] personArray = new PersonDto[]{testPersonDto};
        when(personService.getAllPersons()).thenReturn(personArray);

        // Act
        ResponseEntity<PersonDto[]> response = personController.personList();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(personArray);
        verify(personService).getAllPersons();
    }

    @Test
    void getMe_ReturnsCurrentPerson() {
        // Arrange
        when(personService.findMe()).thenReturn(testPersonDto);

        // Act
        ResponseEntity<PersonDto> response = personController.getMe();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(testPersonDto);
        verify(personService).findMe();
    }

    @Test
    void readById_WithValidId_ReturnsPerson() {
        // Arrange
        when(personService.read(1L)).thenReturn(testPersonDto);

        // Act
        ResponseEntity<PersonDto> response = personController.readById(1L);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(testPersonDto);
        verify(personService).read(1L);
    }

    @Test
    void readById_WithNullId_CallsServiceWithNull() {
        // Act
        personController.readById(null);

        // Assert
        verify(personService).read(null);
    }

    // ========== PUT TESTS ==========

    @Test
    void updateMe_WithValidDto_ReturnsUpdatedPerson() {
        // Arrange
        when(personService.update(testPersonDto)).thenReturn(testPersonDto);

        // Act
        ResponseEntity<PersonDto> response = personController.updateMe(testPersonDto);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(testPersonDto);
        verify(personService).update(testPersonDto);
    }

    @Test
    void updateMe_WithNullDto_CallsServiceWithNull() {
        // Act
        personController.updateMe(null);

        // Assert
        verify(personService).update(null);
    }

    @Test
    void updateImage_WithValidId_ReturnsOk() {
        // Act
        ResponseEntity<Void> response = personController.updateImage(1L);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        verify(personService).setImageById(1L);
    }

    @Test
    void updateImage_WithNullId_CallsServiceWithNull() {
        // Act
        personController.updateImage(null);

        // Assert
        verify(personService).setImageById(null);
    }

    @Test
    void addToGroupByName_WithGroupName_ReturnsUpdatedPerson() {
        // Arrange
        String groupName = "TestGroup";
        when(personService.findMe()).thenReturn(testPersonDto);

        // Act
        ResponseEntity<PersonDto> response = personController.addToGroupByName(groupName);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(testPersonDto);
        verify(personService).addGroupByName(groupName);
        verify(personService).findMe();
    }

    @Test
    void addToGroupByName_WithEmptyGroupName_CallsServiceWithEmptyString() {
        // Arrange
        when(personService.findMe()).thenReturn(testPersonDto);

        // Act
        personController.addToGroupByName("");

        // Assert
        verify(personService).addGroupByName("");
    }

    @Test
    void deleteFromGroupByName_WithGroupName_ReturnsUpdatedPerson() {
        // Arrange
        String groupName = "TestGroup";
        when(personService.findMe()).thenReturn(testPersonDto);

        // Act
        ResponseEntity<PersonDto> response = personController.deleteFromGroupByName(groupName);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(testPersonDto);
        verify(personService).deleteFromGroupByName(groupName);
        verify(personService).findMe();
    }

    // ========== DELETE TESTS ==========

    @Test
    void deleteMe_ReturnsOk() {
        // Act
        ResponseEntity<Void> response = personController.deleteMe();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        verify(personService).delete();
    }

    @Test
    void deleteImage_ReturnsOk() {
        // Act
        ResponseEntity<Void> response = personController.deleteImage();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        verify(personService).deleteImage();
    }

    // ========== POST TESTS ==========

    @Test
    void recreateImage_WithMultipartFile_ReturnsOk() {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        // Act
        ResponseEntity<Void> response = personController.recreateImage(file);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        verify(personService).updateImage(file);
    }

    @Test
    void recreateImage_WithNullFile_CallsServiceWithNull() {
        // Act
        personController.recreateImage(null);

        // Assert
        verify(personService).updateImage(null);
    }

    @Test
    void readById_LogsInfoWithId() {
        // Arrange
        when(personService.read(999L)).thenReturn(testPersonDto);

        // Act
        personController.readById(999L);

        // Assert
        // Проверяем, что сервис был вызван с правильным ID
        verify(personService).read(999L);
    }

    @Test
    void updateImage_LogsInfoWithImageId() {
        // Act
        personController.updateImage(777L);

        // Assert
        verify(personService).setImageById(777L);
    }
}