package com.bravi.contactlist.services;

import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.PersonDTO;
import com.bravi.contactlist.models.entity.Contact;
import com.bravi.contactlist.models.entity.Person;
import com.bravi.contactlist.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findAll() {
        return personRepository.findAll().stream().map(this::converterToDTO).collect(toList());

    }

    private PersonDTO converterToDTO(final Person person) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(person, PersonDTO.class);
    }

    private Person converterToEntity(final PersonDTO personDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(personDTO, Person.class);
    }

    public PersonDTO findById(final Long id) throws PersonNotFundException {
        Optional<Person> person = getPerson(id);
        return person.filter(Objects::nonNull)
                .map(this::converterToDTO)
                .orElseThrow(() -> new PersonNotFundException());
    }

    public Optional<Person> getPerson(Long id) {
        return this.personRepository.findById(id);
    }

    public PersonDTO create(PersonDTO personDTO) {
        Person person = this.converterToEntity(personDTO);
        this.save(person);
        return converterToDTO(person);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public PersonDTO update(PersonDTO personDTO) throws PersonNotFundException {
        Optional<Person> person = this.getPerson(personDTO.getId());
        return person.filter(Objects::nonNull)
                .map(personDb->{return this.updatePerson(personDb, personDTO);})
                .map(this::save)
                .map(this::converterToDTO)
                .orElseThrow(() -> new PersonNotFundException());
    }

    private Person updatePerson(Person personDb, PersonDTO personDTO) {
        personDb.setName(personDTO.getName());
        return personDb;
    }

    public Person findByContact(Contact contact) {
        return this.personRepository.findByContacts(contact);
    }
}
