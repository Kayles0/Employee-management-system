package com.kayles.employee_management_system.test.service;

import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.entity.Image;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.mapper.ImageMapper;
import com.kayles.employee_management_system.repository.ImageRepository;
import com.kayles.employee_management_system.service.impl.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private ImageServiceImpl imageService;

    private Image testImage;
    private ImageDto testImageDto;
    private byte[] testImageBytes;

    @BeforeEach
    void setUp() {
        testImageBytes = new byte[]{1, 2, 3, 4, 5};
        testImage = Image.builder()
                .id(1L)
                .image(testImageBytes)
                .isDeleted(false)
                .build();

        testImageDto = ImageDto.builder()
                .id(1L)
                .image(testImageBytes)
                .build();
    }

    @Test
    void createFromFile_ShouldSaveAndReturnImageDto() throws IOException {
        // Arrange
        MultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                testImageBytes
        );

        when(imageRepository.save(any(Image.class))).thenReturn(testImage);
        when(imageMapper.toDto(testImage)).thenReturn(testImageDto);

        // Act
        ImageDto result = imageService.createFromFile(mockFile);

        // Assert
        assertNotNull(result);
        assertEquals(testImageDto, result);
        verify(imageRepository, times(1)).save(any(Image.class));
        verify(imageMapper, times(1)).toDto(testImage);
    }

    @Test
    void createFromFile_WithIOException_ShouldThrowRuntimeException() throws IOException {
        // Arrange
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenThrow(new IOException("File read error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            imageService.createFromFile(mockFile);
        });

        assertEquals("Error creating image", exception.getMessage());
        assertTrue(exception.getCause() instanceof IOException);
        verify(imageRepository, never()).save(any());
        verify(imageMapper, never()).toDto(any());
    }

    @Test
    void createFromFile_ShouldSetIsDeletedToFalse() throws IOException {
        // Arrange
        MultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                testImageBytes
        );

        Image savedImage = Image.builder()
                .id(2L)
                .image(testImageBytes)
                .isDeleted(false)
                .build();

        when(imageRepository.save(any(Image.class))).thenReturn(savedImage);
        when(imageMapper.toDto(savedImage)).thenReturn(ImageDto.builder()
                .id(2L)
                .image(testImageBytes)
                .build());

        // Act
        ImageDto result = imageService.createFromFile(mockFile);

        // Assert
        assertNotNull(result);
        verify(imageRepository).save(argThat(image ->
                image.getIsDeleted() != null && !image.getIsDeleted()
        ));
    }

    @Test
    void createFromDto_ShouldMapAndSaveImage() {
        // Arrange
        ImageDto inputDto = ImageDto.builder()
                .image(testImageBytes)
                .build();

        when(imageMapper.toEntity(inputDto)).thenReturn(testImage);
        when(imageRepository.save(testImage)).thenReturn(testImage);
        when(imageMapper.toDto(testImage)).thenReturn(testImageDto);

        // Act
        ImageDto result = imageService.createFromDto(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(testImageDto, result);
        verify(imageMapper, times(1)).toEntity(inputDto);
        verify(imageRepository, times(1)).save(testImage);
        verify(imageMapper, times(1)).toDto(testImage);
    }

    @Test
    void createFromDto_WithNullImageData_ShouldWork() {
        // Arrange
        ImageDto inputDto = ImageDto.builder()
                .image(null)
                .build();

        Image imageWithNullData = Image.builder()
                .id(3L)
                .image(null)
                .isDeleted(false)
                .build();

        ImageDto resultDto = ImageDto.builder()
                .id(3L)
                .image(null)
                .build();

        when(imageMapper.toEntity(inputDto)).thenReturn(imageWithNullData);
        when(imageRepository.save(imageWithNullData)).thenReturn(imageWithNullData);
        when(imageMapper.toDto(imageWithNullData)).thenReturn(resultDto);

        // Act
        ImageDto result = imageService.createFromDto(inputDto);

        // Assert
        assertNotNull(result);
        assertNull(result.getImage());
        verify(imageRepository).save(argThat(image -> image.getImage() == null));
    }

    @Test
    void read_WithValidId_ShouldReturnImageDto() {
        // Arrange
        Long imageId = 1L;
        when(imageRepository.findByIdAndIsNotDeleted(imageId))
                .thenReturn(Optional.of(testImage));
        when(imageMapper.toDto(testImage)).thenReturn(testImageDto);

        // Act
        ImageDto result = imageService.read(imageId);

        // Assert
        assertNotNull(result);
        assertEquals(testImageDto, result);
        verify(imageRepository, times(1)).findByIdAndIsNotDeleted(imageId);
        verify(imageMapper, times(1)).toDto(testImage);
    }

    @Test
    void read_WithNonExistentId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long nonExistentId = 999L;
        when(imageRepository.findByIdAndIsNotDeleted(nonExistentId))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            imageService.read(nonExistentId);
        });

        assertTrue(exception.getMessage().contains("Image not found with id"));
        verify(imageRepository, times(1)).findByIdAndIsNotDeleted(nonExistentId);
        verify(imageMapper, never()).toDto(any());
    }

    @Test
    void read_WithDeletedImage_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long deletedImageId = 2L;
        when(imageRepository.findByIdAndIsNotDeleted(deletedImageId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            imageService.read(deletedImageId);
        });
    }

    @Test
    void recreate_ShouldUpdateImageAndReturnDto() throws IOException {
        // Arrange
        Long imageId = 1L;
        byte[] newImageBytes = new byte[]{6, 7, 8, 9, 10};
        MultipartFile newFile = new MockMultipartFile(
                "newImage",
                "new.jpg",
                "image/jpeg",
                newImageBytes
        );

        Image updatedImage = Image.builder()
                .id(imageId)
                .image(newImageBytes)
                .isDeleted(false)
                .build();

        ImageDto updatedDto = ImageDto.builder()
                .id(imageId)
                .image(newImageBytes)
                .build();

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(testImage));
        when(imageRepository.save(testImage)).thenReturn(updatedImage);
        when(imageMapper.toDto(updatedImage)).thenReturn(updatedDto);

        // Act
        ImageDto result = imageService.recreate(imageId, newFile);

        // Assert
        assertNotNull(result);
        assertEquals(updatedDto, result);
        assertArrayEquals(newImageBytes, result.getImage());
        verify(imageRepository, times(1)).findById(imageId);
        verify(imageRepository, times(1)).save(testImage);
        verify(imageMapper, times(1)).toDto(updatedImage);
    }

    @Test
    void recreate_WithNonExistentId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long nonExistentId = 999L;
        MultipartFile mockFile = mock(MultipartFile.class);
        when(imageRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            imageService.recreate(nonExistentId, mockFile);
        });

        verify(imageRepository, times(1)).findById(nonExistentId);
        verify(imageRepository, never()).save(any());
    }

    @Test
    void recreate_WithIOException_ShouldThrowRuntimeException() throws IOException {
        // Arrange
        Long imageId = 1L;
        MultipartFile mockFile = mock(MultipartFile.class);
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(testImage));
        when(mockFile.getBytes()).thenThrow(new IOException("File read error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            imageService.recreate(imageId, mockFile);
        });

        assertEquals("Error recreating image", exception.getMessage());
        verify(imageRepository, never()).save(any());
    }

    @Test
    void update_ShouldUpdateExistingImage() {
        // Arrange
        ImageDto updateDto = ImageDto.builder()
                .id(1L)
                .image(new byte[]{10, 20, 30})
                .build();

        Image newImageEntity = Image.builder()
                .id(1L)
                .image(new byte[]{10, 20, 30})
                .isDeleted(false)
                .build();

        when(imageRepository.findById(1L)).thenReturn(Optional.of(testImage));
        when(imageMapper.toEntity(updateDto)).thenReturn(newImageEntity);

        // Act
        imageService.update(updateDto);

        // Assert
        verify(imageRepository, times(1)).findById(1L);
        verify(imageMapper, times(1)).toEntity(updateDto);
        verify(imageMapper, times(1)).updateImage(testImage, newImageEntity);
        verify(imageRepository, times(1)).save(testImage);
    }

    @Test
    void update_WithNonExistentId_ShouldThrowEntityNotFoundException() {
        // Arrange
        ImageDto updateDto = ImageDto.builder()
                .id(999L)
                .image(new byte[]{1, 2, 3})
                .build();

        when(imageRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            imageService.update(updateDto);
        });

        verify(imageRepository, times(1)).findById(999L);
        verify(imageMapper, never()).toEntity(any());
        verify(imageRepository, never()).save(any());
    }

    @Test
    void update_ShouldCallUpdateImageMapper() {
        // Arrange
        ImageDto updateDto = ImageDto.builder()
                .id(1L)
                .image(new byte[]{99, 100})
                .build();

        Image newImage = Image.builder()
                .id(1L)
                .image(new byte[]{99, 100})
                .build();

        when(imageRepository.findById(1L)).thenReturn(Optional.of(testImage));
        when(imageMapper.toEntity(updateDto)).thenReturn(newImage);

        // Act
        imageService.update(updateDto);

        // Assert
        verify(imageMapper).updateImage(eq(testImage), eq(newImage));
    }

    @Test
    void delete_ShouldSoftDeleteImage() {
        // Arrange
        Long imageId = 1L;
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(testImage));

        // Act
        imageService.delete(imageId);

        // Assert
        assertTrue(testImage.getIsDeleted());
        verify(imageRepository, times(1)).findById(imageId);
        verify(imageRepository, times(1)).save(testImage);
    }

    @Test
    void delete_WithNonExistentId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long nonExistentId = 999L;
        when(imageRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            imageService.delete(nonExistentId);
        });

        verify(imageRepository, times(1)).findById(nonExistentId);
        verify(imageRepository, never()).save(any());
    }

    @Test
    void delete_ShouldOnlySetIsDeletedToTrue() {
        // Arrange
        Long imageId = 1L;
        byte[] originalBytes = testImage.getImage();
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(testImage));

        // Act
        imageService.delete(imageId);

        // Assert
        assertTrue(testImage.getIsDeleted());
        assertArrayEquals(originalBytes, testImage.getImage()); // Image data should remain unchanged
        verify(imageRepository).save(argThat(image ->
                image.getIsDeleted() &&
                        image.getImage() == originalBytes
        ));
    }

    @Test
    void read_ShouldUseCorrectRepositoryMethod() {
        // Arrange
        Long imageId = 1L;
        when(imageRepository.findByIdAndIsNotDeleted(imageId))
                .thenReturn(Optional.of(testImage));
        when(imageMapper.toDto(testImage)).thenReturn(testImageDto);

        // Act
        imageService.read(imageId);

        // Assert
        verify(imageRepository).findByIdAndIsNotDeleted(imageId);
        verify(imageRepository, never()).findById(imageId);
    }
}