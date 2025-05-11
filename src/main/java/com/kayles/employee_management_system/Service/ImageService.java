package com.kayles.employee_management_system.Service;

import com.kayles.employee_management_system.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void createNewImage(MultipartFile imageFile);

    void create(ImageDto dto);

    void recreate(Long id, MultipartFile imageFile);

    ImageDto read(Long id);

    void update(ImageDto dto);

    void delete(Long id);
}
