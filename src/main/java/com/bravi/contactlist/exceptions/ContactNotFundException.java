package com.bravi.contactlist.exceptions;

public class ContactNotFundException extends Exception {

    public ContactNotFundException(String message) {
        super("Contact not found");
    }
}
