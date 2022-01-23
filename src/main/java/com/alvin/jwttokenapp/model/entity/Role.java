package com.alvin.jwttokenapp.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Role {
    private long id;
    private String role;
}
