package com.staj.dto;

import com.staj.entities.Person;
import com.staj.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Person}
 */
@Data
@NoArgsConstructor
public class PersonDtoEdit implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    String username;
    @NotNull
    @NotEmpty
    String email;
    @NotNull
    @NotEmpty
    String name;
    List<Long> roleRids = new ArrayList<>();
    Long s_id;
}