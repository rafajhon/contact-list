package com.bravi.contactlist.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class PersonsDTO implements Serializable {
    @JsonProperty(value = "persons")
    private List<PersonDTO> personListDTOS;

    public List<PersonDTO> getPersonListDTOS() {
        return personListDTOS;
    }

    public void setPersonListDTOS(List<PersonDTO> personListDTOS) {
        this.personListDTOS = personListDTOS;
    }
}
