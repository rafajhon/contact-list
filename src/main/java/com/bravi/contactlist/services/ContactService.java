package com.bravi.contactlist.services;

import com.bravi.contactlist.models.dto.ContactDTO;
import com.bravi.contactlist.models.entity.Contact;
import com.bravi.contactlist.repositories.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ContactService {
    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDTO> findAll() {
        return contactRepository.findAll().stream().map(this::converterToDTO).collect(toList());
    }

    private ContactDTO converterToDTO(final Contact contact) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(contact, ContactDTO.class);
    }

}
