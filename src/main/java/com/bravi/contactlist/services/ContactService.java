package com.bravi.contactlist.services;

import com.bravi.contactlist.exceptions.ContactNotFundException;
import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.ContactDTO;
import com.bravi.contactlist.models.entity.Contact;
import com.bravi.contactlist.models.entity.Person;
import com.bravi.contactlist.models.enums.ContactType;
import com.bravi.contactlist.repositories.ContactRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    private Contact converterToEntity(final ContactDTO contactDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(contactDTO, Contact.class);
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

    private Contact save(Contact contact) {
        return contactRepository.save(contact);
    }


    public ContactDTO create(ContactDTO contactDTO) throws PersonNotFundException {
        Contact contact = this.converterToEntity(contactDTO);
        Person person = this.getPerson(contactDTO.getPersonId());
        this.save(contact);
        person.getContacts().add(contact);
        personService.save(person);
        ContactDTO contactDTOResponse = this.converterToDTO(contact);
        contactDTOResponse.setPersonId(person.getId());
        return contactDTO;
    }

    private Person getPerson(Long personId) throws PersonNotFundException {
        Optional<Person> personOp = personService.getPerson(personId);
        if(!personOp.isPresent()){
            throw new PersonNotFundException();
        }
        return personOp.get();
    }

    public ContactDTO update(ContactDTO contactDTO) throws ContactNotFundException {
        Optional<Contact> contactOp = this.getContact(contactDTO.getId());
        return contactOp.filter(Objects::nonNull)
                .map(contactDb->{return this.updateContact(contactDb, contactDTO);})
                .map(this::save)
                .map(this::converterToDTO)
                .orElseThrow(() -> new ContactNotFundException());
    }

    private Contact updateContact(Contact contactDb, ContactDTO contactDTO) {
        contactDb.setValue(contactDTO.getValue());
        contactDb.setDescription(ContactType.valueOf(contactDTO.getDescription()));
        return contactDb;
    }

    public Optional<Contact> getContact(Long id) {
        return this.contactRepository.findById(id);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }


}
