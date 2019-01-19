package com.bravi.contactlist.services;

import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.PersonDTO;
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
        Optional<Person> person = this.personRepository.findById(id);
        return person.filter(Objects::nonNull)
                .map(this::converterToDTO)
                .orElseThrow(() -> new PersonNotFundException());
    }

    public PersonDTO create(PersonDTO personDTO) {
        Person person = this.converterToEntity(personDTO);
        this.save(person);
        return converterToDTO(person);
    }
    private Person save(Person person){
        return personRepository.save(person);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
