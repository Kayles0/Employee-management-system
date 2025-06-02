package com.kayles.employee_management_system.controller.impl;

import com.kayles.employee_management_system.Service.ImageService;
import com.kayles.employee_management_system.Service.PersonService;
import com.kayles.employee_management_system.controller.AdminController;
import com.kayles.employee_management_system.dto.PersonDto;
import com.kayles.employee_management_system.dto.RoleDto;
import com.kayles.employee_management_system.entity.Image;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.repository.ImageRepository;
import com.kayles.employee_management_system.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminControllerImpl.class);

    private final PersonService personService;
    private final ImageService imageService;
    private final PersonRepository personRepository;
    private final ImageRepository imageRepository;

    @Override
    @PostMapping
    public ResponseEntity<PersonDto> updatePersonById(PersonDto dto) {
        logger.info("Update person id {}", dto.getId());
        return ResponseEntity.ok(personService.update(dto));
    }

    @Override
    @DeleteMapping("/deletePersonById/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable Long id) {
        logger.info("Delete person id {}", id);
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/updateRole/{id}")
    public ResponseEntity<PersonDto> setRoleById(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(personService.updatePersonRoleById(id, roleDto));
    }

    @Override
    @PutMapping("/updatePerson/{id}")
    public ResponseEntity<PersonDto> updatePersonById(@PathVariable Long id, @RequestBody PersonDto dto) {
        return ResponseEntity.ok(personService.updateById(dto, id));
    }

    @Override
    @PostMapping(value = "/image/recreate/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> recreateByIdFromFile(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        imageService.recreate(id, file);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping(value = "/person/{id}/updateImage")
    public ResponseEntity<Void> recreateImageForPersonById(@PathVariable("id") Long userId,@RequestParam("file") MultipartFile file) {
        personService.updateImage(userId, file);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/image/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable("id") Long id) {
        imageService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PersonDto> deletePersonFromGroup(String groupName, Long userId) {
        personService.deleteFromGroupByName(groupName, userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PersonDto> addPersonToGroup(String groupName, Long userId) {
        personService.addGroupByName(groupName, userId);
        return ResponseEntity.ok().build();
    }
}
