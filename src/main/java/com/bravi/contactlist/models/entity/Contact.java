package com.bravi.contactlist.models.entity;

import com.bravi.contactlist.models.enums.ContactType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Contact {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContactType description;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getDescription() {
        return description;
    }

    public void setDescription(ContactType description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}