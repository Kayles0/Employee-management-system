package com.kayles.employee_management_system.Service;

import com.kayles.employee_management_system.dto.ImageDto;

public interface ImageService {
    void create(ImageDto dto);

    ImageDto read(Long id);

    void update(ImageDto dto);

    void delete(Long id);
}
