package com.kayles.employee_management_system.controller.impl;

import com.kayles.employee_management_system.controller.ImageController;
import com.kayles.employee_management_system.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("/image")
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    @Override
    public ResponseEntity<Void> createImage(ImageDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }
}
