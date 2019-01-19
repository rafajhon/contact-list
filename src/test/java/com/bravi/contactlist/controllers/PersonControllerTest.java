package com.bravi.contactlist.controllers;

import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.PersonDTO;
import com.bravi.contactlist.services.PersonService;
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
public class PersonControllerTest {
    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;


    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.personController).build();
    }

    @Test
    public void testFindAllPersons() throws Exception {
        Mockito.when(personService.findAll()).thenReturn(new LinkedList<>());
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"persons\":[]}"));
    }

    @Test
    public void testFindIdPerson() throws Exception, PersonNotFundException {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(Long.valueOf("1"));
        personDTO.setName("testName");
        Mockito.when(personService.findById(eq(personDTO.getId()))).thenReturn(personDTO);
        mockMvc.perform(get("/persons/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"testName\",\"contacts\":[]}"));
    }

    @Test
    public void testCreate() throws Exception {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(Long.valueOf("1"));
        personDTO.setName("testName");
        Mockito.when(personService.create(any())).thenReturn(personDTO);
        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"id\":1,\"name\":\"testName\",\"contacts\":[]}"));
    }

    @Test
    public void testDelete() throws Exception {
        Mockito.doNothing().when(personService).delete(eq(Long.valueOf("1")));
        mockMvc.perform(delete("/persons/" + 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception, PersonNotFundException {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(Long.valueOf("1"));
        personDTO.setName("testName");
        Mockito.when(personService.update(any())).thenReturn(personDTO);
        mockMvc.perform(put("/persons")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"testName\",\"contacts\":[]}"));
    }

}