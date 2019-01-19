package com.bravi.contactlist.services;

import com.bravi.contactlist.exceptions.ContactNotFundException;
import com.bravi.contactlist.models.dto.ContactDTO;
import com.bravi.contactlist.models.entity.Contact;
import com.bravi.contactlist.models.entity.Person;
import com.bravi.contactlist.repositories.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ContactService {
    private ContactRepository contactRepository;
    private PersonService personService;

    @Autowired
    public ContactService(ContactRepository contactRepository, PersonService personService) {
        this.contactRepository = contactRepository;
        this.personService = personService;
    }

    public List<ContactDTO> findAll() {
        return contactRepository.findAll().stream().map(this::converterToDTO).collect(toList());
    }

    private ContactDTO converterToDTO(final Contact contact) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(contact, ContactDTO.class);
    }

    public ContactDTO findById(Long id) throws ContactNotFundException {
        Optional<Contact> contactDb = contactRepository.findById(id);
        return contactDb.filter(Objects::nonNull)
                .map(contact -> {
                    ContactDTO contactDTO = converterToDTO(contact);
                    contactDTO.setPersonId(personService.findByContact(contact).getId());
                    return contactDTO;
                }).orElseThrow(() -> new ContactNotFundException());
    }


}
