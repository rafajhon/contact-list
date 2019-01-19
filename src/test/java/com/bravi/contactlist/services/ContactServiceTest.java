package com.bravi.contactlist.services;

import com.bravi.contactlist.exceptions.ContactNotFundException;
import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.ContactDTO;
import com.bravi.contactlist.models.entity.Contact;
import com.bravi.contactlist.models.entity.Person;
import com.bravi.contactlist.models.enums.ContactType;
import com.bravi.contactlist.repositories.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {
    @InjectMocks
    private ContactService contactService;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private PersonService personService;


    @Test
    public void testFindAll() {
        Contact contact = new Contact();
        contact.setDescription(ContactType.EMAIL);
        contact.setValue("test@gmail.com.br");
        Mockito.when(contactRepository.findAll()).thenReturn(Collections.singletonList(contact));
        List<ContactDTO> contactDTOS = contactService.findAll();
        assertNotNull(contactDTOS);
        assertEquals(1, contactDTOS.size());
        assertEquals("test@gmail.com.br", contactDTOS.get(0).getValue());
        assertEquals("EMAIL", contactDTOS.get(0).getDescription());
    }

    @Test
    public void testFindId() throws ContactNotFundException {
        Contact contact = new Contact();
        contact.setId(new Long(1));
        contact.setDescription(ContactType.EMAIL);
        contact.setValue("test@gmail.com.br");
        Mockito.when(contactRepository.findById(new Long(1))).thenReturn(Optional.of(contact));
        Person person = new Person();
        person.setId(new Long(1));
        Mockito.when(personService.findByContact(eq(contact))).thenReturn(person);
        ContactDTO contactDTO = contactService.findById(new Long(1));
        assertNotNull(contactDTO);
        assertEquals("test@gmail.com.br", contactDTO.getValue());
        assertEquals("EMAIL", contactDTO.getDescription());
        assertEquals(new Long(1), contactDTO.getPersonId());
    }

    @Test
    public void testCreate() throws PersonNotFundException {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(Long.valueOf("1"));
        contactDTO.setDescription(ContactType.EMAIL.name());
        contactDTO.setValue("test@gmail.com");
        contactDTO.setPersonId(new Long(1));
        Person person = new Person();
        person.setId(new Long(2));
        Mockito.when(personService.getPerson(eq(new Long(1)))).thenReturn(Optional.of(person));
        Mockito.when(contactRepository.save(any(Contact.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });
        Mockito.when(personService.save(eq(person))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });
        ContactDTO contactDTOResponse = contactService.create(contactDTO);
        assertNotNull(contactDTOResponse);
    }

    @Test(expected = PersonNotFundException.class)
    public void testCreateError() throws PersonNotFundException {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(Long.valueOf("1"));
        contactDTO.setDescription(ContactType.EMAIL.name());
        contactDTO.setValue("test@gmail.com");
        contactDTO.setPersonId(new Long(1));
        Mockito.when(personService.getPerson(eq(new Long(1)))).thenReturn(Optional.ofNullable(null));
        contactService.create(contactDTO);
    }
}