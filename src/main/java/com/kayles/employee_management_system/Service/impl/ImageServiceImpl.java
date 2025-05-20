package com.kayles.employee_management_system.Service.impl;

import com.kayles.employee_management_system.Service.ImageService;
import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.entity.Image;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.mapper.ImageMapper;
import com.kayles.employee_management_system.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    public ImageDto createFromFile(MultipartFile imageFile) {
        try {
            Image image = Image.builder()
                    .image(imageFile.getBytes())
                    .isDeleted(false)
                    .build();
            image = imageRepository.save(image);
            return imageMapper.toDto(image);
        } catch (IOException e){
            throw new RuntimeException("Error creating image", e);
        }
    }

    @Override
    public ImageDto createFromDto(ImageDto dto) {
        Image image = imageMapper.toEntity(dto);
        imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    @Override
    public ImageDto read(Long id) {
        Image image = imageRepository.findByIdAndIsNotDeleted(id).orElseThrow(() -> new EntityNotFoundException("Image not found with id {}", id));
        return imageMapper.toDto(image);
    }

    @Override
    public ImageDto recreate(Long id, MultipartFile imageFile) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image not found with id {}", id));
        try {
            image.setImage(imageFile.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Error recreating image", e);
        }
        return imageMapper.toDto(image);
    }

    @Override
    public void update(ImageDto dto) {
        Image exImage = imageRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Image not found with id {}", dto.getId()));
        Image newImage = imageMapper.toEntity(dto);
        imageMapper.updateImage(exImage, newImage);
        imageRepository.save(exImage);
    }

    @Override
    public void delete(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image not found with id {}", id));
        image.setIsDeleted(true);
        imageRepository.save(image);
    }



}
