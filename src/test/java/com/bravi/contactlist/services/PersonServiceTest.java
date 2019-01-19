package com.bravi.contactlist.services;

import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.PersonDTO;
import com.bravi.contactlist.models.entity.Contact;
import com.bravi.contactlist.models.entity.Person;
import com.bravi.contactlist.repositories.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Test
    public void findBySuccess() throws PersonNotFundException {
        Person person = new Person();
        person.setName("nameTest");
        person.setId(new Long(10));
        Contact contact = new Contact();
        contact.setDescription("Email");
        contact.setValue("test@mail.com.br");
        person.setContacts(Collections.singletonList(contact));
        Mockito.when(personRepository.findById(eq(person.getId()))).thenReturn(Optional.of(person));
        PersonDTO personDTO = personService.findById(person.getId());
        assertEquals(person.getName(), personDTO.getName());
        assertEquals(person.getId(), personDTO.getId());
        assertEquals(person.getContacts().get(0).getValue(), personDTO.getContacts().get(0).getValue());
        assertEquals(person.getContacts().get(0).getDescription(), personDTO.getContacts().get(0).getDescription());

    }

    @Test(expected = PersonNotFundException.class)
    public void findByError() throws PersonNotFundException {
        Long aLong = new Long(1);
        Optional<Person> o = Optional.ofNullable(null);
        Mockito.when(personRepository.findById(aLong)).thenReturn(o);
        personService.findById(aLong);
    }
}