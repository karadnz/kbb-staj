package com.staj.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eid;

    @NotBlank
    @NotEmpty
    @NotNull
    private String username;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String name;

    private Boolean enable = true;

    @ManyToMany
    private List<Role> roles;

    private Long s_id;


    public String getName() {
        return this.username;
    }
}


