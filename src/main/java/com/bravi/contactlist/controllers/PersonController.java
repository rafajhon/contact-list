package com.bravi.contactlist.controllers;

import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.PersonDTO;
import com.bravi.contactlist.models.dto.PersonsDTO;
import com.bravi.contactlist.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<PersonsDTO> getAllPersons() {
        PersonsDTO personListDTO = new PersonsDTO();
        personListDTO.setPersonListDTOS(this.personService.findAll());
        return ResponseEntity.ok().body(personListDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getOnePerson(@PathVariable(value = "id") Long id) throws PersonNotFundException {
        return ResponseEntity.ok().body(personService.findById(id));
    }
}
