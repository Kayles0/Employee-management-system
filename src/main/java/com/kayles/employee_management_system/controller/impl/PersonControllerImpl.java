package com.kayles.employee_management_system.controller.impl;

import com.kayles.employee_management_system.Service.PersonService;
import com.kayles.employee_management_system.controller.PersonController;
import com.kayles.employee_management_system.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonControllerImpl implements PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonControllerImpl.class);

    private final PersonService personService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<PersonDto> read(@PathVariable Long id) {
        logger.info("Read person with id {}", id);
        return ResponseEntity.ok(personService.read(id));
    }

    @Override
    @PutMapping
    public ResponseEntity<Void> update(PersonDto dto) {
        logger.info("Update");
        personService.update(dto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete() {
        logger.info("Delete");
        personService.delete();
        return ResponseEntity.ok().build();
    }
}
