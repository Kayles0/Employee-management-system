package com.kayles.employee_management_system.controller.impl;

import com.kayles.employee_management_system.Service.ImageService;
import com.kayles.employee_management_system.Service.PersonService;
import com.kayles.employee_management_system.controller.AdminController;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminControllerImpl.class);

    private final PersonService personService;
    private final ImageService imageService;

    @Override
    @PostMapping
    public ResponseEntity<PersonDto> updatePersonById(PersonDto dto) {
        logger.info("Update person id {}", dto.getId());
        return ResponseEntity.ok(personService.update(dto));
    }

    @Override
    public ResponseEntity<Void> deletePersonById(Long id) {
        logger.info("Delete person id {}", id);
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<PersonDto> setRoleById(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(personService.updatePersonRoleById(id, roleDto));
    }

    @Override
    @PostMapping(value = "/image/recreate/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> recreateByIdFromFile(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        imageService.recreate(id, file);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable("id") Long id) {
        imageService.delete(id);
        return ResponseEntity.ok().build();
    }
}
