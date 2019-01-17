package com.bravi.contactlist.controllers;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    PersonController personController;

    @Before
    public void setUp() throws Exception {
//        mockMvc = new MockMvcBuilder().build()
    }
}