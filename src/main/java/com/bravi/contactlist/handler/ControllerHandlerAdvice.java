package com.bravi.contactlist.handler;

import com.bravi.contactlist.exceptions.ContactNotFundException;
import com.bravi.contactlist.exceptions.PersonNotFundException;
import com.bravi.contactlist.models.dto.ResponceErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandlerAdvice {

    @ExceptionHandler(PersonNotFundException.class)
    public ResponseEntity<ResponceErroDTO> catchMethodArgumentNotValidException(PersonNotFundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponceErroDTO(null, exception.getMessage()));
    }

    @ExceptionHandler(ContactNotFundException.class)
    public ResponseEntity<ResponceErroDTO> catchMethodContactNotFundException(ContactNotFundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponceErroDTO(null, exception.getMessage()));
    }
}
