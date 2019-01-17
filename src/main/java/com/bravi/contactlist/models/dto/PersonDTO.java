package com.bravi.contactlist.models.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class PersonDTO implements Serializable {
    private Long id;
    @NotBlank
    private String name;
    private List<ContactDTO> contacts;

}
