package com.bravi.contactlist.controllers;

import com.bravi.contactlist.exceptions.ContactNotFundException;
import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.ContactDTO;
import com.bravi.contactlist.models.dto.ContactsDTO;
import com.bravi.contactlist.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private ContactService contactService;


    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactsDTO> getAll() {
        ContactsDTO contactsDTO = new ContactsDTO();
        contactsDTO.setContacts(this.contactService.findAll());
        return ResponseEntity.ok().body(contactsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getOne(@PathVariable(value = "id") Long id) throws ContactNotFundException {
        return ResponseEntity.ok().body(contactService.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactDTO> create(@Valid @RequestBody ContactDTO contactDTO) throws PersonNotFundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.create(contactDTO));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactDTO> update(@Valid @RequestBody ContactDTO contactDTO) throws ContactNotFundException {
        return ResponseEntity.ok().body(contactService.update(contactDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id){
        contactService.delete(id);
        return ResponseEntity.ok().build();
    }
}
