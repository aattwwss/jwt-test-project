package com.alvin.jwttokenapp.model.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
public class UserEntity {
    private long id;
    private String username;
    private String password;
    private Set<Role> roles;
    private Set<Authority> authorities;
}
