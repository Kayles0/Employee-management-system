package com.kayles.employee_management_system.service;

import com.kayles.employee_management_system.dto.ImageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    ImageDto createFromFile(MultipartFile imageFile);

    ImageDto createFromDto(ImageDto dto);

    ImageDto recreate(Long id, MultipartFile imageFile);

    ImageDto read(Long id);

    void update(ImageDto dto);

    void delete(Long id);
}
