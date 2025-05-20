package com.kayles.employee_management_system.controller.impl;

import com.kayles.employee_management_system.Service.ImageService;
import com.kayles.employee_management_system.controller.ImageController;
import com.kayles.employee_management_system.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageControllerImpl implements ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageControllerImpl.class);

    private final ImageService imageService;

    @Override
    @PostMapping
    public ResponseEntity<ImageDto> createImageFromDto(@RequestBody ImageDto dto) {
        logger.info("Create image from dto");
        return ResponseEntity.ok(imageService.createFromDto(dto));
    }

    @Override
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> createImageFromFile(@RequestParam("file") MultipartFile file) {
        logger.info("Create image from file");
        return ResponseEntity.ok(imageService.createFromFile(file));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> readImageById(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.read(id).getImage());
    }

}
