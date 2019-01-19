package com.bravi.contactlist.exceptions;

public class ContactNotFundException extends Throwable {

    public ContactNotFundException() {
        super("Contact not found");
    }
}
