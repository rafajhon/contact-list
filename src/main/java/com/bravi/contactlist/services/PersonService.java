package com.bravi.contactlist.services;

import com.bravi.contactlist.models.dto.PersonDTO;
import com.bravi.contactlist.models.entity.Person;
import com.bravi.contactlist.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private PersonDTO converterToDTO(Person person){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(person,PersonDTO.class);
    }
}
