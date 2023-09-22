package com.staj.dto;

import com.staj.entities.Person;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Person}
 */
@Value
public class PersonDtoAuth implements Serializable {
    String username;
    String password;
}