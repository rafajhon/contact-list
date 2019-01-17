package com.bravi.contactlist.controllers;

import com.bravi.contactlist.models.dto.PersonDTO;
import com.bravi.contactlist.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    private ResponseEntity<List<PersonDTO>> getAll() {
        List<PersonDTO> personDTOList = this.personService.findAll();
        return ResponseEntity.ok().body(personDTOList);
    }
}
