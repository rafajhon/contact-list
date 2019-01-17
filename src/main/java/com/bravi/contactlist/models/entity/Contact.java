package com.bravi.contactlist.models.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Contact {

    @Id
    private Long id;
    private String description;
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