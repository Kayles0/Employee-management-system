package com.kayles.employee_management_system.controller.impl;

import com.kayles.employee_management_system.Service.PersonService;
import com.kayles.employee_management_system.controller.PersonController;
import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonControllerImpl implements PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonControllerImpl.class);

    private final PersonService personService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<PersonDto[]> personList(){
        logger.info("Get person list");
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<PersonDto> getMe(){
        logger.info("Get me");
        return ResponseEntity.ok(personService.findMe());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> readById(@PathVariable Long id) {
        logger.info("Read person with id {}", id);
        return ResponseEntity.ok(personService.read(id));
    }

    @Override
    @PutMapping
    public ResponseEntity<PersonDto> updateMe(@RequestBody PersonDto dto) {
        logger.info("Update");
        return ResponseEntity.ok(personService.update(dto));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteMe() {
        logger.info("Delete");
        personService.delete();
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping(value = "/setImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> updateImage(@RequestParam("file")MultipartFile file) {
        logger.info("Update image");
        return ResponseEntity.ok(personService.updateImageFromFile(file));
    }

   @Override
   @DeleteMapping("/deleteImage")
   public ResponseEntity<Void> deleteImage() {
        logger.info("Delete image");
        personService.deleteImage();
        return ResponseEntity.ok().build();
   }
}
