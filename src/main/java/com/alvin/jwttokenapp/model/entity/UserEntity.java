package com.alvin.jwttokenapp.model.entity;

import lombok.Data;

@Data
public class UserEntity {
    private long id;
    private String username;
    private String password;
}
