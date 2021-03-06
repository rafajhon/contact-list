package com.bravi.contactlist.controllers;

import com.bravi.contactlist.exceptions.ContactNotFundException;
import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.ContactDTO;
import com.bravi.contactlist.models.enums.ContactType;
import com.bravi.contactlist.services.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactService contactService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.contactController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        Mockito.when(contactService.findAll()).thenReturn(new LinkedList<>());
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"contacts\":[]}"));
    }

    @Test
    public void testFindIdPerson() throws Exception, ContactNotFundException {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(Long.valueOf("1"));
        contactDTO.setDescription(ContactType.EMAIL.name());
        contactDTO.setValue("test@gmail.com");
        contactDTO.setPersonId(new Long(1));
        Mockito.when(contactService.findById(eq(Long.valueOf("1")))).thenReturn(contactDTO);
        mockMvc.perform(get("/contacts/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"description\":\"EMAIL\",\"value\":\"test@gmail.com\",\"personId\":1}"));
    }

    @Test
    public void testCreate() throws Exception, PersonNotFundException {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(Long.valueOf("1"));
        contactDTO.setDescription(ContactType.EMAIL.name());
        contactDTO.setValue("test@gmail.com");
        contactDTO.setPersonId(new Long(1));
        Mockito.when(contactService.create(any())).thenReturn(contactDTO);
        mockMvc.perform(post("/contacts")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(contactDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"id\":1,\"description\":\"EMAIL\",\"value\":\"test@gmail.com\",\"personId\":1}"));
    }
    @Test
    public void testUpdate() throws Exception, PersonNotFundException, ContactNotFundException {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(Long.valueOf("1"));
        contactDTO.setDescription(ContactType.EMAIL.name());
        contactDTO.setValue("test@gmail.com");
        contactDTO.setPersonId(new Long(1));
        Mockito.when(contactService.update(any())).thenReturn(contactDTO);
        mockMvc.perform(put("/contacts")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(contactDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"description\":\"EMAIL\",\"value\":\"test@gmail.com\",\"personId\":1}"));
    }

    @Test
    public void testDelete() throws Exception {
        Mockito.doNothing().when(contactService).delete(eq(Long.valueOf("1")));
        mockMvc.perform(delete("/contacts/" + 1))
                .andExpect(status().isOk());
    }
}