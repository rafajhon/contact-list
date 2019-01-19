package com.bravi.contactlist.models.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactsDTO implements Serializable {

    private List<ContactDTO> contacts = new ArrayList<>();

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }
}
