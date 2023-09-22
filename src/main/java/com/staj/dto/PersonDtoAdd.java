package com.staj.dto;

import com.staj.entities.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Person}
 */
@Data
@NoArgsConstructor
public class PersonDtoAdd implements Serializable {
    String username;
    String password;
    String name;
    String email;
    Boolean enable= true;
    List<Long> roleRids = new ArrayList<>();
    Long s_id;
}