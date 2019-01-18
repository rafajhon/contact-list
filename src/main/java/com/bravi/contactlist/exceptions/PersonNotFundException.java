package com.bravi.contactlist.exceptions;

public class PersonNotFundException extends Throwable {
    public PersonNotFundException() {
        super("Registration Request not found");
    }
}
