package com.bravi.contactlist.models.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ContactDTO implements Serializable {
    private Long id;
    @NotBlank
    private String description;
    @NotBlank
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
