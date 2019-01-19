package com.bravi.contactlist.controllers;

import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.PersonDTO;
import com.bravi.contactlist.models.dto.PersonsDTO;
import com.bravi.contactlist.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonsDTO> getAll() {
        PersonsDTO personListDTO = new PersonsDTO();
        personListDTO.setPersonListDTOS(this.personService.findAll());
        return ResponseEntity.ok().body(personListDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getOne(@PathVariable(value = "id") Long id) throws PersonNotFundException {
        return ResponseEntity.ok().body(personService.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(personDTO));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO personDTO) throws PersonNotFundException {
        return ResponseEntity.ok().body(personService.update(personDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id){
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
}
