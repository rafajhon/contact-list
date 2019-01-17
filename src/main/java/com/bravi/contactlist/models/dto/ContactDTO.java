package com.bravi.contactlist.models.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ContactDTO implements Serializable {
    private Long id;
    @NotBlank
    private String description;
    @NotBlank
    private String value;
}
